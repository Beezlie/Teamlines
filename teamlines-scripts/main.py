#!/usr/bin/python

from db_operations import update_db, create_tables
from wiki_scrape import nba_scrape, mlb_scrape, nhl_scrape

nba_wiki = "https://en.wikipedia.org/wiki/List_of_current_NBA_team_rosters"     #list of NBA roster and players
nba_twitacct = "http://www.basketball-reference.com/friv/twitter.html"          #NBA twitter handle list
nhl_eastern_wiki = "https://en.wikipedia.org/wiki/List_of_current_NHL_Eastern_Conference_team_rosters"      #list of NHL eastern conf. roster and players
nhl_western_wiki = "https://en.wikipedia.org/wiki/List_of_current_NHL_Western_Conference_team_rosters"      #list of NHL western conf. roster and players
mlb_wiki = "https://en.wikipedia.org/wiki/List_of_current_Major_League_Baseball_team_rosters"   #list of MLB roster and players
mlb_twitacct = "http://www.baseball-reference.com/friv/baseball-player-twitter-accounts.shtml"  #MLB twitter handle list

#dictionary used to store Athlete objects (name, team, twitter handle)
player_dict = dict()

#create nba, mlb and nhl player tables in the DB
create_tables()

#scrape mlb wikipedia page for a list of teams and associated players
player_dict = mlb_scrape(mlb_wiki, mlb_twitacct, player_dict)
# update mlb table in the DB from player_dict
update_db("mlb", player_dict)
player_dict.clear()

#scrape nba wikipedia page for a list of teams and associated players
player_dict = nba_scrape(nba_wiki, nba_twitacct, player_dict)
# update nba table in the DB from player_dict
update_db("nba", player_dict)
player_dict.clear()

#scrape nhl eastern conference wikipedia page for a list of teams and associated players
player_dict = nhl_scrape(nhl_eastern_wiki, player_dict)
# populate dict with twitter handles using a call to the Twitter API with Tweepy
update_db("nhl", player_dict)
player_dict.clear()

#scrape nhl western conference wikipedia page for a list of teams and associated players
player_dict = nhl_scrape(nhl_western_wiki, player_dict)
# update nhl table in the DB with western conference player data
update_db("nhl", player_dict)
player_dict.clear()
