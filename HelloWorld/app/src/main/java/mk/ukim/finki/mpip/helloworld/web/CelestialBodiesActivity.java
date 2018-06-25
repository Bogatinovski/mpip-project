package mk.ukim.finki.mpip.helloworld.web;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.adapters.CelestialBodyAdapter;
import mk.ukim.finki.mpip.helloworld.adapters.MoviesAdapter;
import mk.ukim.finki.mpip.helloworld.model.CelestialBodyListItem;
import mk.ukim.finki.mpip.helloworld.tasks.CelestialBodiesLoadingTask;
import mk.ukim.finki.mpip.helloworld.tasks.ICelestialBodiesCallbacks;
import mk.ukim.finki.mpip.helloworld.tasks.UniverseLoadingTask;

public class CelestialBodiesActivity extends AppCompatActivity implements ICelestialBodiesCallbacks {
    private TextView messageTextView;
    private RecyclerView recyclerView;
    private CelestialBodyAdapter adapter;
    private String bodyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celestial_bodies);

        Intent intent = getIntent();
        bodyType = intent.getStringExtra("celestialBodyType");

        initRecyclerView();


    }
    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.celestial_bodies);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CelestialBodyAdapter(this, getApplicationContext());
        recyclerView.setAdapter(adapter);

        CelestialBodiesLoadingTask task = new CelestialBodiesLoadingTask(this, bodyType);

        if(Build.VERSION.SDK_INT >= 11)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

    @Override
    public void setListData(List<CelestialBodyListItem> celestialBodies) {
        this.adapter.setData(celestialBodies);
    }
}
