package ar.com.emsoft.salvaguarda.service.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by augustopinto on 11/21/17.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String DATA_FORMAT = "json";

    public static URL buildUrl(final String connectionUrl) {
        Uri buildUri = Uri.parse(connectionUrl).buildUpon()
                .appendQueryParameter("mode", DATA_FORMAT)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) {
        String response = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                response = scanner.next();
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
