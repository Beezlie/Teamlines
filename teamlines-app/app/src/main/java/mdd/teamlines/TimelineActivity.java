package mdd.teamlines;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

public class TimelineActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //initialize the Twitter Kit API
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        //get the name of the twitter list - passed from selected team
        Bundle bundle = getIntent().getExtras();
        String twitterList = bundle.getString("twitterList");

        //create Twitter list timeline instance and display in a listview
        final TwitterListTimeline listTimeline = new TwitterListTimeline.Builder()
                .slugWithOwnerScreenName(twitterList, getString(R.string.twitter_list_owner))
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(listTimeline)
                .build();
        setListAdapter(adapter);
    }
}