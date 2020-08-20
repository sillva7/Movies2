package com.example.movies2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import data.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;//для щелчка по постер в РесВью
    private OnReachEndListener onReachEndListener;//при достижении конца списка

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {//для щелчка по постер в РесВью
        this.onPosterClickListener = onPosterClickListener;
    }


    interface OnPosterClickListener {//для щелчка по постер в РесВью

        void onPosterClick(int position);
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {//при достижении конца списка
        this.onReachEndListener = onReachEndListener;
    }

    interface OnReachEndListener {//при достижении конца списка
        void onReachEnd();
    }

    public List<Movie> getMovies() {
        movies = new ArrayList<>();
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();//для отслеживания изменения
    }

    public MovieAdapter(List<Movie> movies) {
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
        if( position == movies.size() - 1 && onReachEndListener != null){//при достижении конца списка. А точнее его конкретной позиции
            onReachEndListener.onReachEnd();
        }
        Movie movie = movies.get(position);//нужен для знания адреса постера
        ImageView imageView = holder.smallPoster;//элемент в РесВью,а ещё точнее - ИВ лайаута, который будет наполнять РесВью
        Picasso.get().load(movie.getPosterPath()).into(imageView);//в ИВ загружаем путь постера из Муви
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<Movie> movies) {//Чтобы при добавлении не переделывать целлый массив, а просто добавлять в существующий:
        this.movies.addAll(movies);
        notifyDataSetChanged();//для отслеживания изменения

    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView smallPoster;//movie_item layout сделали жи. Вот РесВью будет состоять из них

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            smallPoster = itemView.findViewById(R.id.smallPosterForRecView);//itemView находит по пакету в лайаутах этот ИД?
            itemView.setOnClickListener(new View.OnClickListener() {//для щелчка по постеру в РесВью
                @Override
                public void onClick(View view) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());//для щелчка по постеру в РесВью
                    }
                }
            });
        }


    }

}
