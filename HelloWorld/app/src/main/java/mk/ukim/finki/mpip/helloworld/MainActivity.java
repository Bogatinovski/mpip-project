package mk.ukim.finki.mpip.helloworld;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import mk.ukim.finki.mpip.helloworld.model.CelestialBody;
import mk.ukim.finki.mpip.helloworld.model.Universe;
import mk.ukim.finki.mpip.helloworld.tasks.IUniverseCallbacks;
import mk.ukim.finki.mpip.helloworld.tasks.UniverseLoadingTask;
import mk.ukim.finki.mpip.helloworld.web.CelestialBodiesActivity;

public class MainActivity extends AppCompatActivity implements IUniverseCallbacks {

    public static final String LIFECYCLE = "LIFECYCLE";
    public static final String CELESTIAL_BODY_TYPE_SATELLITE = "Satellite";
    public static final String CELESTIAL_BODY_TYPE_ASTEROID = "Asteroid";
    public static final String CELESTIAL_BODY_TYPE_CONSTELLATION = "Constellation";
    public static final String CELESTIAL_BODY_TYPE_GALAXY = "Galaxy";
    public static final String CELESTIAL_BODY_TYPE_PLANET = "Planet";
    public static final String CELESTIAL_BODY_TYPE_STAR = "Star";

    private ImageView imageView;
    private TextView textView;
    private ImageView satelliteImageView;
    private TextView satelliteNameTextView;
    private TextView satelliteCommentTextView;
    private ImageView asteroidImageView;
    private TextView asteroidNameTextView;
    private TextView asteroidCommentTextView;
    private ImageView constellationImageView;
    private TextView constellationNameTextView;
    private TextView constellationCommentTextView;
    private ImageView galaxyImageView;
    private TextView galaxyNameTextView;
    private TextView galaxyCommentTextView;
    private ImageView planetImageView;
    private TextView planetNameTextView;
    private TextView planetCommentTextView;
    private ImageView starImageView;
    private TextView starNameTextView;
    private TextView starCommentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.item_icon_image);
        textView = (TextView)findViewById(R.id.textView);

        satelliteImageView = (ImageView)findViewById(R.id.category1_image);
        satelliteNameTextView = (TextView)findViewById(R.id.category1_name);
        satelliteCommentTextView = (TextView)findViewById(R.id.category1_comment);

        asteroidImageView = (ImageView)findViewById(R.id.category2_image);
        asteroidNameTextView = (TextView)findViewById(R.id.category2_name);
        asteroidCommentTextView = (TextView)findViewById(R.id.category2_comment);

        constellationImageView = (ImageView)findViewById(R.id.category3_image);
        constellationNameTextView = (TextView)findViewById(R.id.category3_name);
        constellationCommentTextView = (TextView)findViewById(R.id.category3_comment);

        galaxyImageView = (ImageView)findViewById(R.id.category4_image);
        galaxyNameTextView = (TextView)findViewById(R.id.category4_name);
        galaxyCommentTextView = (TextView)findViewById(R.id.category4_comment);

        planetImageView = (ImageView)findViewById(R.id.category5_image);
        planetNameTextView = (TextView)findViewById(R.id.category5_name);
        planetCommentTextView = (TextView)findViewById(R.id.category5_comment);

        starImageView = (ImageView)findViewById(R.id.category6_image);
        starNameTextView = (TextView)findViewById(R.id.category6_name);
        starCommentTextView = (TextView)findViewById(R.id.category6_comment);

        UniverseLoadingTask task = new UniverseLoadingTask(this);

        if(Build.VERSION.SDK_INT >= 11)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(LIFECYCLE, "onStart");
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(LIFECYCLE, "onResume");
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LIFECYCLE, "onPause");
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(LIFECYCLE, "onStop");
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LIFECYCLE, "onDestroy");
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LIFECYCLE, "onRestart");
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(LIFECYCLE, "onSaveInstanceState");
        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUniverseData(Universe universe) {
        textView.setText(universe.Abstract);
        Log.i("MainActivity", universe.Abstract);

        CelestialBody satellite = universe.Categories.get(0);
        Picasso.with(this).load(satellite.Thumbnail).placeholder(R.mipmap.ic_launcher).into(satelliteImageView);
        satelliteNameTextView.setText(satellite.Name);
        satelliteCommentTextView.setText(satellite.Comment);
        satelliteImageView.setOnClickListener(new SatelliteOnClickListener());

        CelestialBody asteroid = universe.Categories.get(1);
        Picasso.with(this).load(asteroid.Thumbnail).placeholder(R.mipmap.ic_launcher).into(asteroidImageView);
        asteroidNameTextView.setText(asteroid.Name);
        asteroidCommentTextView.setText(asteroid.Comment);
        asteroidImageView.setOnClickListener(new AsteroidOnClickListener());

        CelestialBody constellation = universe.Categories.get(2);
        Picasso.with(this).load(constellation.Thumbnail).placeholder(R.mipmap.ic_launcher).into(constellationImageView);
        constellationNameTextView.setText(constellation.Name);
        constellationCommentTextView.setText(constellation.Comment);
        constellationImageView.setOnClickListener(new ConstellationOnClickListener());

        CelestialBody galaxy = universe.Categories.get(3);
        Picasso.with(this).load(galaxy.Thumbnail).placeholder(R.mipmap.ic_launcher).into(galaxyImageView);
        galaxyNameTextView.setText(galaxy.Name);
        galaxyCommentTextView.setText(galaxy.Comment);
        galaxyImageView.setOnClickListener(new GalaxyOnClickListener());

        CelestialBody planet = universe.Categories.get(4);
        Picasso.with(this).load(planet.Thumbnail).placeholder(R.mipmap.ic_launcher).into(planetImageView);
        planetNameTextView.setText(planet.Name);
        planetCommentTextView.setText(planet.Comment);
        planetImageView.setOnClickListener(new PlanetOnClickListener());

        CelestialBody star = universe.Categories.get(5);
        Picasso.with(this).load(star.Thumbnail).placeholder(R.mipmap.ic_launcher).into(starImageView);
        starNameTextView.setText(star.Name);
        starCommentTextView.setText(star.Comment);
        starImageView.setOnClickListener(new StarOnClickListener());
    }

    private class CelestialBodyOnClickListener implements View.OnClickListener
    {
        protected String celestialBodyType;

        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, CelestialBodiesActivity.class);
            myIntent.putExtra("celestialBodyType", celestialBodyType);
            MainActivity.this.startActivity(myIntent);
        }
    }

    private class SatelliteOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_SATELLITE;
            super.onClick(v);
        }
    }

    private class AsteroidOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_ASTEROID;
            super.onClick(v);
        }
    }

    private class ConstellationOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_CONSTELLATION;
            super.onClick(v);
        }
    }

    private class GalaxyOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_GALAXY;
            super.onClick(v);
        }
    }

    private class PlanetOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_PLANET;
            super.onClick(v);
        }
    }

    private class StarOnClickListener extends CelestialBodyOnClickListener
    {
        @Override
        public void onClick(View v) {
            celestialBodyType = CELESTIAL_BODY_TYPE_STAR;
            super.onClick(v);
        }
    }
}
