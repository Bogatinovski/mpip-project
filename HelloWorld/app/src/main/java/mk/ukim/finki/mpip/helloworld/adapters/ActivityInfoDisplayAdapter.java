package mk.ukim.finki.mpip.helloworld.adapters;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.adapters.holders.ActivityInfoItem;

/**
 * Created by ristes on 18.10.17.
 */

public class ActivityInfoDisplayAdapter extends Adapter<ActivityInfoItem> {

    private List<ResolveInfo> activities;
    private PackageManager packageManager;
    private LayoutInflater layoutInflater;
    private Context context;

    public static final String TAG = "AdapterCount";
    public static int createCalls = 0;
    public static int bindCalls = 0;

    public ActivityInfoDisplayAdapter(List<ResolveInfo> activities,
                                      Context context) {
        this.activities = activities;
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ActivityInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.acitivity_info_item, null);
        ActivityInfoItem holder = new ActivityInfoItem(rootView);
        holder.icon = rootView.findViewById(R.id.item_icon_image);
        holder.name = rootView.findViewById(R.id.item_name);
        holder.packageName = rootView.findViewById(R.id.item_package);
        holder.activity = rootView.findViewById(R.id.item_activity);

        createCalls++;
        Log.i(TAG, "create: " + createCalls);

        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityInfoItem holder, int position) {
        final ActivityInfo info = activities.get(position).activityInfo;

        holder.name.setText(info.applicationInfo.name);
        holder.packageName.setText(info.applicationInfo.packageName);
        holder.activity.setText(info.targetActivity);

        Drawable icon = info.applicationInfo.loadIcon(this.packageManager);
        holder.icon.setImageDrawable(icon);
        holder.icon.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            info.packageName,
                            info.targetActivity)
            );
            context.startActivity(intent);
        });


        bindCalls++;
        Log.i(TAG, "" + bindCalls);
    }


    @Override
    public int getItemCount() {
        return activities.size();
    }

}
