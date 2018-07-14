package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.felipewisniewski.marvelcomics.Presenter.GetComics;
import com.felipewisniewski.marvelcomics.R;

public class ComicsActivity extends Activity {

    private RecyclerView recyclerView;
    private GetComics getComics;

    private String charId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        recyclerView = findViewById(R.id.recyclerview_comics_id);

        Intent intent = getIntent();
        charId = intent.getStringExtra("id");

        getComics = new GetComics(this, recyclerView, charId);
        getComics.execute();
    }
}
