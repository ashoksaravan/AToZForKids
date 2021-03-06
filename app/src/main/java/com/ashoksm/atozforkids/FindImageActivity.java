package com.ashoksm.atozforkids;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.AppRater;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.RandomNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class FindImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView right;
    private Animation fadeInAnimation;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image11;
    private ImageView image21;
    private ImageView image31;
    private ImageView image41;
    private ItemsDTO itemsDTO;
    private TextToSpeech textToSpeech;
    private View view;
    private int randInt;
    private MediaPlayer mediaPlayer;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x - 100;
        int height = point.y - getStatusBarHeight() - (getActionBarHeight() * 2) - 10;

        size = Double.valueOf(Math.sqrt((width / 2d) * (height / 2d))).intValue();
        if (size > (width / 2)) {
            size = width / 2;
        } else if (size > (height / 2)) {
            size = height / 2;
        }

        view = findViewById(R.id.find_image_main_view);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image11 = findViewById(R.id.image11);
        image21 = findViewById(R.id.image21);
        image31 = findViewById(R.id.image31);
        image41 = findViewById(R.id.image41);
        right = findViewById(R.id.right);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image2.getLayoutParams().height = size;
        image2.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        loadImages(false);

        initTTS();

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                right.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
                unbindDrawables(view);
                System.gc();
                loadImages(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);

        image1.setTag(image11);
        image2.setTag(image21);
        image3.setTag(image31);
        image4.setTag(image41);

        mediaPlayer = MediaPlayer.create(this, R.raw.applause);
        AppRater.appLaunched(this);
    }

    private void initTTS() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(0.8f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        try {
                            Set<Voice> voices = textToSpeech.getVoices();
                            for (Voice v : voices) {
                                if (v.getLocale().equals(Locale.getDefault())) {
                                    textToSpeech.setVoice(v);
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("Voice not found", "Voice not found", ex);
                        }
                    }
                    speak("Click " + itemsDTO.getItemName());
                } else {
                    Log.i("SliderActivity",
                            "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });
    }

    private void loadImages(boolean speak) {
        List<ItemsDTO> items = new ArrayList<>();
        items.add(DataStore.getInstance().getAlphabets().get(RandomNumber.randInt(0,
                DataStore.getInstance().getAlphabets().size() - 1)));
        items.add(DataStore.getInstance().getAnimals().get(RandomNumber.randInt(0,
                DataStore.getInstance().getAnimals().size() - 1)));
        items.add(DataStore.getInstance().getFruits().get(RandomNumber.randInt(0,
                DataStore.getInstance().getFruits().size() - 1)));
        items.add(DataStore.getInstance().getVegetables().get(RandomNumber.randInt(0,
                DataStore.getInstance().getVegetables().size() - 1)));

        Collections.shuffle(items);
        itemsDTO = items.get(RandomNumber.randInt(0, 3));

        if (speak) {
            speak("Click " + itemsDTO.getItemName());
        }


        image1.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(),
                items.get(0).getImageResource(), size, size));
        image11.setTag(items.get(0).getItemName());
        image2.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(),
                items.get(1).getImageResource(), size, size));
        image21.setTag(items.get(1).getItemName());
        image3.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(),
                items.get(2).getImageResource(), size, size));
        image31.setTag(items.get(2).getItemName());
        image4.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(),
                items.get(3).getImageResource(), size, size));
        image41.setTag(items.get(3).getItemName());

        image11.setVisibility(View.GONE);
        image21.setVisibility(View.GONE);
        image31.setVisibility(View.GONE);
        image41.setVisibility(View.GONE);

        image1.setEnabled(true);
        image2.setEnabled(true);
        image3.setEnabled(true);
        image4.setEnabled(true);
    }

    private void speak(String s) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
            } else {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No TTS Found!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    @Override
    public void onClick(View view) {
        view.setEnabled(false);
        if (((ImageView) view.getTag()).getTag().equals(itemsDTO.getItemName())) {
            while (true) {
                int temp = RandomNumber.randInt(0, DataStore.getInstance()
                        .getStatusValues().size() - 1);
                if (temp != randInt) {
                    randInt = temp;
                    break;
                }
            }
            speak(DataStore.getInstance().getStatusValues().get(randInt));
            image1.setEnabled(false);
            image2.setEnabled(false);
            image3.setEnabled(false);
            image4.setEnabled(false);
            right.setVisibility(View.VISIBLE);
            right.startAnimation(fadeInAnimation);
            mediaPlayer.start();
        } else {
            speak("Try Again");
            ((ImageView) view.getTag()).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speaker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_play:
                speak("Click " + itemsDTO.getItemName());
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left, 0);
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources()
                    .getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
