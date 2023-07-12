package kr.ac.doowon.healthmanageapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
public class AteFood{
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

    public int getAteFoodId() {
        return ateFoodId;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getFood_name() {
        return food_name;
    }

    public int getAmount() {
        return amount;
    }

    public int getKcal() {
        return kcal;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

}