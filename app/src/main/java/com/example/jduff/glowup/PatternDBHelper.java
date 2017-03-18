package com.example.jduff.glowup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jduff on 3/16/2017.
 */

public class PatternDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pattern.db";

    private static final String SQL_CREATE_PATTERNS =
            "CREATE TABLE " + PatternDBContract.PatternsTable.TABLE_NAME + " (" +
                    PatternDBContract.PatternsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    PatternDBContract.PatternsTable.COLUMN_NAME + " TEXT)";
    private static final String SQL_DELETE_PATTERNS =
        "DROP TABLE IF EXISTS " + PatternDBContract.PatternsTable.TABLE_NAME;

    private static final String SQL_CREATE_ELEMENTS =
            "CREATE TABLE " + PatternDBContract.ElementTable.TABLE_NAME + " (" +
                    PatternDBContract.ElementTable._ID + " INTEGER PRIMARY KEY," +
                    PatternDBContract.ElementTable.COLUMN_INDEX_ID + " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_RING_ID + " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_PATTERN_ID + " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_LENGTH + " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_RED+ " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_GREEN + " INTEGER," +
                    PatternDBContract.ElementTable.COLUMN_BLUE + " INTEGER)";
    private static final String SQL_DELETE_ELEMENTS=
            "DROP TABLE IF EXISTS " + PatternDBContract.ElementTable.TABLE_NAME;

    public PatternDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PATTERNS);
        db.execSQL(SQL_CREATE_ELEMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ELEMENTS);
        db.execSQL(SQL_DELETE_PATTERNS);
        onCreate(db);
    }
}
