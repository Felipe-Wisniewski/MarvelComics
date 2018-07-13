package com.felipewisniewski.marvelcomics.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.Business.Character;
import com.felipewisniewski.marvelcomics.R;

import java.util.List;

public class MarvelsAdapter extends RecyclerView.Adapter<MarvelsAdapter.ViewHolderMarvels> {

    private List<Character> listCharacter;
    private Context context;

    public MarvelsAdapter(List<Character> listC, Context MarvelsActivity) {
        this.listCharacter = listC;
        this.context = MarvelsActivity;
    }

    @Override
    public ViewHolderMarvels onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.list_character, parent, false);
        ViewHolderMarvels holderMarvels = new ViewHolderMarvels(view);

        return holderMarvels;
    }

    @Override
    public void onBindViewHolder(final MarvelsAdapter.ViewHolderMarvels holder, final int position) {
        if((listCharacter != null) && (listCharacter.size() > 0)) {
            final Character cha = listCharacter.get(position);


            holder.txtName.setText(cha.getName());
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

