package com.felipewisniewski.marvelcomics.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolderMarvels holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

