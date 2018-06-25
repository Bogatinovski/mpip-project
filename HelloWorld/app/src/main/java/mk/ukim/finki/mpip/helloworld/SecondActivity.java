package mk.ukim.finki.mpip.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    public static final String LIFECYCLE = "SECOND_LIFECYCLE";
    public static final String STARTED_COUNT = "started_count";
    private static final int PICK_CONTACT_REQUEST = 1;
    private static final int PICK_GALERY_REQUEST = 2;

    private int startedCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView tv = (TextView) findViewById(R.id.display_text);

        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            ArrayList<String> list = new ArrayList();
            for (int i = 0; i < 10000; i++) {
                list.add("lasdjlasdjflasdjasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsdafdasfasdfasdfasdfflasdjflasdjfljasdf");
                if (i % 100 == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.i(LIFECYCLE, "done creating strings");
            Intent startSecondAcitivityAgain = new Intent(SecondActivity.this, MainActivity.class);
            startSecondAcitivityAgain.putExtra(STARTED_COUNT, startedCount);
            SecondActivity.this.startActivity(startSecondAcitivityAgain);
            /*
                Default launchMode:
                SA
                SA
                SA
                SA
                FA
             */

            /*
                launchMode: singleTop
                SA
                FA
             */

            /*
                launchMode: SA -> singleTask
                impossible scenario:
                SA
                FA
                SA
             */
        });
        Log.i(LIFECYCLE, "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startedCount = intent.getIntExtra(STARTED_COUNT, 0);
        startedCount++;

        Log.i(LIFECYCLE, "onNewIntent");
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectContact(View view) {

        // When the user center presses, let them pick a contact.
        Intent pickContact = new Intent(Intent.ACTION_PICK);
        pickContact.setData(Uri.parse("content://contacts"));

        startActivityForResult(
                pickContact,
                PICK_CONTACT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                startActivity(
                        new Intent(Intent.ACTION_VIEW, data.getData())
                );
            }
        }
    }
}
