package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.felipewisniewski.marvelcomics.Presenter.GetAllCharacters;
import com.felipewisniewski.marvelcomics.R;

public class MarvelsActivity extends Activity {

    private RecyclerView recyclerView;
    private GetAllCharacters getAllCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvels);

        recyclerView = findViewById(R.id.recyclerview_listmarvels_id);

        getAllCharacters = new GetAllCharacters(this, recyclerView);
        getAllCharacters.execute();
    }
}
