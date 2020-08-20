package data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    //внизу будут методы манипуляции с БД
    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {//для получения данных

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    public Movie getMovieById(int id) {//для получения данных. Это сам метод
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DeleteMoviesTask extends AsyncTask<Void, Void, Void> {//удаление. асинхронные таск

        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    public void deleteAllMovies() {
        new DeleteMoviesTask().execute();
    }//сам метод удаления

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {//Асинк таск для метода вставки

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    public void insertMovie(Movie movie) {//сам метод
        new InsertMovieTask().execute(movie);
    }

    private static class DeleteTask extends AsyncTask<Movie, Void, Void> {//для удаления отдельно взятого фильма

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    public void deleteMovie(Movie movie) {//сам метод
        new DeleteTask().execute(movie);
    }
}
