package com.felipewisniewski.marvelcomics.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felipewisniewski.marvelcomics.Presenter.Comics;
import com.felipewisniewski.marvelcomics.R;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolderComics> {

    private List<Comics> listComics;

    public ComicsAdapter(List<Comics> listCo) {
        this.listComics = listCo;
    }

    @Override
    public ViewHolderComics onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.list_comics, parent, false);
        ViewHolderComics holderComics = new ViewHolderComics(view);

        return holderComics;
    }

    @Override
    public void onBindViewHolder(final ViewHolderComics holder, final int position) {
        if((listComics != null) && (listComics.size() > 0)) {
            final Comics co = listComics.get(position);

            holder.txtComicParticipation.setText(co.getTitle());
        }
    }

    @Override
    public int getItemCount() { return listComics.size(); }

    public class ViewHolderComics extends RecyclerView.ViewHolder {
        public TextView txtComicParticipation;

        public ViewHolderComics(View itemView) {
            super(itemView);
            txtComicParticipation = itemView.findViewById(R.id.txt_comics_list_id);
        }
    }
}
