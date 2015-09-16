package com.ashoksm.atozforkids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.com.ashoksm.atozforkids.dto.ItemsDTO;

import java.util.List;

public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.ViewHolder> {

    private List<ItemsDTO> titles;

    public MainGridAdapter(List<ItemsDTO> titlesIn) {
        this.titles = titlesIn;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid, parent, false);
        // set the view's size, margins, padding's and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(titles.get(position).getItemName());
        holder.titleImg.setImageResource(titles.get(position).getImageResource());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView titleImg;

        public ViewHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.title);
            this.titleImg = (ImageView) v.findViewById(R.id.titleImg);
        }
    }
}
