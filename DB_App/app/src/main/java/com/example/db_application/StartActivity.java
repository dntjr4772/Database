package com.example.db_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, 101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
