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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        setSportView(mlbteams);
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
                return true;
            case R.id.hockey:
                setSportView(nhlteams);
                return true;
            case R.id.basketball:
                return true;
            case R.id.football:
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
}