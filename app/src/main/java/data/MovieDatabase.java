package data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {//синглтон делаем для БД

    private static final String DB_NAME = "movies.db";
    private static MovieDatabase database;
    private static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (LOCK) {//вроде, чтобы использовать этот кусок кода мог только один поток
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract MovieDao movieDao();


}
