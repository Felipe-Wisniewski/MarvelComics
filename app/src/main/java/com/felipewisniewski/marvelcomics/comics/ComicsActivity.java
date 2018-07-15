package com.felipewisniewski.marvelcomics.comics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.felipewisniewski.marvelcomics.R;

public class ComicsActivity extends Activity {
    private RecyclerView rView;
    private GridLayoutManager gridLayoutManager;
    public ComicsAdapter comicsAdapter;
    private GetAllComics getAllComics;
    private String charId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        rView = findViewById(R.id.recyclerview_comics_id);

        gridLayoutManager = new GridLayoutManager(ComicsActivity.this, 1);
        rView.setLayoutManager(gridLayoutManager);
        comicsAdapter = new ComicsAdapter();

        Intent intent = getIntent();
        charId = intent.getStringExtra("id");

        getAllComics = new GetAllComics(ComicsActivity.this, rView, comicsAdapter, charId);
        getAllComics.execute(0);

        rView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("STATUS: ",getAllComics.getStatus().toString());
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == getAllComics.getComicsList().size() -1) {
                    if(getAllComics.getOffSet() != getAllComics.getTotal()) {
                        Log.e("ComicsActivity: ","addOnScrollListener -> IF");
                        getAllComics = new GetAllComics(ComicsActivity.this, comicsAdapter, charId);
                        getAllComics.execute(getAllComics.getOffSet());
                    }
                }
            }
        });
    }
}
