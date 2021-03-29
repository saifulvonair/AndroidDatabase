/**
 * @author open Source
 */
package com.open.source.base.database;

import org.json.JSONException;
import org.json.JSONObject;

public class DbEntity extends JSONObject {

    private String ID = "ID";
    private String TAG = getClass().getSimpleName();

    /**
     * The id of this object
     * @param id
     */
    public DbEntity(long id) {
        this.setValue(ID, id);
    }

    public DbEntity(String json) throws JSONException {
        super(json);
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param id the long value of the name.
     */
    public void setValue(String name, long id) {
        try {
            this.put(name, id);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param value the string value of the name.
     */
    public void setValue(String name, String value) {
        try {
            this.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param value the string value of the name.
     */
    public void setValue(String name, Object value) {
        try {
            this.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }
    }

    /**
     * Return the value for given name, return null if no key found.
     * @param name
     * @return
     */
    public Object getValue(String name) {
        try {
            return this.get(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }

        return null;
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public String getString(String name) {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }

        return null;
    }

    /**
     * @return the ID of this object.
     */
    public long getId() {
        long id = (Long) this.getValue(ID);

        return id;
    }

    /**
     * Set the id of this object.
     * @param id
     */
    public void setId(long id) {
        this.setValue(ID, id);
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public long getLong(String name) {
        try {
            return super.getLong(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }

        // Invalid value
        return -999999999;
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public int getInt(String name) {
        try {
            return super.getInt(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }

        // Invalid value
        return -999999999;
    }

    /**
     * Return the boolean value for given name, return false if no key found.
     */
    public boolean getBoolean(String name) {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            DbLog.d(TAG, e.getMessage());
        }

        return false;
    }
}

