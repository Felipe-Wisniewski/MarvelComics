package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.felipewisniewski.marvelcomics.R;

public class MainActivity extends Activity {

    private Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMain = findViewById(R.id.buttonMainId);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MarvelsActivity.class));
            }
        });
    }
}
