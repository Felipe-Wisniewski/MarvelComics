package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.R;

public class CharacterActivity extends Activity {

    private ImageView characterPicture;
    private TextView characterName, characterDescripition;

    private String charId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        characterPicture = findViewById(R.id.picture_character_img_id);
        characterName = findViewById(R.id.name_character_txt_id);
        characterDescripition = findViewById(R.id.desc_character_txt_id);

        Intent intent = getIntent();
        charId = intent.getStringExtra("id");

    }
}
