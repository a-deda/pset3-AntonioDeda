package nl.adeda.watchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestHelper extends AppCompatActivity {

    protected static synchronized String downloadFromServer(String... params) throws MalformedURLException {
        String result = "";
        String chosenTag = params[0];

        String className = new Exception().getStackTrace()[1].getClassName();
        String urlString = "";

        if (className.equals("nl.adeda.watchlist.MovieAsyncTask")) {
            urlString = "http://www.omdbapi.com/?s=" + chosenTag;
        }
        else if (className.equals("nl.adeda.watchlist.DetailsAsyncTask")){
            urlString = "http://www.omdbapi.com/?t=" + chosenTag;
        }
        URL url = new URL(urlString);

        HttpURLConnection connect;

        if (url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responseCode = connect.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return result;
    }
}
