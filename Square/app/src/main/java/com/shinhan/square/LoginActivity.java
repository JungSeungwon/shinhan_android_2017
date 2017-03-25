package com.shinhan.square;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void onBut1Clicked(View view){
        Intent intent = new Intent(this, joonggoMainActivity.class);
        startActivityForResult(intent,0);
    }
    public void onBut2Clicked(View view){
        Intent intent = new Intent(this, bongsaActivity.class);
        startActivityForResult(intent,0);
    }
}
