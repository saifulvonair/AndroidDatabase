
package com.open.source.base.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbController {
    // All Static variables
    public static String TAG = "DatabaseController";
    // The instance of this class
    static private DbController mInstance = null;
    // The helper class for creating and updating the DB
    protected DatabaseHelper mHelper = null;
    // The actual database instance that execute the query.
    protected SQLiteDatabase mDatabase = null;
    // Database Name
    public static String DATABASE_NAME = "com.open.source.base.database.db";
    // Database Version
    public static final int DATABASE_VERSION = 1;

    private DbController() {
    }

    /**
     * Constructor function
     */
    synchronized static public DbController getInstance() {
        if (mInstance == null) {
            mInstance = new DbController();
        }

        return mInstance;
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    /**
     * Return the string from long value
     *
     * @param value
     * @return the converted value
     */
    public static String toString(long value) {
        return String.valueOf(value);
    }

    /**
     * @param context
     */
    public void createDatabase(Context context, String dbName, int versionCode) {

        if(mHelper == null){
            mHelper = new DatabaseHelper(context, dbName, versionCode);
            //Apply the provided Name..
            DATABASE_NAME = dbName;
        }
        open();

    }

    /**
     * Delete the database.
     *
     * @param context
     */
    public void deleteDatabase(Context context) {
        try {
            context.deleteDatabase(DATABASE_NAME);
        }catch (Exception e){
            DbLog.d(TAG, "Database Deletion Error: "+e.getMessage());
        }
    }

    /**
     * Open the database, this method must be called before executing any operation.
     * By default
     */
    private synchronized void open() {

        mDatabase = mHelper.getWritableDatabase();
    }

    /**
     * Close the database when not needed
     */
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
        mHelper.close();
    }


    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
     * It has no means to return any data (such as the number of affected rows).
     *
     * @param sql
     */
    public void execSQL(String sql) {
        if(mDatabase == null){
            return;
        }
        mDatabase.execSQL(sql);
    }

    /**
     * Query the given table, returning a Cursor over the result set.
     *
     * @return A Cursor object, which is positioned before the first entry.
     */


    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {

        try {
            mDatabase = this.mHelper.getReadableDatabase();
            Cursor cursor = mDatabase.query(table,
                    columns,
                    selection,
                    selectionArgs,
                    groupBy,
                    having,
                    orderBy);

            return cursor;
        } catch (Exception e) {
        }

        return null;
    }


    /**
     * Convenience method for inserting a row into the database.
     *
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */

    public long insert(String table, ContentValues values) {

        try {
            mDatabase = this.mHelper.getWritableDatabase();
            long ret = mDatabase.insert(table,
                    null,
                    values);

            return ret;

        } catch (Exception e) {
        }

        return -1;
    }

    public long insertDataForTransaction(String table, ContentValues values) {

        try {

            long ret = mDatabase.insert(table,
                    null,
                    values);
            return ret;

        } catch (Exception e) {
        }

        return -1;
    }

    public void databaseBeginTransaction() {
        try {
            mDatabase.beginTransaction();
        } catch (Exception e) {
        }
    }

    public void databaseEndTransaction() {
        try {
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            try {
                mDatabase.endTransaction();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     * To remove all rows and get a count pass "1" as the whereClause.
     */

    public int delete(String table, String whereClause, String[] whereArgs) {
        try {
            mDatabase = this.mHelper.getWritableDatabase();
            int ret = mDatabase.delete(table,
                    whereClause,
                    whereArgs);
            return ret;
        } catch (Exception e) {
        }
        return -1;
    }

    public int getMaxIDValue(String table, String column) {
        mDatabase = this.mHelper.getWritableDatabase();
        int id = 0;
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT Max(" + column + ") FROM " + table,null);
            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getInt(0);
            }
        }catch (Exception e) {
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return id;
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @return the number of rows affected
     */

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        try {

            mDatabase = this.mHelper.getWritableDatabase();
            int ret = mDatabase.update(table,
                    values,
                    whereClause,
                    whereArgs);

            return ret;
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * Runs the provided SQL and returns a Cursor over the result set.
     *
     * @return A Cursor object, which is positioned before the first entry.
     */

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        try {
            mDatabase = this.mHelper.getReadableDatabase();
            Cursor cursor = mDatabase.rawQuery(sql, selectionArgs);
            return cursor;
        } catch (Exception e) {
        }

        return null;
    }

    //
    public  class DatabaseHelper extends SQLiteOpenHelper {
        public  String TAG = "DataBaseHelper";
        private SQLiteDatabase mDataBase;

        public DatabaseHelper(Context context, String dbName, int version) {
            super(context, dbName, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
        }

        /**
         * Upgrade the tables
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public synchronized void close() {
            if (mDataBase != null)
                mDataBase.close();
            super.close();
        }
    }
}
