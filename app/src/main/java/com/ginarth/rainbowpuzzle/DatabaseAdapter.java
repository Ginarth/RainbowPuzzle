package com.ginarth.rainbowpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public long add(String name, int size, int moves, int time, String date) {
        ContentValues cv  = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, name);
        cv.put(DatabaseHelper.COLUMN_SIZE, size);
        cv.put(DatabaseHelper.COLUMN_MOVES, moves);
        cv.put(DatabaseHelper.COLUMN_TIME, time);
        cv.put(DatabaseHelper.COLUMN_DATE, date);

        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public ArrayList<Record> take(int size) {
        ArrayList<Record> records = new ArrayList<Record>();
        String command = String.format("SELECT * FROM %s WHERE %s = %d ORDER BY %s",
                DatabaseHelper.TABLE, DatabaseHelper.COLUMN_SIZE, size, DatabaseHelper.COLUMN_TIME);

        Cursor cursor = database.rawQuery(command, null);
        int nameInd, dateInd, movesInd, timeInd;

        if (cursor.moveToFirst()) {
            do {
                nameInd = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                dateInd = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);
                movesInd = cursor.getColumnIndex(DatabaseHelper.COLUMN_MOVES);
                timeInd = cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME);

                if (nameInd > -1 && dateInd > -1 && movesInd > -1 && timeInd > -1) {
                    records.add(new Record(
                            cursor.getString(nameInd),
                            cursor.getString(dateInd),
                            cursor.getInt(movesInd),
                            cursor.getInt(timeInd)));
                }

            } while (cursor.moveToNext());
        }

        return records;
    }

    public void close() {
        dbHelper.close();
    }
}
