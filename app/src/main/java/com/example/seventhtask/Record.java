package com.example.seventhtask;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Record extends Activity {
    private static final String TAG = "";
    private static final int RECORD_REQUEST_CODE = 101;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;
    private Button Start;
    private Button Play;
    private Button Stop;
    private boolean rec=false;
    private ImageView myImageView;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);
        fileName = Environment.getExternalStorageDirectory() + "/record.3gpp";
        myImageView = (ImageView) findViewById(R.id.micro);
        Start=(Button)findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nagraj();
            }
        });

        Play=(Button)findViewById(R.id.Play);
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordPlay();
            }
        });

        Stop=(Button)findViewById(R.id.Stop);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordStop();
            }
        });
        Start.setEnabled(true);
        Play.setEnabled(false);
        Stop.setEnabled(false);
    }
    public void nagraj() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission to record denied");
            makeRequest();
        } else {
            recordStart();
        }
    }
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED || grantResults[1] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                    recordStart();
                }
                return;
            }
        }
    }



    public void recordStart() {
        try {
            releaseRecorder();
            File outFile = new File(fileName);
            if (outFile.exists()) {
                outFile.delete();
            }
            myImageView.setImageResource(R.drawable.red);
            Start.setEnabled(false);
            Stop.setEnabled(true);
            Play.setEnabled(false);
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);
            mediaRecorder.prepare();
            mediaRecorder.start();
            rec=true;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
        }
    }
    public void recordStop() {
        if(rec==true) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            rec=false;
        } else {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;

            }
        }
        Stop.setEnabled(false);
        Start.setEnabled(true);
        Play.setEnabled(true);
        myImageView.setImageResource(R.drawable.grey);
    }
    public void recordPlay(){
        try {
            Start.setEnabled(false);
            Stop.setEnabled(true);
            myImageView.setImageResource(R.drawable.green);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {

        }
    }
    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

}
