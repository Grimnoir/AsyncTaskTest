package com.sherwinvaz4827.user.asynctasktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private TextView textView;
    private Button btnstart,btnstop;
    private ProgressBar progressBar;
    private ProgressAsync progressAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        btnstart=findViewById(R.id.btnstart);
        btnstop=findViewById(R.id.btnstop);
        progressBar=findViewById(R.id.progressBar);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            progressAsync=new ProgressAsync();
                            progressAsync.execute();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressAsync.cancel(true);
                progressBar.setProgress(0);
                textView.setText("Task is cancelled");
            }
        });

    }
    public class ProgressAsync extends AsyncTask<Void,Integer,String>
    {
    int value=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(value);
        }

        @Override
        protected String doInBackground(Void... voids) {
            while(!isCancelled())
            {
                value++;
                if(value>=100)
                {
                    break;
                }
                else
                {
                publishProgress(value);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "progress:"+value);
            }
            return "Task Finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            textView.setText("Progress :"+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            value=0;
            progressBar.setProgress(value);
            textView.setText(s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressAsync!=null)
        {
                progressAsync.cancel(true);
        }
    }
}
