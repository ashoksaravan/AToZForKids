package com.ashoksm.atozforkids.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.vo.ItemsVO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;

import java.util.List;

public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.ViewHolder> {

    private List<ItemsVO> titles;
    private Resources res;

    public MainGridAdapter(Resources resIn, List<ItemsVO> titlesIn) {
        this.titles = titlesIn;
        this.res = resIn;
    }

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
            this.title = (TextView) v.findViewById(R.id.title);
            this.titleImg = (ImageView) v.findViewById(R.id.titleImg);
        }
    }
}
