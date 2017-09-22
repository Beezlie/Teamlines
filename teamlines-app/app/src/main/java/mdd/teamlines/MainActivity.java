package mdd.teamlines;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String currentLeagueSelectedKey = "currentLeagueSelectedKey";
    private static String league = "MLB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        HashMap<String, Team[]> hmap = new HashMap<>();
        hmap.put("MLB", mlbteams);
        hmap.put("NHL", nhlteams);
        hmap.put("NBA", nbateams);

        setSportView(hmap.get(league));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    //need to think of a way to pass a different team array based on the league selected and have it refresh the main_activity and use that team instead of default
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.baseball:
                setSportView(mlbteams);
                league = "MLB";
                return true;
            case R.id.hockey:
                setSportView(nhlteams);
                league = "NHL";
                return true;
            case R.id.basketball:
                setSportView(nbateams);
                league = "NBA";
                return true;
            case R.id.football:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(currentLeagueSelectedKey, league);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            league = savedInstanceState.getString(currentLeagueSelectedKey);
        }
    }

    public void setSportView(Team[] teams) {
        GridView gridView = (GridView)findViewById(R.id.gridview);

        final TeamsAdapter teamsAdapter = new TeamsAdapter(this, teams);
        gridView.setAdapter(teamsAdapter);

        final Team[] league = teams;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Team team = league[position];

                //launch new actvity displaying twitter timeline for selected team
                Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
                //pass the name of the twitter list
                intent.putExtra("twitterList", getString(team.getSlug()));
                startActivity(intent);
            }
        });
    }

    //team name, twitter list slug, league, team logo image resource
    private Team[] mlbteams = {
            new Team(R.string.arizona_diamondbacks, R.string.arizona_diamondbacks_slug, R.string.mlb, R.drawable.mlb_arizona),
            new Team(R.string.atlanta_braves, R.string.atlanta_braves_slug, R.string.mlb, R.drawable.mlb_atlanta),
            new Team(R.string.baltimore_orioles, R.string.baltimore_orioles_slug, R.string.mlb, R.drawable.mlb_baltimore),
            new Team(R.string.boston_red_sox, R.string.boston_red_sox_slug, R.string.mlb, R.drawable.mlb_boston),
            new Team(R.string.chicago_white_sox, R.string.chicago_white_sox_slug, R.string.mlb, R.drawable.mlb_chicagowhitesox),
            new Team(R.string.chicago_cubs, R.string.chicago_cubs_slug, R.string.mlb, R.drawable.mlb_chicagocubs),
            new Team(R.string.cincinnati_reds, R.string.cincinnati_reds_slug, R.string.mlb, R.drawable.mlb_cincinnati),
            new Team(R.string.cleveland_indians, R.string.cleveland_indians_slug, R.string.mlb, R.drawable.mlb_cleveland),
            new Team(R.string.colorado_rockies, R.string.colorado_rockies_slug, R.string.mlb, R.drawable.mlb_colorado),
            new Team(R.string.detroit_tigers, R.string.detroit_tigers_slug, R.string.mlb, R.drawable.mlb_detroit),
            new Team(R.string.houston_astros, R.string.houston_astros_slug, R.string.mlb, R.drawable.mlb_houston),
            new Team(R.string.kansas_city_royals, R.string.kansas_city_royals_slug, R.string.mlb, R.drawable.mlb_kansascity),
            new Team(R.string.los_angeles_angels, R.string.los_angeles_angels_slug, R.string.mlb, R.drawable.mlb_losangelesangels),
            new Team(R.string.los_angeles_dodgers, R.string.los_angeles_dodgers_slug, R.string.mlb, R.drawable.mlb_losangelesdodgers),
            new Team(R.string.miami_marlins, R.string.miami_marlins_slug, R.string.mlb, R.drawable.mlb_miami),
            new Team(R.string.milwaukee_brewers, R.string.milwaukee_brewers_slug, R.string.mlb, R.drawable.mlb_milwaukee),
            new Team(R.string.minnesota_twins, R.string.minnesota_twins_slug, R.string.mlb, R.drawable.mlb_minnesota),
            new Team(R.string.new_york_mets, R.string.new_york_mets_slug, R.string.mlb, R.drawable.mlb_nymets),
            new Team(R.string.new_york_yankees, R.string.new_york_yankees_slug, R.string.mlb, R.drawable.mlb_nyyankees),
            new Team(R.string.oakland_athletics, R.string.oakland_athletics_slug, R.string.mlb, R.drawable.mlb_oakland),
            new Team(R.string.philadelphia_phillies, R.string.philadelphia_phillies_slug, R.string.mlb, R.drawable.mlb_philadelphia),
            new Team(R.string.pittsburgh_pirates, R.string.pittsburgh_pirates_slug, R.string.mlb, R.drawable.mlb_pittsburgh),
            new Team(R.string.san_diego_padres, R.string.san_diego_padres_slug, R.string.mlb, R.drawable.mlb_sandiego),
            new Team(R.string.san_francisco_giants, R.string.san_francisco_giants_slug, R.string.mlb, R.drawable.mlb_sanfrancisco),
            new Team(R.string.seattle_mariners, R.string.seattle_mariners_slug, R.string.mlb, R.drawable.mlb_seattle),
            new Team(R.string.st_louis_cardinals, R.string.st_louis_cardinals_slug, R.string.mlb, R.drawable.mlb_stlouis),
            new Team(R.string.tampa_bay_rays, R.string.tampa_bay_rays_slug, R.string.mlb, R.drawable.mlb_tampabay),
            new Team(R.string.texas_rangers, R.string.texas_rangers_slug, R.string.mlb, R.drawable.mlb_texas),
            new Team(R.string.toronto_blue_jays, R.string.toronto_blue_jays_slug, R.string.mlb, R.drawable.mlb_toronto),
            new Team(R.string.washington_nationals, R.string.washington_nationals_slug, R.string.mlb, R.drawable.mlb_washington)
    };

    private Team[] nhlteams = {
            new Team(R.string.ottawa_senators, R.string.ottawa_senators_slug, R.string.nhl, R.drawable.nhl_ottawa),
            new Team(R.string.buffalo_sabres, R.string.buffalo_sabres_slug, R.string.nhl, R.drawable.nhl_buffalo)
    };

    private Team[] nbateams = {
            new Team(R.string.atlanta_hawks, R.string.atlanta_hawks_slug, R.string.nba, R.drawable.nba_atlanta),
            new Team(R.string.boston_celtics, R.string.boston_celtics_slug, R.string.nba, R.drawable.nba_boston),
            new Team(R.string.brooklyn_nets, R.string.brooklyn_nets_slug, R.string.nba, R.drawable.nba_brooklyn),
            new Team(R.string.charlotte_hornets, R.string.charlotte_hornets_slug, R.string.nba, R.drawable.nba_charlotte),
            new Team(R.string.chicago_bulls, R.string.chicago_bulls_slug, R.string.nba, R.drawable.nba_chicago),
            new Team(R.string.cleveland_cavaliers, R.string.cleveland_cavaliers_slug, R.string.nba, R.drawable.nba_cleveland),
            new Team(R.string.dallas_mavericks, R.string.dallas_mavericks_slug, R.string.nba, R.drawable.nba_dallas),
            new Team(R.string.denver_nuggets, R.string.denver_nuggets_slug, R.string.nba, R.drawable.nba_denver),
            new Team(R.string.detroit_pistons, R.string.detroit_pistons_slug, R.string.nba, R.drawable.nba_detroit),
            new Team(R.string.golden_state_warriors, R.string.golden_state_warriors_slug, R.string.nba, R.drawable.nba_goldenstate),
            new Team(R.string.houston_rockets, R.string.houston_rockets_slug, R.string.nba, R.drawable.nba_houston),
            new Team(R.string.indiana_pacers, R.string.indiana_pacers_slug, R.string.nba, R.drawable.nba_indiana),
            new Team(R.string.los_angeles_clippers, R.string.los_angeles_clippers_slug, R.string.nba, R.drawable.nba_losangelesclippers),
            new Team(R.string.los_angeles_lakers, R.string.los_angeles_lakers_slug, R.string.nba, R.drawable.nba_losangeleslakers),
            new Team(R.string.memphis_grizzlies, R.string.memphis_grizzlies_slug, R.string.nba, R.drawable.nba_memphis),
            new Team(R.string.miami_heat, R.string.miami_heat_slug, R.string.nba, R.drawable.nba_miami),
            new Team(R.string.milwaukee_bucks, R.string.milwaukee_bucks_slug, R.string.nba, R.drawable.nba_milwaukee),
            new Team(R.string.minnesota_timberwolves, R.string.minnesota_timberwolves_slug, R.string.nba, R.drawable.nba_minnesota),
            new Team(R.string.new_orleans_pelicans, R.string.new_orleans_pelicans_slug, R.string.nba, R.drawable.nba_neworleans),
            new Team(R.string.new_york_knicks, R.string.new_york_knicks_slug, R.string.nba, R.drawable.nba_newyork),
            new Team(R.string.oklahoma_city_thunder, R.string.oklahoma_city_thunder_slug, R.string.nba, R.drawable.nba_oklahoma),
            new Team(R.string.orlando_magic, R.string.orlando_magic_slug, R.string.nba, R.drawable.nba_orlando),
            new Team(R.string.philadelphia_76ers, R.string.philadelphia_76ers_slug, R.string.nba, R.drawable.nba_philadelphia),
            new Team(R.string.phoenix_suns, R.string.phoenix_suns_slug, R.string.nba, R.drawable.nba_phoenix),
            new Team(R.string.portland_trail_blazers, R.string.portland_trail_blazers_slug, R.string.nba, R.drawable.nba_portland),
            new Team(R.string.sacramento_kings, R.string.sacramento_kings_slug, R.string.nba, R.drawable.nba_sacramento),
            new Team(R.string.san_antonio_spurs, R.string.san_antonio_spurs_slug, R.string.nba, R.drawable.nba_sanantonio),
            new Team(R.string.toronto_raptors, R.string.toronto_raptors_slug, R.string.nba, R.drawable.nba_toronto),
            new Team(R.string.utah_jazz, R.string.utah_jazz_slug, R.string.nba, R.drawable.nba_utah),
            new Team(R.string.washington_wizards, R.string.washington_wizards_slug, R.string.nba, R.drawable.nba_washington)
    };
}