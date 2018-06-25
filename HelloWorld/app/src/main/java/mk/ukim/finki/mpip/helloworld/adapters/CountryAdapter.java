package mk.ukim.finki.mpip.helloworld.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import mk.ukim.finki.mpip.helloworld.model.Country;

/**
 * Created by ristes on 18.10.17.
 */

public class CountryAdapter extends Adapter<ActivityInfoItem> {

    private List<Country> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public CountryAdapter(Context context) {

        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ActivityInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.acitivity_info_item, null);
        ActivityInfoItem holder = new ActivityInfoItem(rootView);
        holder.parent = rootView;
        holder.icon = rootView.findViewById(R.id.item_icon_image);
        holder.name = rootView.findViewById(R.id.item_name);
        holder.packageName = rootView.findViewById(R.id.item_package);
        holder.activity = rootView.findViewById(R.id.item_activity);
        rootView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityInfoItem holder, int position) {
        final Country country = data.get(position);

        holder.name.setText(country.name);
        holder.packageName.setText(country.region);
        holder.activity.setText(country.capital);

        // TODO: Picasso does not support svg images. This should be fixed!
        Picasso
                .with(context)
                .load(country.flag)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.icon);

        holder.parent.setOnClickListener(v->{
            Intent showMap =new Intent(context, CountryDetails.class);
            showMap.putExtra("country", country.name);
            context.startActivity(showMap);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Country> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
