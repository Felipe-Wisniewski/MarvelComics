package com.felipewisniewski.marvelcomics.comics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.R;
import com.squareup.picasso.Picasso;

public class CharacterActivity extends Activity {

    private ImageView characterPicture;
    private TextView characterName, characterDescripition;
    private Button button;

    private String[] character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        characterPicture = findViewById(R.id.picture_character_img_id);
        characterName = findViewById(R.id.name_character_txt_id);
        characterDescripition = findViewById(R.id.desc_character_txt_id);
        button = findViewById(R.id.button_comics_id);

        Intent intent = getIntent();
        character = intent.getStringArrayExtra("character");

        characterName.setText(character[1]);
        characterDescripition.setText(character[2]);
        Picasso.get().load(character[3]).fit().centerInside().into(characterPicture);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CharacterActivity.this, ComicsActivity.class);
                i.putExtra("id", character[0]);
                startActivity(i);
            }
        });
    }
}
