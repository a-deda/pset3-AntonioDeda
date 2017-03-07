package nl.adeda.watchlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Watchlist extends AppCompatActivity implements Serializable {
    public static ArrayList<String> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        SharedPreferences savedList = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set movieHashSet = savedList.getStringSet("watchlist", null);
        movieList = new ArrayList<String>(movieHashSet);

        ArrayAdapter<String> watchlistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movieList);

        final ListView watchlistView = (ListView) findViewById(R.id.watchList);
        watchlistView.setAdapter(watchlistAdapter);

        watchlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = (String) watchlistView.getItemAtPosition(position);
                movieList.remove(movie);
                Toast.makeText(Watchlist.this, "Deleted from watchlist", Toast.LENGTH_SHORT).show();

                ArrayAdapter<String> watchlistAdapter = new ArrayAdapter<String>(Watchlist.this, android.R.layout.simple_list_item_1, movieList);
                watchlistView.setAdapter(watchlistAdapter);

                SharedPreferences.Editor sharedPrefsEditor = PreferenceManager.getDefaultSharedPreferences(Watchlist.this).edit();
                HashSet movieHashSet = new HashSet(movieList);
                sharedPrefsEditor.putStringSet("watchlist", movieHashSet);
                sharedPrefsEditor.commit();

                return true;
            }


        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}