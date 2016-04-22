package com.miroslav.menuinyourcity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 4/22/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Data Base of liked list", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table likedList ("
                + "id integer primary key autoincrement,"
                + "shop_id integer,"
                + "category_id text,"
                + "city_id text,"
                + "title text,"
                + "description text,"
                + "time text,"
                + "lat text,"
                + "lon text,"
                + "street text,"
                + "phone text,"
                + "date_start text,"
                + "date_stop text,"
                + "updated_at text,"
                + "rating float"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
