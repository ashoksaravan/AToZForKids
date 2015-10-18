package com.ashoksm.atozforkids;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ashoksm.atozforkids.adapter.SliderPagerAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;

import java.util.List;
import java.util.Locale;

public class SliderActivity extends AppCompatActivity {

    private List<ItemsDTO> items = null;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Intent intent = getIntent();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String itemName = intent.getStringExtra(MainActivity.EXTRA_ITEM_NAME);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(0.8f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        try {
                            Voice v = textToSpeech.getVoices().iterator().next();
                            textToSpeech.setVoice(v);
                        } catch (Exception ex) {
                            Log.e("Voice not found", ex.getMessage());
                        }
                    }
                    speak(0, itemName);
                } else {
                    Log.i("SliderActivity", "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(itemName);
        }
        final ViewPager viewPager = (ViewPager) findViewById(R.id.slider);

        switch (itemName) {
            case "Alphabets":
                items = DataStore.getInstance().getAlphabets();
                break;
            case "Colors":
                items = DataStore.getInstance().getColors();
                break;
            case "Shapes":
                items = DataStore.getInstance().getShapes();
                break;
            case "Numbers":
                items = DataStore.getInstance().getNumbers();
                break;
            case "Animals":
                items = DataStore.getInstance().getAnimals();
                break;
            case "Fruits":
                items = DataStore.getInstance().getFruits();
                break;
            case "Vegetables":
                items = DataStore.getInstance().getVegetables();
                break;
        }
        final SliderPagerAdapter adapter = new SliderPagerAdapter(items, this, textToSpeech);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                speak(position, itemName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //viewPager.setPageTransformer(true, new DepthPageTransformer());

        final ImageButton replay = (ImageButton) findViewById(R.id.replay);
        final ImageButton next = (ImageButton) findViewById(R.id.next);
        final ImageButton previous = (ImageButton) findViewById(R.id.previous);
        replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

    }

    private void speak(int position, String itemName) {
        try {
            String s;
            if (itemName.equalsIgnoreCase("Alphabets")) {
                s = items.get(position).getItemName().substring(0, 1) + " For " + items.get(position).getItemName();
            } else {
                s = items.get(position).getItemName();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
            } else {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No TTS Found!!!", Toast.LENGTH_LONG).show();
        }
    }

}
