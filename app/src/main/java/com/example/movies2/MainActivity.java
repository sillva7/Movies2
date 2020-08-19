package com.example.movies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import data.Movie;
import utils.JSONUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;

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
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);

        recyclerView = findViewById(R.id.recyclerView);
        movieAdapter = new MovieAdapter(movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter.setMovies(movies);//приплетаем этот массив к РесВью чз адаптерг
        recyclerView.setAdapter(movieAdapter);//устанавливаем сопсна сам адаптерг на РесВью


        //SWITCH:
        switchSort = findViewById(R.id.switchSort);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setMethodOfSort(b);
            }
        });
        switchSort.setChecked(false);
        //SWITCH;
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {//при достижении конца списка
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "END", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(movieAdapter);//устанавливаем сопсна сам адаптерг на РесВью


    }



    public void setMethodOfSort(boolean isTopRated) { // этот метод для того чтобы, в зависимости от положения свитча, формировался ДЖСОН объект с характерной сортировкой
        int methodOfSort;
        if (isTopRated) {
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            methodOfSort = NetworkUtils.POPULARITY;

            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.yellow));
        }
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);//получаем JSON объект в котором куча инфы о фильмах и сами фильмы
        movies = JSONUtils.getMoviesFromJSON(jsonObject);//получаем эту кучу фильмов в виде массива
        movieAdapter.setMovies(movies);//приплетаем этот массив к РесВью чз адаптерг
    }

    public void OnClickSetPopularity(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(false);

    }

    public void OnClickSetTopRated(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(true);

    }
}