import tweepy
import logging
from config import config
from unidecode import unidecode

#TODO fix the add_list_members call (Tweepy error code 25)

logging.basicConfig(filename='teamlines.log', level=logging.ERROR)

# read authentication parameters
params = config("OAuth")    
vars = list()  
for key, value in params.items() :
    vars.append(value)

#variables for Twitter OAuth
consumer_secret = vars[0]
access_token = vars[1]
consumer_key = vars[2]
user = vars[3]
access_token_secret = vars[4]

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True)

#use Tweepy and Twitter API to iterate through lists of professional athletes to get list member data
def populate_handles(player_dict, user, slug):
    # Iterate through all members of the owner's list
    for member in tweepy.Cursor(api.list_members, user, slug).items():
        name = unidecode(member.name.title())
        #if current player (listed in wikipedia roster) then add Twitter handle to associated Athlete object
        if name in player_dict:
            player_dict[name].twitter_handle = member.screen_name
    return player_dict

#update Teamlines Twitter list
def update_twlist(player_dict):
    twlist = api.lists_all()
    twlist_teams = dict()    # key = name, value = id
    twlist_players = dict()  #key = list_id, value = list of players

    #get all lists belonging to Teamlines app
    for member in twlist:
        twlist_teams[member.name] = member.id
        twlist_players[member.id] = []
        #note - only able to delete 100 lists at a time
        #api.destroy_list(list_id=member.id)

    for player in player_dict:
        #create twitter list for any new teams
        team = player_dict[player].team
        handle = player_dict[player].twitter_handle
        if team not in twlist_teams:
            try:
                logging.info("Creating list: %s", team)
                new_list = api.create_list(team)
                twlist_teams[team] = new_list.id
                twlist_players[new_list.id] = []
            except Exception, e:
                logging.error("Unable to create list: %s", team)
                logging.error(e)
        
        #add player to twitter list
        twlist_players[twlist_teams[team]].append(handle.encode('ascii','ignore')) 
    
    #bulk add all players for each team to the associated twitter list
    for list_id in twlist_players:
        try:
            api.add_list_members(list_id=list_id, screen_name=twlist_players[list_id])
        except Exception, e:
            logging.error("Unable to add players to list id: %u", list_id)
            logging.error(e)
