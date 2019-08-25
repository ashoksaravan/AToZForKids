package com.ashoksm.atozforkids.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.ViewHolder> {

    private List<ItemsDTO> titles;
    private Resources res;

    public MainGridAdapter(Resources resIn, List<ItemsDTO> titlesIn) {
        this.titles = titlesIn;
        this.res = resIn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid, parent, false);
        // set the view's size, margins, padding's and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(titles.get(position).getItemName());
        holder.titleImg.setImageBitmap(DecodeSampledBitmapFromResource
                .execute(res, titles.get(position).getImageResource(),
                        240, 120));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView titleImg;

        ViewHolder(View v) {
            super(v);
            this.title = v.findViewById(R.id.title);
            this.titleImg = v.findViewById(R.id.titleImg);
        }
    }
}
