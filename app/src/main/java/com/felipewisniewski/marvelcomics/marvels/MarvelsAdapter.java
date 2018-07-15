package com.felipewisniewski.marvelcomics.marvels;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.Business.Character;
import com.felipewisniewski.marvelcomics.R;
import com.felipewisniewski.marvelcomics.comics.CharacterActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MarvelsAdapter extends RecyclerView.Adapter<MarvelsAdapter.ViewHolderMarvels> {

    private List<Character> listCharacter;
    private Context context;

    public MarvelsAdapter(List<Character> listC, Context marvelsActivity) {
        this.listCharacter = listC;
        this.context = marvelsActivity;
    }

    @Override
    public ViewHolderMarvels onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.list_characters, parent, false);
        ViewHolderMarvels holderMarvels = new ViewHolderMarvels(view);

        return holderMarvels;
    }

    @Override
    public void onBindViewHolder(final ViewHolderMarvels holder, final int position) {
        if((listCharacter != null) && (listCharacter.size() > 0)) {
            final Character cha = listCharacter.get(position);
            final String[] character = {cha.getId(), cha.getName(), cha.getDescription(), cha.getThumbnail()};

            Picasso.get().load(cha.getThumbnail()).fit().centerInside().into(holder.imgHero);
            holder.txtName.setText(cha.getName());

            holder.txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CharacterActivity.class);
                    intent.putExtra("character", character);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listCharacter.size();
    }


    public class ViewHolderMarvels extends RecyclerView.ViewHolder {
        public TextView txtName;
        public ImageView imgHero;

        public ViewHolderMarvels(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.nameCharacterId);
            imgHero = itemView.findViewById(R.id.imageCharacterId);
        }
    }
}

