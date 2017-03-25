package com.shinhan.square;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class joonggoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joonggo_main);
    }
    public void gotohome(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,0);
    }
    public void writeText(View view) {
        Intent intent = new Intent(this, joonggoMainActivity.class);
        startActivityForResult(intent, 0);
    }


}
