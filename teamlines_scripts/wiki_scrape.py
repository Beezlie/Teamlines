from bs4 import BeautifulSoup
from twitter_scrape import populate_handles
from unidecode import unidecode  #library used to convert unicode characters in player's names to ASCII
import urllib.request

header = {'User-Agent': 'Mozilla/5.0'}

class Athlete:
    def __init__(self, name, team, twitter_handle):
        self.name = name
        self.team = team
        self.twitter_handle = twitter_handle

def nba_scrape(wiki, twitacct, player_dict):
    wiki_req = urllib.request.Request(wiki,headers=header)
    twit_req = urllib.request.Request(twitacct,headers=header)
    wiki_page = urllib.request.urlopen(wiki_req)
    twit_page = urllib.request.urlopen(twit_req)
    wiki_soup = BeautifulSoup(wiki_page)
    twit_soup = BeautifulSoup(twit_page)
    copy_dict = dict()

    # scrape NBA wiki page for player names and their teams
    #Team name listed under h4 heading
    for h4 in wiki_soup.find_all("h4"):
        team_info = h4.find_next()
        for x in team_info:
            team = x.text
        #list of players for each team found in sortable table below
        table = h4.find_next("table", {"class": "sortable"})
        for row in table.find_all("tr"):
            cells = row.find_all("td", {"style": "text-align:left;"})
            for x in cells[:1]:
                # player name string formatted as 'last_name, first_name'
                name = x.text.split(", ")
                last_name = name[0]
                #if players have a first and last name (> Nene)
                if len(name) > 1:
                    first_name = name[1]
                    #remove unicode characters
                    if name[1].find("\xa0") != -1:
                        first_name = name[1].split("\xa0", 1)[0]
                    full_name = unidecode(first_name + (" ") + last_name) #convert to ASCII
                    player_dict[full_name] = Athlete(full_name, team, "None")

    #scrape NBA twitter handle page for player names and their teams
    for row in twit_soup.find_all("tr"):
        cells = row.find_all("td")
        for x in cells[:1]:
            name = x.text
        for x in cells[1:2]:
            # if the player name matches active players from wikipedia, add twitter handle to Athlete object
            if name in player_dict:
                twitter_handle = x.text
                player_dict[name].twitter_handle = twitter_handle

    # populate dict with twitter handles using a call to the Twitter API with Tweepy
    player_dict = populate_handles(player_dict, 'NBAplayers', 'nba-players')

    # copy to new dictionary - removes players that do not have twitter accounts
    for i in player_dict:
        if player_dict[i].twitter_handle is not "None":
            copy_dict[i] = player_dict[i]

    return copy_dict

def mlb_scrape(wiki, twitacct, player_dict):
    wiki_req = urllib.request.Request(wiki,headers=header)
    twit_req = urllib.request.Request(twitacct,headers=header)
    wiki_page = urllib.request.urlopen(wiki_req)
    twit_page = urllib.request.urlopen(twit_req)
    wiki_soup = BeautifulSoup(wiki_page)
    twit_soup = BeautifulSoup(twit_page)
    copy_dict = dict()

    # scrape MLB wiki page for player names and their teams
    # Team name listed in 'toccolours' tables
    for tbl in wiki_soup.find_all("table", {"class": "toccolours"}):
        team_info = tbl.find("li", {"class": "nv-view"})
        # Get team name from list title
        # Given string ex) 'Template:Chicago Cubs roster' - remove 'Template:' and ' roster'
        for x in team_info:
            team = x.get("title").split(":")
            team = (team[1])[:-7]
        for li in tbl.find_all("li"):
            if len(li.text) > 1:
                player = li.text.split(' ', 1)
                full_name = unidecode(player[1])
                player_dict[full_name] = Athlete(full_name, team, "None")

    # scrape MLB twitter handle page for player names and their teams
    for content in twit_soup.find_all("div", {"class": "section_content"}):
        # gives player info as 'player name - @handle'
        for p in content.find_all("p"):
            info = p.text.split(' - ')
            name = unidecode(info[0])
            #if the player name matches active players from wikipedia, add twitter handle to Athlete object
            if name in player_dict:
                 twitter_handle = info[1]
                 player_dict[name].twitter_handle = twitter_handle

    # populate dict with twitter handles using a call to the Twitter API with Tweepy
    player_dict = populate_handles(player_dict, 'MLB', 'players1')

    # copy to new dictionary - removes players that do not have twitter accounts
    for i in player_dict:
        if player_dict[i].twitter_handle is not "None":
            copy_dict[i] = player_dict[i]

    return copy_dict

def nhl_scrape(wiki, player_dict):
    wiki_req = urllib.request.Request(wiki,headers=header)
    wiki_page = urllib.request.urlopen(wiki_req)
    wiki_soup = BeautifulSoup(wiki_page)
    copy_dict = dict()

    # scrape NHL wiki page for player names and their teams
    # Team name listed under h3 heading
    for h3 in wiki_soup.find_all("h3"):
        content = h3.text
        if "edit" in content:
            team = content[:-6]
            # list of players for each team found in sortable table below
            table = h3.find_next("table", {"class": "sortable"})
            for row in table.find_all("tr"):
                info = row.find_all("span", {"class": "sortkey"})
                for x in info[1:2]:
                    # player name string formatted as 'last_name, first_name'
                    name = x.text.split(", ")
                    last_name = name[0]
                    # if players have a first and last name
                    if len(name) > 1:
                        first_name = name[1]
                        # remove unicode characters
                        if name[1].find("\xa0") != -1:
                            first_name = name[1].split("\xa0", 1)[0]
                        full_name = unidecode(first_name + (" ") + last_name)  # convert to ASCII
                        player_dict[full_name] = Athlete(full_name, team, "None")

    # populate dict with twitter handles using a call to the Twitter API with Tweepy
    player_dict = populate_handles(player_dict, 'NHL', 'nhl-players')

    # copy to new dictionary - removes players that do not have twitter accounts
    for i in player_dict:
        if player_dict[i].twitter_handle is not "None":
            copy_dict[i] = player_dict[i]

    # for i in copy_dict:
    #     print(i, copy_dict[i].name, copy_dict[i].team, copy_dict[i].twitter_handle)

    return copy_dict