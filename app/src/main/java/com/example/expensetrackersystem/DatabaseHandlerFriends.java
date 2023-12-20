package com.example.expensetrackersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensetrackersystem.model.FriendExpense;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerFriends extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "friend_expenses.db";
    public static final String TABLE_NAME = "friend_expenses_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "FRIEND_NAME";
    public static final String COL3 = "EXPENSE_AMOUNT";

    public DatabaseHandlerFriends(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "NAME TEXT," + " EXPENSE_AMOUNT TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String a = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(a);
        onCreate(db);
    }

    public boolean addData(String friendName, double expenseAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, friendName);
        contentValues.put(COL3, expenseAmount);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public List<FriendExpense> getAllFriendExpenses() {
        List<FriendExpense> friendExpensesList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (data.getCount() == 0) {
            while (data.moveToNext()) {
                int id = data.getInt(data.getColumnIndex(COL1));
                String friendName = data.getString(data.getColumnIndex(COL2));
                double expenseAmount = data.getDouble(data.getColumnIndex(COL3));

                // Use the appropriate constructor for FriendExpense
                FriendExpense friendExpense = new FriendExpense(id, friendName, expenseAmount);
                friendExpensesList.add(friendExpense);
            }
        }

        return friendExpensesList;
    }
}
