package com.example.gameify;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String NAME = "NAME";
    public static final String SURNAME = "SURNAME";
    public static final String USERNAME = "USERNAME";
    public static final String MAIL = "MAIL";
    public static final String AGE = "AGE";
    public static final String GAME = "GAME";
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String LEVEL = "LEVEL";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "mydb_backup",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableStatement = "CREATE TABLE " + USERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + SURNAME + " TEXT, " + USERNAME + " TEXT, " + MAIL + " TEXT, " + AGE + " TEXT, " + GAME + " TEXT, " + GAME_TYPE + " TEXT, " + LEVEL + " TEXT)";

        db.execSQL(tableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(userAccount user, gameData userdata) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(NAME, user.getName());
        cv.put(SURNAME, user.getSurname());
        cv.put(USERNAME, user.getUsername());
        cv.put(MAIL, user.getEmail());
        cv.put(AGE, user.getAge());
        cv.put(GAME, "CSGO");
        cv.put(GAME_TYPE, "FPS");
        cv.put(LEVEL, userdata.getrCsgo());
        db.insert(USERS_TABLE , null , cv);
        cv.put(GAME, "LOL");
        cv.put(GAME_TYPE, "MOBA");
        cv.put(LEVEL, userdata.getrLol());
        db.insert(USERS_TABLE , null , cv);
        cv.put(GAME, "R6");
        cv.put(GAME_TYPE, "FPS");
        cv.put(LEVEL, userdata.getrR6());
        db.insert(USERS_TABLE , null , cv);
        cv.put(GAME, "GTA");
        cv.put(GAME_TYPE, "TPS");
        cv.put(LEVEL, userdata.getrGta());
        db.insert(USERS_TABLE , null , cv);

        return true;
    }

    public void updateData(gameData userdata){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String whereClause = "GAME=?";
        String whereArgs[] = {userdata.getUsername()};

        cv.put(GAME, "CSGO");
        cv.put(GAME_TYPE, "FPS");
        cv.put(LEVEL, userdata.getrCsgo());
        db.update(USERS_TABLE, cv, whereClause, whereArgs);

        cv.put(GAME, "LOL");
        cv.put(GAME_TYPE, "MOBA");
        cv.put(LEVEL, userdata.getrLol());
        db.update(USERS_TABLE, cv, whereClause, whereArgs);

        cv.put(GAME, "R6");
        cv.put(GAME_TYPE, "FPS");
        cv.put(LEVEL, userdata.getrR6());
        db.update(USERS_TABLE, cv, whereClause, whereArgs);

        cv.put(GAME, "GTA");
        cv.put(GAME_TYPE, "TPS");
        cv.put(LEVEL, userdata.getrGta());
        db.update(USERS_TABLE, cv, whereClause, whereArgs);

    }
}
