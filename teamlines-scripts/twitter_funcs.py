import tweepy
import logging
from config import config
from unidecode import unidecode

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

    #get all lists belonging to Teamlines app
    twlist_names = list()    
    twlist_dict = dict()
    
    for member in twlist:
        twlist_names.append(member.name)
        twlist_dict[member.name] = member.id
        #note - only able to delete 100 lists at a time
        #api.destroy_list(list_id=member.id)

    for player in player_dict:
        #create twitter list for any new teams
        if player_dict[player].team not in twlist_names:
            twlist_names.append(player_dict[player].team)
            try:
                logging.info("Creating list: %s", player_dict[player].team)
                new_list = api.create_list(player_dict[player].team)
                twlist_dict[new_list.name] = new_list.id
            except Exception, e:
                logging.error("Unable to create list: %s", player_dict[player].team)
                print(e)
        #add player to twitter list
        list_id = twlist_dict[player_dict[player].team]
        #need to check if player is already in list before adding (due to limit on # players that can be added)

'''
        try:
            api.add_list_member(screen_name=player_dict[player].twitter_handle, list_id=list_id) 
        except Exception, e:
            logging.error("Unable to add %s with screen_name %s to list %s",  player_dict[player].name, player_dict[player].twitter_handle, player_dict[player].team)
            print(e)
'''            
