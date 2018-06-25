package mk.ukim.finki.mpip.helloworld.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;

/**
 * Created by Dejan on 17.12.2017.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies m ORDER BY m.title")
    List<MovieSearchEntry> getAll();

    @Insert
    void insert(MovieSearchEntry movie);

    @Insert
    void insertAll(MovieSearchEntry... movies);

    @Update
    void update(MovieSearchEntry movie);

    @Delete
    void delete(MovieSearchEntry movie);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("SELECT * FROM movies m WHERE m.title=:movieTitle")
    MovieSearchEntry getByTitle(String movieTitle);

    @Query("SELECT * FROM movies m WHERE m.imdbId=:imdbId")
    MovieSearchEntry getByImdbId(String imdbId);
}
