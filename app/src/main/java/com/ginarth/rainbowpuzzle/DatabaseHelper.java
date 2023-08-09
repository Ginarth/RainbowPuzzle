package com.ginarth.rainbowpuzzle;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE = "score";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SIZE = "Size";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_MOVES = "Moves";
    public static final String COLUMN_TIME = "Time";

    private static final String DATABASE_NAME = "LocalScore.db";
    private static final int VERSION = 1;

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = String.format(
                "CREATE TABLE %s (" +
                "%s TEXT NOT NULL," +
                "%s INTEGER NOT NULL," +
                "%s TEXT NOT NULL," +
                "%s INTEGER NOT NULL," +
                "%s INTEGER NOT NULL" + ");",
                TABLE, COLUMN_NAME, COLUMN_SIZE, COLUMN_DATE, COLUMN_MOVES, COLUMN_TIME);
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String command = String.format("DROP TABLE IF EXISTS %s", TABLE);
        db.execSQL(command);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //String command = String.format("DELETE FROM %s", TABLE);
        //db.execSQL(command);

        String command = String.format("SELECT * FROM %s", TABLE);
        Cursor cursor =  db.rawQuery(command, null);
        if (cursor.getCount() == 0) {
            String[] names = new String[] {"Pride", "Greed", "Wrath", "Envy", "Lust", "Gluttony", "Sloth"};
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            String date = dateFormat.format(new Date());
            for (int i = 3; i < 6; i++) {
                for (int j = 0; j < names.length; j++) {
                    command = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) " +
                                    "VALUES ('%s', %d, '%s', %d, %d)",
                            TABLE, COLUMN_NAME, COLUMN_SIZE, COLUMN_DATE, COLUMN_MOVES, COLUMN_TIME,
                            names[j], i, date, (j + 1) * i * 10, (j + 1) * i * 60);
                    db.execSQL(command);
                }
            }
        }
        cursor.close();
    }
}
