package com.felipewisniewski.marvelcomics.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.R;
import com.squareup.picasso.Picasso;

public class CharacterActivity extends Activity {

    private ImageView characterPicture;
    private TextView characterName, characterDescripition;

    private String[] character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        characterPicture = findViewById(R.id.picture_character_img_id);
        characterName = findViewById(R.id.name_character_txt_id);
        characterDescripition = findViewById(R.id.desc_character_txt_id);

        Intent intent = getIntent();
        character = intent.getStringArrayExtra("character");

        characterName.setText(character[0]);
        characterDescripition.setText(character[1]);
        Picasso.get().load(character[2]).fit().centerInside().into(characterPicture);
    }
}
