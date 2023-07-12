package kr.ac.doowon.healthmanageapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Diet.class, AteFood.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TableDAO.DietDAO dietDAO();
    public abstract TableDAO.AteFoodDAO ateFoodDAO();

    private static volatile AppDatabase INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static AppDatabase getDatabase(final Context context) {  // Singleton 패턴 구현
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "health_database.db")
                            .addMigrations(MIGRATION_1_2)    // Migration 방법 지정
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
