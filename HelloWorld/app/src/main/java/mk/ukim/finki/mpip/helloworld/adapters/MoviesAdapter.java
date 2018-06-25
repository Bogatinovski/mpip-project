package mk.ukim.finki.mpip.helloworld.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.ukim.finki.mpip.helloworld.MovieDetailsActivity;
import mk.ukim.finki.mpip.helloworld.R;

import mk.ukim.finki.mpip.helloworld.adapters.holders.MovieInfoItem;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dejan on 14.11.2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieInfoItem>
{
    private List<MovieSearchEntry> movies;
    private Context context;
    private LayoutInflater layoutInflater;

    public MoviesAdapter(Context context)
    {
        Log.d("ADAPTER", "CONSTRUCTOR");
        this.movies = new ArrayList<MovieSearchEntry>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.movie_info_item, null);
        MovieInfoItem holder = new MovieInfoItem(rootView);

        holder.icon = rootView.findViewById(R.id.item_icon_image);
        holder.name = rootView.findViewById(R.id.item_name);
        holder.packageName = rootView.findViewById(R.id.item_package);
        holder.activity = rootView.findViewById(R.id.item_activity);
        rootView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieInfoItem holder, final int position) {
        MovieSearchEntry movie = movies.get(position);
        holder.name.setText(movie.Title);
        holder.packageName.setText(movie.Year);

        Picasso
                .with(context)
                .load(movie.Poster)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.icon);

        holder.icon.setOnClickListener(view -> {
            Intent explicitIntent = new Intent(context, MovieDetailsActivity.class);
            explicitIntent.putExtra("imdbId", movie.imdbID);
            context.startActivity(explicitIntent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setData(MoviesSearchResult result)
    {
        this.movies = result.Search;
    }
}
