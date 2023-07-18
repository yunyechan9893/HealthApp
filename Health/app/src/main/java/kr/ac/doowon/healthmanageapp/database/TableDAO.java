package kr.ac.doowon.healthmanageapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.core.Completable;
import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;

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
        Completable deleteAll();

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
        Completable deleteAll();

        @Query("SELECT * FROM ate_food")
        List<AteFood> getAteFood();
    }
}
