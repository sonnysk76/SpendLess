package com.longpaws.spendless;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by Mitch on 6/25/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "DollarsSpentDB.db";
    public static final String TABLE_NAME = "dollars_spent_transaction";

    public static final String COL_ID = "ID";
    public static final String COL_MONTH = "MONTH";
    public static final String COL_YEAR = "YEAR";
    public static final String COL_EXPENSE_NAME = "EXPENSE_NAME";
    public static final String COL_DOLLARS_SPENT = "DOLLARS_SPENT";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DOLLARS_SPENT_TRANSACTION_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_MONTH + " INTEGER," +
                COL_YEAR + " INTEGER," +
                COL_EXPENSE_NAME + " TEXT," +
                COL_DOLLARS_SPENT + " REAL" + ")";
        db.execSQL(CREATE_DOLLARS_SPENT_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addTransaction(DollarsSpentTransaction transaction) {
        ContentValues values = new ContentValues();
        values.put(COL_MONTH, transaction.getMonth() );
        values.put(COL_YEAR, transaction.getYear() );
        values.put(COL_EXPENSE_NAME, transaction.getExpenseName() );
        values.put(COL_DOLLARS_SPENT, transaction.getDollarsSpent() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String[] displayDataBase() {
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String[] data = new String[cursor.getCount()];

        cursor.moveToFirst();
        int index = 0;

        while (!cursor.isAfterLast() ) {
            String totalString;

            String ID = "ID: " + cursor.getString(0) + "\n";
            String MONTH = "Month: " + cursor.getString(1) + "\n";
            String YEAR = "Year: " + cursor.getString(2) + "\n";
            String EXPENSE_NAME = "Expense Name: " + cursor.getString(3) + "\n";
            String DOLLAR_SPENT = "Amount: " + "$" + cursor.getString(4) + "\n\n";

            totalString = ID + MONTH + YEAR + EXPENSE_NAME + DOLLAR_SPENT;

            data[index] = totalString;

            cursor.moveToNext();
            index++;
        }

        db.close();
        cursor.close();
        return data;
    }
}