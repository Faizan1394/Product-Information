package com.example.faizan.assignment2;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by faizan on 15/11/17.
 */

public class downloadBitPrice extends AsyncTask<URL, Void, Double> {

    BrowseProductsActivity browseActivity;

    public downloadBitPrice(BrowseProductsActivity browseActivity){
        this.browseActivity = browseActivity;
    }

    @Override
    protected Double doInBackground(URL... urls) {
        String strLine = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();

            int result = conn.getResponseCode();
            if(result == HttpURLConnection.HTTP_OK){
                InputStream in = conn.getInputStream();
                BufferedReader dataIO = new BufferedReader(new InputStreamReader(in));
                strLine = dataIO.readLine();
                in.close();
                conn.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(strLine);
    }

    @Override
    protected void onPostExecute(Double s) {
        super.onPostExecute(s);
        browseActivity.bitPrice(s);
    }

}
