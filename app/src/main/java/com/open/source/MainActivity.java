package com.open.source;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import com.open.source.base.database.DbController;
import com.open.source.base.database.DbSettingTable;
import com.open.source.base.ui.BaseActivity;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    DbSettingTable mSettingDbAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
    }

    @Override
    protected void createView(Bundle savedInstanceState) {

    }

    @Override
    protected void createAdapter() {

    }

    @Override
    protected void loadData() {
        DbController.getInstance().createDatabase(this, "com.open.source.db1", 1);
        mSettingDbAdapter = new DbSettingTable();
        mSettingDbAdapter.createTable();
        mSettingDbAdapter.query();
    }

    @Override
    public void doUpdateRequest(Object response) {

    }

    public void onInsertClick(View view) {
        testInsertDatabase();
        Toast.makeText(this, "insert at " + mSettingDbAdapter.getCount() + " position", Toast.LENGTH_LONG).show();
    }

    public void onUdateClick(View view) {
        testUpdateTableRow();
    }

    public void onDeleteClick(View view) {
        testDeleteRow();
    }

    public void onQueryClick(View view) {
        testQueryDatabase();
        Toast.makeText(this, "Complete query  " + mSettingDbAdapter.getCount() + " number item found", Toast.LENGTH_LONG).show();
    }

    public void onRowQueryClick(View view) {
        testRawQueryDatabase();
        Toast.makeText(this, "Complete row query  " + mSettingDbAdapter.getCount() + " number item found", Toast.LENGTH_LONG).show();
    }

    public void onQueryBetweenTimetClick(View view) {
        testRawQueryBetweenDate();
        Toast.makeText(getApplicationContext(), "Complete row query between time  " + mSettingDbAdapter.getCount() + " number item found", Toast.LENGTH_LONG).show();
    }

    private void testRawQueryBetweenDate() {
        String startdate = "2016-02-29 09:53:18";
        String enddate = "2016-02-29 09:54:19";
        String dateQuery = "SELECT * FROM " + DbSettingTable.Table.TABLE_NAME +
                " WHERE " + DbSettingTable.Table.DATE +
                " BETWEEN '" + startdate + "' AND '" + enddate + "'";
        mSettingDbAdapter.rawQuery(dateQuery);
        Log.i("i see ", "see log size=" + mSettingDbAdapter.getCount());
        showQueryData();

        Log.i("i see ", "see log" + "query done" + mSettingDbAdapter.getCount());
    }

    void testQueryDatabase() {
        mSettingDbAdapter.query();
        showQueryData();
    }

    private void testInsertDatabase() {
        mSettingDbAdapter.insert(new DbSettingTable.SettingEntity("item1", "value1", getDateTime()));
        testQueryDatabase();
    }

    public void testUpdateTableRow() {
        int row = 1;
        int updateRow = mSettingDbAdapter.update(new DbSettingTable.SettingEntity(row, "item1", "value1", getDateTime()));

        testQueryDatabase();
        Toast.makeText(this, "Updated row number " + updateRow, Toast.LENGTH_LONG).show();
    }

    public void testDeleteRow() {
        int row = 1;
        int deleteRow = mSettingDbAdapter.delete(row, DbSettingTable.Table.TABLE_NAME);
        testQueryDatabase();
        Toast.makeText(this, "Delete row number " + deleteRow, Toast.LENGTH_LONG).show();
    }

    public void testRawQueryDatabase() {
        String name = "item1";
        String query = "SELECT * from " + DbSettingTable.Table.TABLE_NAME + " where name = '" + name + "'";
        mSettingDbAdapter.rawQuery(query);
        showQueryData();
        Log.i("i see ", "see log" + "query done" + mSettingDbAdapter.getCount());

    }

    public void showQueryData() {
        DbSettingTable.SettingEntity es = null;

        for (int i = 0; i < mSettingDbAdapter.getCount(); i++) {
            es = (DbSettingTable.SettingEntity) mSettingDbAdapter.getItem(i);
            Log.i("i see", "see log " + "id=" + es.getId() + " value=" + es.getValue() + " name=" + es.getName() + " time= " + es.getTime());
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String s = dateFormat.format(date);
        Log.i("i see ", "date log" + s);
        return dateFormat.format(date);
    }
}