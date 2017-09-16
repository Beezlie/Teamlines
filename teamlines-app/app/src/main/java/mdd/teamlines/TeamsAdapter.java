package mdd.teamlines;

/**
 * Created by matt on 9/15/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamsAdapter extends BaseAdapter {

    private final Context mContext;
    private final Team[] teams;

    // 1
    public TeamsAdapter(Context context, Team[] teams) {
        this.mContext = context;
        this.teams = teams;
    }

    // 2
    @Override
    public int getCount() {
        return teams.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Team team = teams[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_team, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_team_logo);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_team_name);

        // 4
        imageView.setImageResource(team.getImageResource());
        nameTextView.setText(mContext.getString(team.getName()));

        return convertView;
    }

}