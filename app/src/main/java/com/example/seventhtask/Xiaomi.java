package com.example.seventhtask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Назар on 08.04.2017.
 */

public class Xiaomi extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaomi_main);
        final Context context;
        context = getApplicationContext();
        final Intent intent = new Intent(context,MainActivity.class);
        Button start=(Button)findViewById(R.id.Back);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
