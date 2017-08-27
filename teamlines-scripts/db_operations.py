import psycopg2
from config import config

### Player name, team and twitter handle stored in DB.
### Results are stored in the event that the Twitter rate limit is reached
### and the app's Twitter lists can no longer be updated using the Tweepy API

def create_tables():
    conn = None
    try:
        # read connection parameters
        params = config("postgresql")
        # connect to the db using the params
        conn = psycopg2.connect(**params)
        #create a cursor
        cur = conn.cursor()
        #create the database tables
        #create constraints to be used for UPSERT operations in update_db()
        commands = (
            """
            CREATE TABLE nba_player(
                player_id SERIAL PRIMARY KEY,
                player_name VARCHAR(255) NOT NULL,
                player_team VARCHAR(255) NOT NULL,
                player_handle VARCHAR(255) NOT NULL
            )""",
            """
            ALTER TABLE nba_player
                ADD CONSTRAINT nba_player_uq
                UNIQUE (player_name, player_handle);
            """,
            """
            CREATE TABLE mlb_player(
                player_id SERIAL PRIMARY KEY,
                player_name VARCHAR(255) NOT NULL,
                player_team VARCHAR(255) NOT NULL,
                player_handle VARCHAR(255) NOT NULL
            )""",
            """
            ALTER TABLE mlb_player
                ADD CONSTRAINT mlb_player_uq
                UNIQUE (player_name, player_handle);
            """,
            """
            CREATE TABLE nhl_player(
                player_id SERIAL PRIMARY KEY,
                player_name VARCHAR(255) NOT NULL,
                player_team VARCHAR(255) NOT NULL,
                player_handle VARCHAR(255) NOT NULL
            )""",
            """
            ALTER TABLE nhl_player
                ADD CONSTRAINT nhl_player_uq
                UNIQUE (player_name, player_handle);
            """)
        for command in commands:
            cur.execute(command)
        #close the communication with PostgreSQL
        cur.close()
        #commit the changes
        conn.commit()
    except(Exception, psycopg2.DatabaseError) as error:
        print(error)
    # close the DB connection
    finally:
        if conn is not None:
            conn.close()

def update_db(league, player_dict):
    conn = None
    try:
        # read connection parameters
        params = config("postgresql")
        # connect to the db using the params
        conn = psycopg2.connect(**params)
        #create a cursor
        cur = conn.cursor()
        #insert player into DB from input dictionary and sport league
        #if entry already exists, update entry
        for i in player_dict:
            sql = """INSERT INTO {0}_player(player_name, player_team, player_handle) VALUES($${1}$$, $${2}$$, $${3}$$)
                         ON CONFLICT(player_name, player_handle) DO UPDATE
                             SET player_team = $${2}$$,
                             player_handle = $${3}$$;""".format(league, player_dict[i].name, player_dict[i].team, player_dict[i].twitter_handle)
            cur.execute(sql)
            conn.commit()
        #close the communication with PostgreSQL
        cur.close()
        #commit the changes
        conn.commit()
    except(Exception, psycopg2.DatabaseError) as error:
        print(error)
    #close the DB connection
    finally:
        if conn is not None:
            conn.close()


