package kr.ac.doowon.healthmanageapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


public class Table {
    @Entity(tableName = "diet")
    public static class Diet{
        @PrimaryKey
        @ColumnInfo(name = "diet_Id")
        public int dietId;

        @ColumnInfo(name = "type_of_Meal")
        public String typeOfMeal;

        @ColumnInfo(name = "meal_time")
        public String mealTime;

        @ColumnInfo(name = "comment")
        public String comment;

        @ColumnInfo(name = "date_time")
        public String dateTime;

        @ColumnInfo(name = "url")
        public String url;
    }

    @Entity(
            tableName = "ate_food",
            foreignKeys = {
                    @ForeignKey(
                            entity = Diet.class,
                            parentColumns = "diet_Id",
                            childColumns = "ate_food_id"
                    )
            }
    )
    public static class AteFood{
        @PrimaryKey
        @ColumnInfo(name = "ate_food_id")
        public int ateFoodId;

        @ColumnInfo(name = "serial_number")
        public int serialNumber;

        @ColumnInfo(name = "food_name")
        public String food_name;

        @ColumnInfo(name = "amount")
        public int amount;

        @ColumnInfo(name = "kcal")
        public int kcal;

        @ColumnInfo(name = "carbohydrate")
        public int carbohydrate;

        @ColumnInfo(name = "protein")
        public int protein;

        @ColumnInfo(name = "fat")
        public int fat;

        @ColumnInfo(name = "sodium")
        public int sodium;

    }
}
