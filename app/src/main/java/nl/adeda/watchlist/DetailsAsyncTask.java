package nl.adeda.watchlist;

import android.content.Context;
import android.provider.ContactsContract;
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    DataActivity dataAct;
    MovieData mvData;

    public DetailsAsyncTask(DataActivity main) {
        dataAct = main;
        context = dataAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //ProgressBar progBar = (ProgressBar) dataAct.findViewById(R.id.progressBar);
        //ListView listView = (ListView) dataAct.findViewById(R.id.resultList);
        //progBar.setVisibility(View.VISIBLE);
        //listView.setVisibility(View.GONE);

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

        mvData.title = "";
        mvData.year = "";
        mvData.runtime = "";
        mvData.director = "";
        mvData.actors = "";
        mvData.plot = "";
        mvData.poster = null;

        try {
            JSONObject streamObj = new JSONObject(result);

            mvData.title = streamObj.getString("Title");
            mvData.year = streamObj.getString("Year");
            mvData.runtime = streamObj.getString("Runtime");
            mvData.director = streamObj.getString("Director");
            mvData.actors = streamObj.getString("Actors");
            mvData.plot = streamObj.getString("Plot");
            mvData.poster = streamObj.getString("Poster");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.dataAct.dataStartIntent(mvData);

        //ProgressBar progBar = (ProgressBar) movieAct.findViewById(R.id.progressBar);
        //ListView listView = (ListView) movieAct.findViewById(R.id.resultList);
        //progBar.setVisibility(View.GONE);
       // listView.setVisibility(View.VISIBLE);
    }
}
