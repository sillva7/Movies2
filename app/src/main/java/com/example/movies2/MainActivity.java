package com.example.movies2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import data.Movie;
import utils.JSONUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.TOP_RATED, 3);//тест на правильность. НАЧАЛО
//        if (jsonObject == null){
//            Toast.makeText(this, "NEPRALNO", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "PRALNO", Toast.LENGTH_SHORT).show();
//        }//тест на правильность. КОНЕЦ

        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY, 5);
        Log.d("898989", "onCreate: "+jsonObject.toString());
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();
        for(Movie movie: movies){
            builder.append(movie.getTitle()).append("\n");
        }
        Log.d("494949", "onCreate: "+builder);


    }
}