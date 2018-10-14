package com.example.roy.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateGame extends AppCompatActivity {

    private EditText title;
    private EditText platform;
    private Spinner status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.gameTitle);
        platform = findViewById(R.id.platform);
        status = findViewById(R.id.status);



        //this takes the variables that need to be updated from the mainactivity
        final gameObject gameUpdate = getIntent().getParcelableExtra(MainActivity.EXTRA_GAME);
        title.setText(gameUpdate.getTitle());
        platform.setText(gameUpdate.getPlatform());
        status.getSelectedItemPosition();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("platform",platform.getText().toString());
                intent.putExtra("status",status.getSelectedItem().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
