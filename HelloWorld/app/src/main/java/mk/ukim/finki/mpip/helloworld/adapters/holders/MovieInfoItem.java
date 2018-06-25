package mk.ukim.finki.mpip.helloworld.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dejan on 14.11.2017.
 */

public class MovieInfoItem extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView name;
    public TextView packageName;
    public TextView activity;

    public MovieInfoItem(View itemView) {
        super(itemView);
    }
}

