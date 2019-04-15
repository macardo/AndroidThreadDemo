package com.macardo.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.defaultThreadFactory().newThread(new Runnable() {
                    @Override
                    public void run() {
                        //1.使用Activity对象的runOnUiThread()方法更新UI
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                button.setText("使用Activity对象的runOnUiThread()方法更新UI");
                            }
                        });

                        //2.1使用View对象的post()方法更新UI
                        button.post(new Runnable() {
                            @Override
                            public void run() {
                                button.setText("使用View对象的post()方法更新UI");
                            }
                        });

                        //2.2使用View对象的postDelayed()方法更新UI
                        button.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                button.setText("使用View对象的postDelayed()方法更新UI");
                            }
                        },1000);
                    }
                }).start();
            }
        });
    }
}
