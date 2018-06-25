package mk.ukim.finki.mpip.helloworld;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import mk.ukim.finki.mpip.helloworld.model.Universe;
import mk.ukim.finki.mpip.helloworld.tasks.IUniverseCallbacks;
import mk.ukim.finki.mpip.helloworld.tasks.UniverseLoadingTask;

public class MainActivity extends AppCompatActivity implements IUniverseCallbacks {

    public static final String LIFECYCLE = "LIFECYCLE";
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.item_icon_image);
        textView = (TextView)findViewById(R.id.textView);

        UniverseLoadingTask task = new UniverseLoadingTask(this);
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
        textView.setText(universe.Abstrat);
        Picasso
                .with(this)
                .load(universe.Depiction)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
