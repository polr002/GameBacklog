package com.example.roy.gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import static com.example.roy.gamebacklog.CustomAdapter.*;

public class MainActivity extends AppCompatActivity implements CustomAdapter.CardClickListener {

    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    private List<gameObject> gameBacklog;

    public static final String EXTRA_GAME = "Game";
    private int modifyPosition;

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAMES = 1;
    public final static int TASK_UPDATE_GAMES = 2;
    public final static int TASK_INSERT_GAMES = 3;


    public static final int NEW_GAME = 1;
    public static final int UPDATE_GAME = 2;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = AppDatabase.getsInstance(this);

        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
        gameBacklog = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGame.class);
                startActivityForResult(intent, NEW_GAME);

            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        int position = (viewHolder.getAdapterPosition());
                        new GameAsyncTask(TASK_DELETE_GAMES).execute(gameBacklog.get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void onGameBacklogUpdated(List list) {
        gameBacklog = list;
        updateUI();
    }


    private void updateUI() {
        gameBacklog = db.gameDao().getAllGames();

        if (mAdapter == null) {
            mAdapter = new CustomAdapter(gameBacklog, this);
            recyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.swapList(gameBacklog);
        }

    }


    @Override
    public void cardOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, UpdateGame.class);
        modifyPosition = i;
        intent.putExtra(EXTRA_GAME, gameBacklog.get(i));
        startActivityForResult(intent, UPDATE_GAME);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == NEW_GAME) {
            String title = data.getStringExtra("title");
            String platform = data.getStringExtra("platform");
            String status = data.getStringExtra("status");
            new GameAsyncTask(TASK_INSERT_GAMES).execute(new gameObject(title, platform, status)
            );

        }
        if (resultCode == RESULT_OK && requestCode == UPDATE_GAME) {
            String title = data.getStringExtra("title");
            String platform = data.getStringExtra("platform");
            String status = data.getStringExtra("status");
            gameObject updatedGame = gameBacklog.get(modifyPosition);
            updatedGame.setTitle(title);
            updatedGame.setPlatform(platform);
            updatedGame.setStatus(status);
            new GameAsyncTask(TASK_UPDATE_GAMES).execute(updatedGame);

        }

    }

    public class GameAsyncTask extends AsyncTask<gameObject, Void, List> {

        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(gameObject... games) {
            switch (taskCode) {
                case TASK_DELETE_GAMES:
                    db.gameDao().deleteGame(games[0]);
                    break;

                case TASK_UPDATE_GAMES:
                    db.gameDao().updateGame(games[0]);
                    break;

                case TASK_INSERT_GAMES:
                    db.gameDao().insertGame(games[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return db.gameDao().getAllGames();
        }
        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameBacklogUpdated(list);
        }

    }
}