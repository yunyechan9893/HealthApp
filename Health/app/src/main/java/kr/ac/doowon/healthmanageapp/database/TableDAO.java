package kr.ac.doowon.healthmanageapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.ac.doowon.healthmanageapp.database.Table.AteFood;
import kr.ac.doowon.healthmanageapp.database.Table.Diet;

public class TableDAO {
    @Dao
    interface DietDAO{
        @Insert
        void insert(Diet diet);

        @Update
        void update(Diet diet);

        @Delete
        void delete(Diet diet);

        @Query("SELECT * FROM diet")
        List<Diet> getDiet();
    }

    @Dao
    interface AteFoodDAO{
        @Insert
        void insertAll(AteFood... ateFood);

        @Update
        void updateAll(AteFood... ateFood);

        @Delete
        void deleteAll(AteFood... ateFood);

        @Query("SELECT * FROM ate_food")
        List<AteFood> getAteFood();
    }
}
