package com.example.seventhtask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    static final private int ALERT_DIALOG_INFO = 1;
    static final private int ALERT_DIALOG_MUSIC = 2;
    static final private int ALERT_DIALOG_LIST = 3;
    static final private int CUSTOM_ALERT_EXIT = 4;

    private MediaPlayer mediaPlayer;
    private MediaPlayer exit;
    private ListView lv;
    private String[] lang;
    final Intent []intent=new Intent[4];
    private void initResources(){
        Resources res = getResources();
        lang = res.getStringArray(R.array.NamesPhone);
    }
    private void initLanguagesListView(){
        lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,lang));
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context;
        context = getApplicationContext();
        intent[0] = new Intent(context,Nexus.class);
        intent[1] = new Intent(context,Xiaomi.class);
        intent[2] = new Intent(context,IPhone.class);
        intent[3] = new Intent(context,Record.class);
        lv = (ListView) findViewById(R.id.languages);
        initResources();
        initLanguagesListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                switch (pos){
                    case 0 :
                        startActivity(intent[0]);
                        break;
                    case 1 :
                        startActivity(intent[1]);
                        break;
                    case 2 :
                        startActivity(intent[2]);
                        break;
                    case 3 :
                        showDialog(ALERT_DIALOG_INFO);
                        break;
                    case 4:
                        showDialog(ALERT_DIALOG_MUSIC);
                        break;
                    case 5 :
                        showDialog(ALERT_DIALOG_LIST);
                        break;
                    case 6 :
                        showDialog(CUSTOM_ALERT_EXIT);
                        exit= MediaPlayer.create(MainActivity.this, R.raw.cena);
                        exit.start();
                        break;
                    case 7 :
                        startActivity(intent[3]);
                        break;
                    default:
                        break;
                }

            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ALERT_DIALOG_INFO:
                return createPlainAlertDialog();
            case ALERT_DIALOG_MUSIC:
                return createAlertDialogWithList();
            case ALERT_DIALOG_LIST:
                return createCustomAlertDialog();
            case CUSTOM_ALERT_EXIT:
                return EXIT();
            default:
                return null;


        }
    }
    private Dialog createPlainAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Created by")
                .setMessage("Nazar Shost");
        return dialogBuilder.create();
    }
    private Dialog EXIT() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Exit")
                .setMessage("Are you sure about that?")
                .setIcon(R.drawable.cena)
                .setCancelable(false)
                .setPositiveButton("Yes", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        if(exit != null) {
                            exit.stop();
                            exit.release();
                        }
                        showToast("u cant see me");
                        MainActivity.this.finish();
                    }
                });
        dialogBuilder.setNegativeButton("NO ITS JONH CENA!!!!", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                if(exit != null) {
                    exit.stop();
                    exit.release();
                }
                showToast("NICE");

            }
        });
        return dialogBuilder.create();
    }

    private Dialog createAlertDialogWithList() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] options = {"Pine pineapple apple pen", "Johnny Cash — Hurt"};
        dialogBuilder.setTitle("Music list");
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                switch (position) {
                    case 0:
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.musicone);
                        break;
                    case 1:
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.johnny);
                        break;
                    default:
                        break;
                }
                mediaPlayer.start();
            }
        });
        dialogBuilder.setPositiveButton("Start", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mediaPlayer.start();
            }
        });
        dialogBuilder.setNegativeButton("Pause", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mediaPlayer.pause();
            }
        });
        dialogBuilder.setNeutralButton("STOP", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mediaPlayer.stop();
            }
        });
        return dialogBuilder.create();
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    private Dialog createCustomAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View layout = getCustomDialogLayout();
        dialogBuilder.setView(layout);
        dialogBuilder.setTitle("Custom Dialog");
        return dialogBuilder.create();
    }
    private View getCustomDialogLayout() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.dialog,null);
    }


}

