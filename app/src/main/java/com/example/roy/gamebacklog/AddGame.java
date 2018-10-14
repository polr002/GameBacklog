package com.example.roy.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddGame extends AppCompatActivity {

    private TextView gameTitle;
    private TextView platform;
    private Spinner status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameTitle = findViewById(R.id.gameTitle);
        platform = findViewById(R.id.platform);
        status = findViewById(R.id.status);



        //Floating add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
                data.putExtra("title", gameTitle.getText().toString());
                data.putExtra("platform", platform.getText().toString());
                data.putExtra("status", status.getSelectedItem().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

}
