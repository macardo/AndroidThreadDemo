package com.macardo.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        final EditText input = (EditText) findViewById(R.id.TextView);
        final Button countBtn = (Button) findViewById(R.id.count);
        countBtn.setOnClickListener(new View.OnClickListener() {
            TimeCountTask timeCountTask;
            @Override
            public void onClick(View v) {
                String inputText = input.getText().toString();
                if(countBtn.getTag()==null){
                    timeCountTask = new TimeCountTask();
                    if(!TextUtils.isEmpty(inputText)){
                        //开始计时
                        timeCountTask.execute(Long.valueOf(inputText));
                    }else{
                        //开始计时
                        timeCountTask.execute();
                    }
                }else{
                    timeCountTask.cancel(true);
                }
            }
        });
    }

    class TimeCountTask extends android.os.AsyncTask<Object, String, String> {

        TextView showText;
        Button countBtn;
        EditText inputEdt;

        @Override
        protected void onPreExecute() {
            showText = (TextView) findViewById(R.id.show);
            countBtn = (Button) findViewById(R.id.count);
            inputEdt = (EditText) findViewById(R.id.TextView);

            inputEdt.setEnabled(false);
            countBtn.setTag(0);
            countBtn.setText("取消");
        }

        @Override
        protected String doInBackground(Object... params) {
            if(params.length==1&&params[0] instanceof Long){
                long inputTime = (long) params[0];
                for(;inputTime>0;inputTime--){
                    publishProgress("剩余："+inputTime+"秒");
                    System.out.println(">>>:"+inputTime);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return "";
                    }
                }
            }else{
                AsyncTask.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AsyncTask.this, "参数错误", Toast.LENGTH_LONG).show();
                    }
                });
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            showText.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            showText.setText("计时已完成");

            inputEdt.setEnabled(true);
            countBtn.setTag(null);
            countBtn.setText("计时");
        }

        @Override
        protected void onCancelled(String s) {
            showText.setText("计时已取消");

            inputEdt.setEnabled(true);
            countBtn.setTag(null);
            countBtn.setText("计时");
        }
    }
}
