
package com.open.source.base.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
// <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
public abstract class DbBaseModel {
    public static final String ID = "id";
    protected DbController mDatabaseController;
    // Unique name of the adapter/model
    protected String mName = "DbBaseModel";
    // The resulting values of this adapter
    protected ArrayList<DbEntity> mListItem = new ArrayList<DbEntity>();
    // This is for invalid item id
    protected long INVALID_ITEM_ID = Long.MIN_VALUE;
    protected DbBaseModel mInstance;

    public DbBaseModel() {
        mDatabaseController = DbController.getInstance();
        mInstance = this;
    }

    public ArrayList<DbEntity> getListItem() {
        return mListItem;
    }

    /**
     * @return the mName
     */
    public String getName() {
        return mName;
    }

    /**
     * @param mName the mName to set
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Add an item to the model/adapter
     * @param item
     */
    public void add(DbEntity item) {
        if (item != null) {
            this.mListItem.add(item);
        }
    }

    /**
     * Add an item to the model/adapter
     * @param item
     */
    public void addAtPosition(int index, DbEntity item) {
        if (item != null) {
            this.mListItem.add(index, item);
        }
    }

    /**
     * Removes the object at the specified location from this list.
     * @param position
     * @return
     */
    public DbEntity remove(int position) {
        return this.mListItem.remove(position);
    }

    /**
     * Removes one instance of the specified object from this Collection if one is contained (optional).
     * @param object
     */
    public void remove(DbEntity object) {
        this.mListItem.remove(object);
    }

    /**
     * Remove all items from the adapter, For Garbage collector if you have some bitmap then those needs to be
     * recycle first, and also put null to the objects. like Bitmap b; b.recyle(); b = null;
     * @param callGarbageCollector Whether system garbage collector will be called
     */
    public void clear(boolean callGarbageCollector) {
        mListItem.clear();
        if (callGarbageCollector) {
            System.gc();
        }
    }

    /**
     * Returns the number of elements in this ArrayList.
     */
    public int getCount() {
        return this.mListItem.size();
    }

    /**
     * Returns the element at the specified location in this list.
     * @param position
     */
    public Object getItem(int position) {
        if (position > -1 && position < this.mListItem.size()) {
            return this.mListItem.get(position);
        }

        return null;
    }
    /**
     * Load the data from cursor
     *
     * @param cursor
     */
    protected abstract void loadData(Cursor cursor);

    /**
     * Insert the entity
     *
     * @param entity
     * @return
     */
    abstract public long insert(DbEntity entity);

    /**
     * Update the entity
     *
     * @param entity
     * @return
     */
    abstract public int update(DbEntity entity);

    /**
     * Delete the entity
     * // * @param entity
     *
     * @return
     */
    public int delete(int id, String table) {
        String[] whereArgs = new String[]{DbController.toString(id)};
        // FIXME if required...
        String where = ID + "=?";
        int row = mDatabaseController.delete(table, where, whereArgs);
        return row;
    }

    /**
     * Query the database
     *
     * @return
     */
    abstract public void query();

    /**
     * @param table
     * @return
     */
    public int clearTable(String table) {
        int row = mDatabaseController.delete(table, "", null);
        return row;
    }

    /**
     * Get the column value
     *
     * @param key    -table column
     * @param cursor -Cursor
     * @return String
     */
    public  String getStringFromCursor(String key, Cursor cursor) {
        String value = "";
        int colIndex = cursor.getColumnIndex(key);
        if (colIndex > -1) {
            value = cursor.getString(colIndex);
        }
        return value;
    }

    public String getStringFromCursor(int colIndex, Cursor cursor) {
        String value = "";
        if (colIndex > -1) {
            value = cursor.getString(colIndex);
        }
        return value;
    }

    /**
     * Get the column value
     *
     * @param key    -table column
     * @param cursor -Cursor
     * @return integer
     */
    public int getIntFromCursor(String key, Cursor cursor) {
        int value = 0;
        int colIndex = cursor.getColumnIndex(key);
        if (colIndex > -1) {
            value = cursor.getInt(colIndex);
        }
        return value;
    }
    public int getIntFromCursor(int colIndex, Cursor cursor) {
        int value = 0;
        if (colIndex > -1) {
            value = cursor.getInt(colIndex);
        }
        return value;
    }


    public long getLongFromCursor(String key, Cursor cursor) {
        long value = 0;
        int colIndex = cursor.getColumnIndex(key);
        if (colIndex > -1) {
            value = cursor.getLong(colIndex);
        }
        return value;
    }

    public abstract void createTable();
}
