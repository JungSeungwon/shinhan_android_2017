package com.shinhan.activityexam;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        String string = intent.getStringExtra("String");
        //Toast.makeText(SubActivity.this, string, Toast.LENGTH_SHORT).show();
        EditText editText=  (EditText)findViewById(R.id.edittext1);
        editText.setText(getIntent().getStringExtra("String"));
    }

    public void onCloseButtonClicked(View view){
        Intent intent = new Intent();
        EditText editText = (EditText)findViewById(R.id.edittext1);
        String result = editText.getText().toString();
        intent.putExtra("Result",result);
        setResult(RESULT_OK,intent);
        finish();
    }
}
