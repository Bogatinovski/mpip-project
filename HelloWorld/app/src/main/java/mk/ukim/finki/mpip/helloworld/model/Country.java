package mk.ukim.finki.mpip.helloworld.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ristes on 25.10.17.
 */
@Entity(tableName = "countries")
public class Country {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo
    public String capital;

    @ColumnInfo
    public String region;

    @ColumnInfo
    public String flag;
}
