package com.shinhan.activityexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButton1Clicked(View v) {
        EditText editText = (EditText)findViewById(R.id.edittext);
        String string = editText.getText().toString();
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("String",string);
        //startActivity(intent);
        startActivityForResult(intent,0);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == 0){ // sub Activity가 종료되었다면
            if(resultCode == RESULT_OK) // 값을 넘기는 정상 종료일때만
            {
                String result = data.getStringExtra("Result");
                EditText editText = (EditText)findViewById(R.id.edittext);
                editText.setText(result);
            }
        }
    }
}
