package com.ashoksm.atozforkids.adapter;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    List<ItemsDTO> items;
    LayoutInflater mLayoutInflater;
    Context context;
    Animation shake;
    TextToSpeech textToSpeech;

    public SliderPagerAdapter(List<ItemsDTO> itemsIn, Context contextIn, TextToSpeech textToSpeech) {
        this.items = itemsIn;
        this.context = contextIn;
        mLayoutInflater = (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shake = AnimationUtils.loadAnimation(contextIn, R.anim.shake);
        this.textToSpeech = textToSpeech;
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
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(items.get(position).getItemName());
        final ImageView performerImage = (ImageView) itemView.findViewById(R.id.slider_image);
        performerImage.setImageResource(items.get(position).getImageResource());
        performerImage.setTag(items.get(position).getItemName());
        performerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearAnimation();
                v.startAnimation(shake);
                v.invalidate();
                String s = (String) v.getTag();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
                } else {
                    textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
