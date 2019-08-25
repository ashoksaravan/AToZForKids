package com.ashoksm.atozforkids.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.PagerAdapter;

import static com.ashoksm.atozforkids.SliderActivity.CURRENT_ITEM;
import static com.ashoksm.atozforkids.SliderActivity.MEDIA_PLAYER;

public class SliderPagerAdapter extends PagerAdapter {

    private List<ItemsDTO> items;
    private LayoutInflater mLayoutInflater;
    private Activity activity;
    private Animation shake;
    private TextToSpeech textToSpeech;
    private int width;
    private int height;
    private String itemName;
    private SharedPreferences sharedPreferences;

    public SliderPagerAdapter(List<ItemsDTO> itemsIn, Activity activityIn, TextToSpeech
            textToSpeech, int widthIn, int heightIn, String itemNameIn) {
        this.items = itemsIn;
        this.activity = activityIn;
        mLayoutInflater =
                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
        this.textToSpeech = textToSpeech;
        this.width = widthIn;
        this.height = heightIn;
        this.itemName = itemNameIn;
        this.sharedPreferences =
                activity.getSharedPreferences("com.ashoksm.atozforkids.ABCFlashCards",
                        Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.slider_image_view, container, false);


        Bitmap imageBitmap =
                DecodeSampledBitmapFromResource.execute(activity.getResources(), items.get
                        (position).getImageResource(), width, height);

        Palette palette = Palette.from(imageBitmap).generate();
        int vibrantColor = palette.getVibrantColor(0x000000);
        if (vibrantColor == 0x000000) {
            vibrantColor = palette.getMutedColor(Color.parseColor("#212121"));
        }

        if ("Alphabets".equals(itemName)) {
            TextView alphabet = itemView.findViewById(R.id.alphabet);
            alphabet.setVisibility(View.VISIBLE);
            String alp = String.valueOf(items.get(position).getItemName().charAt(0)).toUpperCase()
                    + String.valueOf(items.get(position).getItemName().charAt(0)).toLowerCase();
            alphabet.setText(alp);
            alphabet.setTextColor(vibrantColor);
        }

        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
            items.get(position).setVibrantColor(vibrantSwatch.getRgb());
            items.get(position)
                    .setColorStateList(ColorStateList.valueOf(vibrantSwatch.getTitleTextColor()));
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                items.get(position).setVibrantColor(swatch.getRgb());
                items.get(position)
                        .setColorStateList(ColorStateList.valueOf(swatch.getTitleTextColor()));
            }
        }

        final ImageView itemImage = itemView.findViewById(R.id.slider_image);
        itemImage.setImageBitmap(imageBitmap);


        itemImage.clearAnimation();
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shake);
                v.invalidate();
                if (sharedPreferences.getBoolean("sound", true)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(items.get(CURRENT_ITEM).getItemName(),
                                TextToSpeech.QUEUE_FLUSH, null,
                                items.get(CURRENT_ITEM).getItemName());
                    } else {
                        textToSpeech.speak(items.get(CURRENT_ITEM).getItemName(),
                                TextToSpeech.QUEUE_FLUSH, null);
                    }
                    playAudio();
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
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

    private void playAudio() {
        if (MEDIA_PLAYER != null && items.get(CURRENT_ITEM).getAudioResource() != 0) {
            MEDIA_PLAYER.start();
        }
    }
}
