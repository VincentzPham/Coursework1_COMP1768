//package com.example.todolistapp;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//
//public class DbHelper extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "ToDoList.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private static final String TABLE_TASKS = "tasks";
//    private static final String COLUMN_ID = "id";
//    private static final String COLUMN_NAME = "name";
//    private static final String COLUMN_DEADLINE = "deadline";
//    private static final String COLUMN_DURATION = "duration";
//    private static final String COLUMN_DESCRIPTION = "description";
//
//    public DbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_NAME + " TEXT,"
//                + COLUMN_DEADLINE + " TEXT,"
//                + COLUMN_DURATION + " INTEGER,"
//                + COLUMN_DESCRIPTION + " TEXT" + ")";
//        db.execSQL(CREATE_TASKS_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
//        onCreate(db);
//    }
//
//    public void addTask(Task task) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, task.getName());
//        values.put(COLUMN_DEADLINE, task.getDeadline());
//        values.put(COLUMN_DURATION, task.getDuration());
//        values.put(COLUMN_DESCRIPTION, task.getDescription());
//
//        db.insert(TABLE_TASKS, null, values);
//        db.close();
//    }
//
//    public ArrayList<Task> getAllTasks() {
//        ArrayList<Task> taskList = new ArrayList<>();
//        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Task task = new Task(
//                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DEADLINE)),
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
//                );
//                taskList.add(task);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return taskList;
//    }
//
//    public void updateTask(Task task, int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, task.getName());
//        values.put(COLUMN_DEADLINE, task.getDeadline());
//        values.put(COLUMN_DURATION, task.getDuration());
//        values.put(COLUMN_DESCRIPTION, task.getDescription());
//
//        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//        db.close();
//    }
//
//    public void deleteTask(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//        db.close();
//    }
//}



package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ToDoList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEADLINE = "deadline";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUS = "status";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DEADLINE + " TEXT,"
                + COLUMN_DURATION + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DEADLINE, task.getDeadline());
        values.put(COLUMN_DURATION, task.getDuration());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_STATUS, task.getStatus());

        long id = db.insert(TABLE_TASKS, null, values);
        task.setId((int) id);  // Set task ID from database autoincrement value
        db.close();
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int deadlineIndex = cursor.getColumnIndex(COLUMN_DEADLINE);
                int durationIndex = cursor.getColumnIndex(COLUMN_DURATION);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int statusIndex = cursor.getColumnIndex(COLUMN_STATUS);
                if (idIndex != -1 && nameIndex != -1 && deadlineIndex != -1 && durationIndex != -1 && descriptionIndex != -1) {
                    Task task = new Task(
                            cursor.getString(nameIndex),
                            cursor.getString(deadlineIndex),
                            cursor.getInt(durationIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(statusIndex)
                    );
                    task.setId(cursor.getInt(idIndex));
                    taskList.add(task);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    public void updateTask(Task task, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DEADLINE, task.getDeadline());
        values.put(COLUMN_DURATION, task.getDuration());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_STATUS, task.getStatus());

        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
