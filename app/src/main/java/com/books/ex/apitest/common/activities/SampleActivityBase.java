package com.books.ex.apitest.common.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.books.ex.apitest.common.logger.LogHelper;
import com.books.ex.apitest.common.logger.LogWrapper;

public class SampleActivityBase extends AppCompatActivity {

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    public void initializeLogging() {
        LogWrapper logWrapper = new LogWrapper();
        LogHelper.setLogNode(logWrapper);

        LogHelper.i(TAG, "Ready");
    }
}