/**
 * @author Mohammad Saiful Alam
 * Setting database adapter
 */
package com.open.source.base.database;

import android.content.ContentValues;
import android.database.Cursor;

// MAP with SettingTable..

//Help from : http://www.tutorialspoint.com/android/android_sqlite_database.htm
public class DbSettingTable extends DbBaseModel {

    /**
     * Constructor function
     */
    public DbSettingTable() {
    }

    /**
     * Return the create table script
     *
     * @return
     */
    public static String getCreateTable() {

        String sqltblCreate = "CREATE TABLE " + Table.TABLE_NAME + "("
                + Table.ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
                + Table.NAME + " TEXT" + ","
                + Table.VALUE + " TEXT" + ","
                + Table.DATE + " DATETIME);";


        return sqltblCreate;
    }

    /**
     * Return the drop table script
     *
     * @return
     */
    public static String getDropTable() {

        String sqltblCreate = "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

        return sqltblCreate;
    }

    /**
     * Insert an entity into database
     */
    @Override
    public long insert(DbEntity entity) {
        SettingEntity item = (SettingEntity) entity;

        ContentValues cv = new ContentValues();
        cv.put(Table.NAME, item.getName());
        cv.put(Table.VALUE, item.getValue());
        cv.put(Table.DATE, item.getTime());

        long row = mDatabaseController.insert(Table.TABLE_NAME, cv);

        return row;
    }

    /**
     * Update an entity into database
     */
    @Override
    public int update(DbEntity entity) {

        SettingEntity item = (SettingEntity) entity;
        ContentValues cv = new ContentValues();
        //cv.put(Table.ID, item.getId());
        cv.put(Table.NAME, item.getName());
        cv.put(Table.VALUE, item.getValue());
        cv.put(Table.DATE, item.getTime());//
        String[] whereArgs = new String[]{mDatabaseController.toString(entity.getId())};
        String where = Table.ID + "=?";

        int row = mDatabaseController.update(Table.TABLE_NAME, cv, where, whereArgs);

        return row;
    }

    /**
     * Retrieve all information from database
     */
    public void query() {
        Cursor c = mDatabaseController.query(Table.TABLE_NAME, null, null, null, null, null, null);
        this.loadData(c);
    }

    @Override
    public void createTable() {
        try{
            mDatabaseController.execSQL(getCreateTable());
        }
        catch (Exception e){
            DbLog.d(e.getMessage());
        }

    }

    public void rawQuery(String query) {
        Cursor c = mDatabaseController.rawQuery(query, null);
        this.loadData(c);
    }

    /**
     * Check row value exists or not by given values.
     *
     * @param value1
     * @param value2
     * @return
     */
    public boolean query(String value1, String value2) {
        String[] columns = new String[]{Table.ID, Table.NAME, Table.VALUE};
        String[] selArgs = new String[]{value1, value2};
        String selection = Table.NAME + "=?" + " AND " + Table.VALUE + "=?";
        mDatabaseController.query(Table.TABLE_NAME, columns, selection, selArgs, null, null, null);

        return this.getCount() > 0 ? true : false;
    }

    /**
     * Load all information from database
     */
    @Override
    protected void loadData(Cursor cursor) {
        // TODO Auto-generated method stub
        clear(false);
        if (cursor == null) return;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

               do {
                SettingEntity entity = new SettingEntity(0, "EntitySetting");
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                //LIKE ANY ONE ADD ITEMS..
                entity.setId(getIntFromCursor(Table.ID, cursor));
                entity.setName(getStringFromCursor(Table.NAME, cursor));
                entity.setValue(getStringFromCursor(Table.VALUE, cursor));
                entity.setTime(getStringFromCursor(Table.DATE, cursor));

                // Adding contact to list
               this.add(entity);

            } while (cursor.moveToNext());
        }

        // Must close this cursor
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    // Add the following lines getCreateTable() in -> Tables.createTableScript()
    // to ensure that table is created.
    public static class Table {
        // TABLE NAME
        public static final String TABLE_NAME = "SettingTable";
        // TABLE COLUMNS
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String VALUE = "value";
        public static final String DATE = "date";
    }

    // TABLE ROW/ENTITY
    public static class SettingEntity extends DbEntity {

        public SettingEntity(String name, String value, String time) {
            super(-1);
            this.setName(name);
            this.setValue(value);
            this.setTime(time);
        }
        public SettingEntity(int t, String name, String value, String time) {
            super(t);
            this.setName(name);
            this.setValue(value);
            this.setTime(time);
        }
        public SettingEntity(long id, String value) {
            super(id);
        }
        public String getValue() {
            return this.getString(Table.VALUE);
        }
        public void setValue(String value) {
            this.setValue(Table.VALUE, value);
        }
        public String getName() {
            return this.getString(Table.NAME);
        }
        public void setName(String value) {
            this.setValue(Table.NAME, value);
        }
        public String getTime() {
            return this.getString(Table.DATE);
        }
        public void setTime(String time) {
            this.setValue(Table.DATE, time);
        }
    }
}

