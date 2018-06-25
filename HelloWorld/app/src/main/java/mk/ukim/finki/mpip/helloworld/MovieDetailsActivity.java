package mk.ukim.finki.mpip.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import mk.ukim.finki.mpip.helloworld.model.MovieDetails;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.tasks.IMovieCallbacks;
import mk.ukim.finki.mpip.helloworld.tasks.MovieDetailsLoadingTask;

import java.util.concurrent.ExecutionException;

public class MovieDetailsActivity extends AppCompatActivity implements IMovieCallbacks {

    private TextView textDvd;
    private TextView textRating;
    private TextView textTitle;
    private TextView textYear;
    private TextView textVotes;
    private TextView textImdbid;
    private TextView textRated;
    private TextView textAwards;
    private TextView textReleased;
    private TextView textRuntime;
    private TextView textGenre;
    private TextView textDirector;
    private TextView textWriter;
    private TextView textActors;
    private TextView textPlot;
    private TextView textLanguage;
    private TextView textCountry;
    private Button shareBtn;

    private MovieDetails movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        textDvd = (TextView)findViewById(R.id.textDvd);
        textRating = (TextView)findViewById(R.id.textRating);
        textTitle = (TextView)findViewById(R.id.textTitle);
        textYear = (TextView)findViewById(R.id.textYear);
        textVotes = (TextView)findViewById(R.id.textVotes);
        textImdbid = (TextView)findViewById(R.id.textImdbid);
        textRated = (TextView)findViewById(R.id.textRated);
        textAwards = (TextView)findViewById(R.id.textAwards);
        textReleased = (TextView)findViewById(R.id.textReleased);
        textRuntime = (TextView)findViewById(R.id.textRuntime);
        textGenre = (TextView)findViewById(R.id.textGenre);
        textDirector = (TextView)findViewById(R.id.textDirector);
        textWriter = (TextView)findViewById(R.id.textWriter);
        textActors = (TextView)findViewById(R.id.textActors);
        textPlot = (TextView)findViewById(R.id.textPlot);
        textLanguage = (TextView)findViewById(R.id.textLanguage);
        textCountry = (TextView)findViewById(R.id.textCountry);

        shareBtn = (Button)findViewById(R.id.shareMovieBtn);
        String imdbId = getIntent().getStringExtra("imdbId");
        MovieDetailsLoadingTask task = new MovieDetailsLoadingTask(this, imdbId);
        task.execute();
    }

    @Override
    public void setMovieDetails(MovieDetails movie) {
        this.movie = movie;
        Log.d("MOVIE DETAILS", movie.Title);
        textDvd.setText(movie.DVD);
        textRating.setText(movie.imdbRating);
        textTitle.setText(movie.Title);
        textYear.setText(movie.Year);
        textVotes.setText(movie.imdbVotes);
        textImdbid.setText(movie.ImdbID);
        textRated.setText(movie.Rated);
        textAwards.setText(movie.Awards);
        textReleased.setText(movie.Released);
        textRuntime.setText(movie.Runtime);
        textGenre.setText(movie.Genre);
        textDirector.setText(movie.Director);
        textWriter.setText(movie.Writer);
        textActors.setText(movie.Actors);
        textPlot.setText(movie.Plot);
        textLanguage.setText(movie.Language);
        textCountry.setText(movie.Country);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        MenuItem item = menu.findItem(R.id.shareMovieBtn);
        item.setOnMenuItemClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, this.movie.Plot);
            intent.putExtra(Intent.EXTRA_SUBJECT, this.movie.Title);
            intent.setType("text/plain");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;
        });


        // Return true to display menu
        return true;
    }
}
