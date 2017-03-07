package nl.adeda.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MovieActivity extends AppCompatActivity {

    MovieData movieData;
    SharedPreferences sharedPreferences;
    int enabledBtn;
    Watchlist watchlist = new Watchlist();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Serializable movieDataSerialized = intent.getSerializableExtra("data");
        movieData = (MovieData) movieDataSerialized;

        setContentView(R.layout.activity_movie);

        TextView titleText = (TextView) findViewById(R.id.movieTitle);
        TextView yearText = (TextView) findViewById(R.id.movieYear);
        TextView directorText = (TextView) findViewById(R.id.movieDirector);
        TextView actorsText = (TextView) findViewById(R.id.movieActors);
        TextView runtimeText = (TextView) findViewById(R.id.movieRuntime);
        TextView plotText = (TextView) findViewById(R.id.moviePlot);
        ImageView posterImage = (ImageView) findViewById(R.id.posterImage);

        titleText.setText(movieData.title);
        yearText.setText(movieData.year);
        directorText.setText(movieData.director);
        actorsText.setText(movieData.actors);
        runtimeText.setText(movieData.runtime);
        plotText.setText(movieData.plot);

        if (movieData.poster.equals("N/A")) {
            posterImage.setImageResource(R.drawable.noimg);
        } else {
            Picasso.with(this).load(movieData.poster).into(posterImage);
        }

        Button watchListButton = (Button) findViewById(R.id.watchList);

        if (watchlist.movieList != null) {
            if (watchlist.movieList.size() > 0) {
                if (watchlist.movieList.contains(movieData.title)) {
                    watchListButton.setEnabled(false);
                    watchListButton.setText("Added");
                }
            }

        }

        if (savedInstanceState != null) {
            enabledBtn = savedInstanceState.getInt("enabledBtn");

            if (enabledBtn == 0) {
                watchListButton.setEnabled(false);
                watchListButton.setText("Added");
            }

            else if (enabledBtn == 1) {
                watchListButton.setEnabled(true);
            }
        }



        sharedPreferences = getSharedPreferences("watchList", this.MODE_PRIVATE);

    }

    public void onClick(View v) {

        String movieTitle = movieData.title;
        SharedPreferences savedList = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set completeMovieHashSet = savedList.getStringSet("watchlist", null);
        ArrayList<String> movieList = new ArrayList<String>(completeMovieHashSet);
        movieList.add(movieTitle);
        Button watchListButton = (Button) findViewById(R.id.watchList);
        watchListButton.setText("Added");
        watchListButton.setEnabled(false);
        enabledBtn = 0;

        SharedPreferences.Editor sharedPrefsEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        HashSet movieHashSet = new HashSet(movieList);
        sharedPrefsEditor.putStringSet("watchlist", movieHashSet);
        sharedPrefsEditor.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("enabledBtn", enabledBtn);
        super.onSaveInstanceState(outState);
    }

}




