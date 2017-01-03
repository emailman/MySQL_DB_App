package edu.dtcc.emailman.todotodayii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric2 on 1/2/2017.
 *
 */

class DBHelper extends SQLiteOpenHelper{

    // Define the database and the table
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "toDo_Today";
    private static final String DATABASE_TABLE = "toDo_Items";

    // Define the column names for the table
    private static final String KEY_TASK_ID = "_id";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IS_DONE = "is_done";

    // private int taskCount;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table =
                "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_DESCRIPTION + " TEST, "
                + KEY_IS_DONE + " INTEGER" + ")";

        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        onCreate(db);
    }

    void addToDoItem(ToDo_Item task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_IS_DONE, task.getIs_done());

        db.insert(DATABASE_TABLE, null, values);
        // taskCount++;

        db.close();
    }

    List<ToDo_Item>  getAllTasks() {
        List<ToDo_Item> todoList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through the results
        if (cursor.moveToFirst()) {
            do {
                ToDo_Item task = new ToDo_Item();
                task.setId(cursor.getInt(0));
                task.setDescription(cursor.getString(1));
                task.setIs_done(cursor.getInt(2));
                // Add each row to the list
                todoList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return todoList;
    }

    void clearAll(List<ToDo_Item> list) {
        list.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, new String[] {});
        db.close();
    }

    void updateTask(ToDo_Item task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_IS_DONE, task.getIs_done());

        db.update(DATABASE_TABLE, values, KEY_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
