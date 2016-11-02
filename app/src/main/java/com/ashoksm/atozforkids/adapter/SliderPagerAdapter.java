package com.ashoksm.atozforkids.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.SliderActivity;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private List<ItemsDTO> items;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private Animation shake;
    private TextToSpeech textToSpeech;
    private int width;
    private int height;
    private String itemName;

    public SliderPagerAdapter(List<ItemsDTO> itemsIn, Context contextIn, TextToSpeech textToSpeech,
                              int widthIn, int heightIn, String itemNameIn) {
        this.items = itemsIn;
        this.context = contextIn;
        mLayoutInflater =
                (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shake = AnimationUtils.loadAnimation(contextIn, R.anim.shake);
        this.textToSpeech = textToSpeech;
        this.width = widthIn;
        this.height = heightIn;
        this.itemName = itemNameIn;
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
        Bitmap imageBitmap =
                DecodeSampledBitmapFromResource.execute(context.getResources(), items.get
                        (position).getImageResource(), width, height);

        Palette palette = Palette.from(imageBitmap).generate();
        if ("Alphabets".equals(itemName)) {
            TextView alphabet = (TextView) itemView.findViewById(R.id.alphabet);
            alphabet.setVisibility(View.VISIBLE);
            String alp = String.valueOf(items.get(position).getItemName().charAt(0)).toUpperCase()
                    + String.valueOf(items.get(position).getItemName().charAt(0)).toLowerCase();
            alphabet.setText(alp);
            int vibrantColor = palette.getVibrantColor(0x000000);
            if (vibrantColor == 0x000000) {
                vibrantColor = palette.getMutedColor(Color.parseColor("#212121"));
            }
            alphabet.setTextColor(vibrantColor);
        }

        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
            name.setBackgroundColor(vibrantSwatch.getRgb());
            name.setTextColor(vibrantSwatch.getTitleTextColor());
            items.get(position).setVibrantColor(vibrantSwatch.getRgb());
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                name.setBackgroundColor(swatch.getRgb());
                name.setTextColor(swatch.getTitleTextColor());
                items.get(position).setVibrantColor(swatch.getRgb());
            }
        }

        final ImageView itemImage = (ImageView) itemView.findViewById(R.id.slider_image);
        itemImage.setImageBitmap(imageBitmap);


        itemImage.clearAnimation();
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shake);
                v.invalidate();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(items.get(SliderActivity.currentItem).getItemName(),
                            TextToSpeech.QUEUE_FLUSH,
                            null, items.get(SliderActivity.currentItem).getItemName());
                } else {
                    textToSpeech.speak(items.get(SliderActivity.currentItem).getItemName(),
                            TextToSpeech.QUEUE_FLUSH,
                            null);
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
        unbindDrawables((View) object);
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
