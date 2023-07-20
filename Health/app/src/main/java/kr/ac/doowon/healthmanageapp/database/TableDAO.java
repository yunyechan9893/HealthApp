package kr.ac.doowon.healthmanageapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;
import kr.ac.doowon.healthmanageapp.database.TargetKcal;

public class TableDAO {
    @Dao
    public interface DietDAO{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insert(Diet... diet);

        @Update
        void update(Diet diet);

        @Delete
        void delete(Diet diet);

        @Query("DELETE FROM diet")
        Completable deleteTable();

        @Query("SELECT * FROM diet WHERE date_time=:dateTime")
        List<Diet> getDiet(String dateTime);
    }

    @Dao
    public interface AteFoodDAO{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insert(AteFood... ateFood);

        @Update
        void updateAll(AteFood... ateFood);

        @Delete
        void deleteAll(AteFood... ateFood);

        @Query("DELETE FROM ate_food")
        Completable deleteTable();

        @Query("SELECT * FROM ate_food")
        List<AteFood> getAteFood();
    }

    @Dao
    public interface TargetKcalDAO{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insert(TargetKcal... targetKcal);

        @Update
        void updateAll(TargetKcal... targetKcal);

        @Delete
        void deleteAll(TargetKcal... targetKcal);

        @Query("DELETE FROM target_kcal")
        Completable deleteAll();

        @Query("SELECT * FROM target_kcal WHERE date==:date")
        List<TargetKcal> getTargetKcal(String date);
    }
}
