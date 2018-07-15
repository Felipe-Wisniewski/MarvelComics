package com.felipewisniewski.marvelcomics.marvels;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.felipewisniewski.marvelcomics.R;

public class MarvelsActivity extends Activity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MarvelsAdapter marvelsAdapter;
    private GetAllCharacters getAllCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvels);

        recyclerView = findViewById(R.id.recyclerview_listmarvels_id);
        gridLayoutManager = new GridLayoutManager(MarvelsActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        marvelsAdapter = new MarvelsAdapter(MarvelsActivity.this);

        getAllCharacters = new GetAllCharacters(MarvelsActivity.this, recyclerView, marvelsAdapter);
        getAllCharacters.execute(0);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == getAllCharacters.getCharactersList().size() -1) {
                    if(getAllCharacters.getOffSet() != getAllCharacters.getTotal()) {
                        getAllCharacters = new GetAllCharacters(MarvelsActivity.this, marvelsAdapter);
                        getAllCharacters.execute(getAllCharacters.getOffSet());
                    }
                }
            }
        });
    }
}
