package nl.adeda.watchlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MovieAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;

    public MovieAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progBar = (ProgressBar) mainAct.findViewById(R.id.progressBar);
        ListView listView = (ListView) mainAct.findViewById(R.id.resultList);
        progBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpRequestHelper.downloadFromServer(params);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ArrayList<String> allTitles = new ArrayList<String>();
        try {
            JSONObject streamObj = new JSONObject(result);
            JSONArray jsonMovies = streamObj.getJSONArray("Search");
            for (int i = 0; i < jsonMovies.length(); i++) {
                JSONObject movieTitle = jsonMovies.getJSONObject(i);
                String title = movieTitle.getString("Title");
                allTitles.add(title);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mainAct.movieStartIntent(allTitles);
        ProgressBar progBar = (ProgressBar) mainAct.findViewById(R.id.progressBar);
        ListView listView = (ListView) mainAct.findViewById(R.id.resultList);
        progBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }


}
