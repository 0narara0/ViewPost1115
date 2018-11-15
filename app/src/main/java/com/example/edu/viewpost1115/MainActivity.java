package com.example.edu.viewpost1115;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBarPost;
    ImageView imageViewPost;
    private int mProgressBarStatus = 0;
    TextView textViewProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.buttonRun)).setOnClickListener(this);
        progressBarPost = findViewById(R.id.progressBar);
        imageViewPost = findViewById(R.id.imageView);
        textViewProgress = findViewById(R.id.textViewProgress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRun:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgressBarStatus<100){
                            try {
                                Thread.sleep(100); //실제 프로그래밍에서는 넣지 않는다. 예제라 넣은 것.
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mProgressBarStatus++;
                            progressBarPost.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarPost.setProgress(mProgressBarStatus);
                                }
                            });
                            textViewProgress.post(new Runnable() {
                                @Override
                                public void run() {
                                    textViewProgress.setText(String.valueOf(mProgressBarStatus)+"%");
                                }
                            });
                        }
                        String strUri = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
                        try {
                            URL url = new URL(strUri);
                            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                            httpsURLConnection.connect();
                            InputStream inputStream = httpsURLConnection.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imageViewPost.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageViewPost.setImageBitmap(bitmap);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

//            case R.id.buttonEnter:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String strUri = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
//                        try {
//                            URL url = new URL(strUri);
//                            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
//                            httpsURLConnection.connect();
//                            InputStream inputStream = httpsURLConnection.getInputStream();
//                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                            imageViewPost.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imageViewPost.setImageBitmap(bitmap);
//                                }
//                            });
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//                break;

        }

    }

}
