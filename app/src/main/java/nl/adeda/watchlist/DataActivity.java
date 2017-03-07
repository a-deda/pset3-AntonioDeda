package nl.adeda.watchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DataActivity extends MainActivity {
    ListView lvItems;
    ArrayList<String> movieArray;
    DataActivity dataAct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lvItems = (ListView) findViewById(R.id.resultList);
        Bundle extras = getIntent().getExtras();
        movieArray = (ArrayList<String>) extras.getSerializable("data");

        if (savedInstanceState != null) {
            movieArray = savedInstanceState.getStringArrayList("movieArray");
        }

        makeMovieAdapter();



        lvItems.setOnItemClickListener(new ListView.OnItemClickListener() {;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = (String) lvItems.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                intent.putExtra("MOVIE", movie);
                DetailsAsyncTask asyncTask = new DetailsAsyncTask(dataAct);
                asyncTask.execute(movie);


                //startActivity(intent);
                //finish();
            }
        });
    }

    public void makeMovieAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, movieArray);
        assert lvItems != null;
        lvItems.setAdapter(arrayAdapter);
    }

    public void dataStartIntent(MovieData movieData) {
        Intent dataIntent = new Intent(this, MovieActivity.class);
        dataIntent.putExtra("data", movieData);
        this.startActivity(dataIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("movieArray", movieArray);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
