package com.example.movies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import data.Movie;
import utils.JSONUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movies;

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
//
//        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY, 5);
//        Log.d("898989", "onCreate: "+jsonObject.toString());
//        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
//        StringBuilder builder = new StringBuilder();
//        for(Movie movie: movies){
//            builder.append(movie.getTitle()).append("\n");
//        }
//        Log.d("494949", "onCreate: "+builder);

        recyclerView = findViewById(R.id.recyclerView);
        movieAdapter = new MovieAdapter(movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY,1);//получаем JSON объект в котором куча инфы о фильмах и сами фильмы
        movies = JSONUtils.getMoviesFromJSON(jsonObject);//получаем эту кучу фильмов в виде массива
        movieAdapter.setMovies(movies);//приплетаем этот массив к РесВью чз адаптерг
        recyclerView.setAdapter(movieAdapter);//устанавливаем сопсна сам адаптерг на РесВью



    }
}