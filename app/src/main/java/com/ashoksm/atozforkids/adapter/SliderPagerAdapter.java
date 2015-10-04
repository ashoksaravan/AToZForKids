package com.ashoksm.atozforkids.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    List<ItemsDTO> items;
    LayoutInflater mLayoutInflater;
    Context context;

    public SliderPagerAdapter(List<ItemsDTO> itemsIn, Context contextIn) {
        this.items = itemsIn;
        this.context = contextIn;
        mLayoutInflater = (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.slider_image_view, container, false);

        final ImageView performerImage = (ImageView) itemView.findViewById(R.id.slider_image);
        performerImage.setImageResource(items.get(position).getImageResource());
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
