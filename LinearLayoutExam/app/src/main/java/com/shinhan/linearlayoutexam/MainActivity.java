package com.shinhan.linearlayoutexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SubActivity(View view){
        Intent i = new Intent(this, SubActivity.class);
        startActivity(i);
    }

    public void onButton1Clicked(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageview2);
        Button button = (Button)view;
        if (button.getText().toString().equals("BUTTON1")) {
            imageView.setBackgroundResource(R.drawable.jungku);
            imageView2.setBackgroundResource(R.drawable.oysterjang);
        } else {
            imageView.setBackgroundResource(R.drawable.oysterjang);
            imageView2.setBackgroundResource(R.drawable.oysterjang);
        }
    }
}
