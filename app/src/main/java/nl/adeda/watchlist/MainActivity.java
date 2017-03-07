package nl.adeda.watchlist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText searchMovie;
    Button searchButton;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchMovie = (EditText) findViewById(R.id.searchBar);
        assert searchMovie != null;

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.floatingButton);
        fab.setOnClickListener(this);
    }

    public void movieSearch(View view) {
        String movieSearch = searchMovie.getText().toString();
        searchMovie.setText("");
        MovieAsyncTask asyncTask = new MovieAsyncTask(this);
        asyncTask.execute(movieSearch);
    }

    public void movieStartIntent(ArrayList<String> movieData) {
        Intent dataIntent = new Intent(this, DataActivity.class);
        dataIntent.putExtra("data", movieData);
        this.startActivity(dataIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                movieSearch(searchMovie);
                break;
            case R.id.floatingButton:
                Intent openWatchlist = new Intent(this, Watchlist.class);
                startActivity(openWatchlist);
        }

    }
}
