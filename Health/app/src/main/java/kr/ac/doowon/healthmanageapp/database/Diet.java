package kr.ac.doowon.healthmanageapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diet")
public class Diet{
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

    public Diet(){

    }

    public Diet(int dietId, String typeOfMeal, String mealTime, String comment, String dateTime, String url){
        this.dietId = dietId;
        this.typeOfMeal = typeOfMeal;
        this.mealTime = mealTime;
        this.comment = comment;
        this.dateTime = dateTime;
        this.url = url;
    }

    public int getDietId() {
        return dietId;
    }

    public String getTypeOfMeal() {
        return typeOfMeal;
    }

    public String getMealTime() {
        return mealTime;
    }

    public String getComment() {
        return comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUrl() {
        return url;
    }

    public Diet setDietId(int dietId) {
        this.dietId = dietId;
        return this;
    }

    public Diet setTypeOfMeal(String typeOfMeal) {
        this.typeOfMeal = typeOfMeal;
        return this;
    }

    public Diet setMealTime(String mealTime) {
        this.mealTime = mealTime;
        return this;
    }

    public Diet setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Diet setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Diet setUrl(String url) {
        this.url = url;
        return this;
    }
}

