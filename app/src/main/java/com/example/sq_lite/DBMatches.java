package com.example.sq_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBMatches {

    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tableMatches";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME= "NAME";
    private static final String COLUMN_NOTE = "NOTE";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_NOTE = 2;

    private SQLiteDatabase mDataBase;

    public DBMatches(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String date, String note) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, date);
        cv.put(COLUMN_NOTE, note);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Noteq md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_NOTE, md.getNote());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Noteq select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String Date = mCursor.getString(NUM_COLUMN_NAME);
        String Note = mCursor.getString(NUM_COLUMN_NOTE);
        return new Noteq(id, Date, Note);
    }

    public ArrayList<Noteq> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Noteq> arr = new ArrayList<Noteq>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String Date = mCursor.getString(NUM_COLUMN_NAME);
                String Note = mCursor.getString(NUM_COLUMN_NOTE);
                arr.add(new Noteq(id, Date, Note));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT, " +
                    COLUMN_NOTE + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}