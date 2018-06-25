package mk.ukim.finki.mpip.helloworld.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.CountryDetails;
import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.adapters.holders.ActivityInfoItem;
import mk.ukim.finki.mpip.helloworld.adapters.holders.CelestialBodyInfoItem;
import mk.ukim.finki.mpip.helloworld.model.CelestialBodyListItem;
import mk.ukim.finki.mpip.helloworld.model.Country;

/**
 * Created by ristes on 18.10.17.
 */

public class CelestialBodyAdapter extends Adapter<CelestialBodyInfoItem> {

    private List<CelestialBodyListItem> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private Context applicationContext;

    public CelestialBodyAdapter(Context context, Context applicationContext) {

        this.context = context;
        this.applicationContext = applicationContext;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CelestialBodyInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.celestial_body_info_item, null);
        CelestialBodyInfoItem holder = new CelestialBodyInfoItem(rootView);

        holder.comment = rootView.findViewById(R.id.item_comment);
        holder.label = rootView.findViewById(R.id.item_label);
        holder.thumbnail = rootView.findViewById(R.id.item_thumbnail);

        rootView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(CelestialBodyInfoItem holder, int position) {
        CelestialBodyListItem item = data.get(position);

        holder.label.setText(item.Label);
        holder.comment.setText(item.Comment);

        Picasso.with(applicationContext).load("http://www.iconarchive.com/download/i66387/iconshow/transport/Sportscar-car-2.ico")
        .placeholder(R.mipmap.ic_launcher).noFade().into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<CelestialBodyListItem> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
