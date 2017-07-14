import tweepy
from config import config
from unidecode import unidecode

params = config("OAuth")    # read authentication parameters

vars = list()   # initialize list to store Twitter OAuth variables
for key, value in params.items() :
    vars.append(value)

#variables for Twitter OAuth
consumer_key = vars[0]
consumer_secret = vars[1]
access_token = vars[2]
access_token_secret = vars[3]

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

api = tweepy.API(auth)

#use Tweepy and Twitter API to iterate through lists of professional athletes to get list member data
def populate_handles(player_dict, user, slug):
    # Iterate through all members of the owner's list
    for member in tweepy.Cursor(api.list_members, user, slug).items():
        name = unidecode(member.name.title())
        #if current player (listed in wikipedia roster) then add Twitter handle to associated Athlete object
        if name in player_dict:
            twitter_handle = "@" + member.screen_name
            player_dict[name].twitter_handle = twitter_handle

    return player_dict
