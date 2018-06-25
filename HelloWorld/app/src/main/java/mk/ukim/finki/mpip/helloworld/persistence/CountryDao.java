package mk.ukim.finki.mpip.helloworld.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.Country;

/**
 * Created by ristes on 15.11.17.
 */
@Dao
public interface CountryDao {

    @Query("SELECT * FROM countries c ORDER BY c.name")
    List<Country> getAll();

    @Insert
    void insert(Country users);

    @Insert
    void insertAll(Country... users);

    @Update
    void update(Country users);

    @Delete
    void delete(Country user);

    @Query("DELETE FROM countries")
    void deleteAll();

    @Query("SELECT * FROM countries c WHERE c.name=:countryName")
    Country getByName(String countryName);
}
