package mk.ukim.finki.mpip.helloworld.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Dejan on 14.11.2017.
 */

@Entity(tableName = "movies")
public class MovieSearchEntry
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int Id;

    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "year")
    public String Year;

    @ColumnInfo(name = "poster")
    public String Poster;

    @ColumnInfo(name = "imdbId")
    public String imdbID;
}
