package kr.ac.doowon.healthmanageapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity(tableName = "target_kcal")
public class TargetKcal {
    @PrimaryKey
    @ColumnInfo(name = "date")
    @NonNull
    public String date;

    @ColumnInfo(name = "kcal")
    public int kcal;

    public TargetKcal setDate(String date) {
        this.date = date;
        return this;
    }

    public TargetKcal setKcal(int kcal) {
        this.kcal = kcal;
        return this;
    }
}

