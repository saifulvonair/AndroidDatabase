package com.open.source.base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author BJIT
 * Base class for all Activity, all Activity should extends this class.
 */
public abstract class BaseActivity extends AppCompatActivity implements NotifyObserver {
    protected BaseActivity mInstance;

    public BaseActivity() {
        this.mInstance = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove default title bar
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove default status bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Take the Permission when loading...
        // DONOT change this order may creates the problems
        createAdapter();
        createView(savedInstanceState);
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Views should be created here, set the listeners, observer etc..
     */
    protected abstract void createView(Bundle savedInstanceState);

    /**
     * Actions should be created here
     */
    protected abstract void createAdapter();

    /**
     * Concrete class should know how to load the data by adapters
     */
    protected abstract void loadData();

    /**
     * Get the data from response, no need to create runOnUiThread its all ready maintained
     */
    public abstract void doUpdateRequest(Object response);

    @Override
    public synchronized void update(Object response) {
        final Object tmpResponse = response;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInstance.doUpdateRequest(tmpResponse);
            }
        });
    }

    /**
     * Gets view from resource
     *
     * @param rViewId
     * @return
     */
    protected View getView(int rViewId) {
        return findViewById(rViewId);
    }

    /**
     * Sets text from resource into view
     *
     * @param rViewId
     * @param rTextId
     */
    protected void setText(int rViewId, int rTextId) {
        View view = getView(rViewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(rTextId);
        }
    }

    /**
     * Sets text into view
     *
     * @param rViewId
     * @param text
     */
    protected void setText(int rViewId, String text) {
        View view = getView(rViewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    @SuppressWarnings("rawtypes")
    public void startActivity(Class name) {

        Intent intent = new Intent(this, name);
        startActivity(intent);
    }

    /**
     * Handle the back button click
     */
    public void onClickBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
