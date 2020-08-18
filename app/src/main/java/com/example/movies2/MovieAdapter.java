package com.example.movies2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import data.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public MovieAdapter() {
    }

    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        movies = new ArrayList<>();
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();//для отслеживания изменения
    }

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()/*берём контекст этой вьюГруп*/).inflate(R.layout.movie_item, parent, false);//метод по надутию лайаута? типо при создании этого об.эт.кл. будет задействован этот лайаут? пролли
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {//не хватает конкретики по работе.
        Movie movie = movies.get(position);//нужен для знания адреса постера
        ImageView imageView = holder.smallPoster;//элемент в РесВью,а ещё точнее - ИВ лайаута, который будет наполнять РесВью
        Picasso.get().load(movie.getPosterPath()).into(imageView);//в ИВ загружаем путь постера из Муви
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(ArrayList<Movie> movies) {//Чтобы при добавлении не переделывать целлый массив, а просто добавлять в существующий:
        this.movies.addAll(movies);
        notifyDataSetChanged();//для отслеживания изменения

    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView smallPoster;//movie_item layout сделали жe. Вот РесВью будет состоять из них

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            smallPoster = itemView.findViewById(R.id.smallPosterForRecView);//itemView находит по пакету в лайаутах этот ИД?
        }
    }

}
