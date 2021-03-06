package com.shinhan.threadexam;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ProgressHandler progressHandler = new ProgressHandler();
    Handler handler = new Handler();
    ProgressRunnable progressRunnable = new ProgressRunnable();
    ProgressRunnableone progressRunnableone = new ProgressRunnableone();
    boolean isRunning= false;
    ProgressBar progressBar1, progressBar2, progressBar3,progressBar4;
    TextView textView1, textView2, textView3,textView4;
    class ProgressTask extends AsyncTask<Integer,Integer,Integer>{
        private int value = 0;
        @Override
        protected Integer doInBackground(Integer... params) { // UI접근하면 안됨
            for(int i = 0; i < 10 && isRunning; i++){
                value += 10;
                publishProgress(value);
                try{
                    Thread.sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() { //백그라운드 작업전에 호출
            super.onPreExecute();
            value = 0; progressBar4.setProgress(value); // 프로그레스 초기화
        }

        @Override
        protected void onPostExecute(Integer integer) { // 백그라운드 작업전에 호출
            super.onPostExecute(integer);
            textView4.setText("Done");
        }

        @Override
        protected void onProgressUpdate(Integer... values) { // 백그라운드 작업후에 호출
            super.onProgressUpdate(values);
            progressBar4.setProgress(values[0]);
            textView4.setText("Working..."+values[0]);
        }
    }
    public class ProgressHandler extends Handler{ //스레드대신 메인 UI 접급
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar1.incrementProgressBy(5);
            if(progressBar1.getProgress()==progressBar1.getMax()){
                textView1.setText("Done");
            }else{
                textView1.setText("Working..."+progressBar1.getProgress());
            }
        }
    }
    public class ProgressRunnable implements Runnable{  //스레드대신 메인 UI접근

        @Override
        public void run() {
            progressBar2.incrementProgressBy(5);
            if(progressBar2.getProgress()==progressBar2.getMax()){
                textView2.setText("Done");
            }else{
                textView2.setText("Working..."+progressBar2.getProgress());
            }
        }
    }

    public class ProgressRunnableone implements Runnable{

        @Override
        public void run() {
            progressBar3.incrementProgressBy(1);
            if(progressBar3.getProgress()==progressBar3.getMax())
            {
                textView3.setText("Done");
            }
            else{
                textView3.setText("Wroking..."+progressBar3.getProgress());
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressBar1.setProgress(0);
        progressBar2.setProgress(0);
        progressBar3.setProgress(0);
        Thread thread = new Thread(new Runnable() {//스레드정의
            @Override
            public void run() { //스레드에서 실행되는 영역(메인UI에는 접근 불가)
                try {
                    for(int i=0; i<20&& isRunning; i++){
                        Thread.sleep(1000);
                        //1. 핸들러를 이용한 메시지 전송방법
                        Message msg= progressHandler.obtainMessage();
                        progressHandler.sendMessage(msg);
                        //2.Runnable 객체를 실행하는 방법
                        handler.post(progressRunnable);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i<100 && isRunning; i++){
                        Thread.sleep(200);
                        handler.post(progressRunnableone);
                    }
                } catch (Exception e){

                }
            }
        });
        isRunning= true;
        thread.start();
        thread2.start();
        new ProgressTask().execute();//세번째 스레드 시작
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=(TextView)findViewById(R.id.textview1);
        textView2=(TextView)findViewById(R.id.textview2);
        textView3=(TextView)findViewById(R.id.textview3);
        textView4=(TextView)findViewById(R.id.textview4);

        progressBar1=(ProgressBar)findViewById(R.id.progress1);
        progressBar2=(ProgressBar)findViewById(R.id.progress2);
        progressBar3=(ProgressBar)findViewById(R.id.progress3);
        progressBar4=(ProgressBar)findViewById(R.id.progress4);

    }
}
