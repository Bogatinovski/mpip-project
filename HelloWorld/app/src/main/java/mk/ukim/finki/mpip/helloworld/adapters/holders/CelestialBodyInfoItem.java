package mk.ukim.finki.mpip.helloworld.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dejan on 14.11.2017.
 */

public class CelestialBodyInfoItem extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView label;
    public TextView comment;

    public CelestialBodyInfoItem(View itemView) {
        super(itemView);
    }
}

