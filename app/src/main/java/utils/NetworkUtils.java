package utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import data.Movie;

public class NetworkUtils {
    private static String API_KEY = "a687e2e3f4e0d2806d76dcc7802dd7a3";
    private static String BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    private static String PARAMS_API_KEY = "api_key"; // параметры, которые нам понадобятся
    private static String PARAMS_LANGUAGE = "language";
    private static String PARAMS_SORT_BY = "sort_by";
    private static String PARAMS_PAGE = "page";

    private static String LANGUAGE_VALUE = "ru-RU";//значения этих параметров
    private static String SORT_BY_POPULARITY = "popularity.desc";
    private static String SORT_BY_TOP_RATED = "vote_average.desc";

    public static final int POPULARITY = 0;//для сортировки
    public static final int TOP_RATED = 1;


    public static URL buildURL(int sortBy, int page){ // методо для построения запроса и получения JSONа. ну всю вот эту табличную поеботу получаем
        URL result = null;
        String methodOfSort=null;
        if(sortBy == POPULARITY){
            methodOfSort = SORT_BY_POPULARITY;
        }else if(sortBy==TOP_RATED){
            methodOfSort = SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page)).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject>{ //для загрузки информации из интернета

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if(urls == null || urls.length == 0){
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();//wodim ddanie
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String listener = reader.readLine();
                while(listener!=null){
                    builder.append(listener);
                    listener=reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            return result;
        }
    }

    public static JSONObject getJSONFromNetwork(int sortBy, int page){//инфа с интернета т.е. получаем JSON
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result=new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }




}

