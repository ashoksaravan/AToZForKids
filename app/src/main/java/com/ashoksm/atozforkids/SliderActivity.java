package com.ashoksm.atozforkids;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ashoksm.atozforkids.adapter.SliderPagerAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DepthPageTransformer;

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


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(0.8f);
                    speak(0);
                } else {
                    Log.i("SliderActivity", "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        String itemName = intent.getStringExtra(MainActivity.EXTRA_ITEM_NAME);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(itemName);
        }
        final TextView name = (TextView) findViewById(R.id.name);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.slider);

        switch (itemName) {
            case "Alphabets":
                items = DataStore.getInstance().getAlphabets();
                break;
        }
        String html = "<font size='8' color='#FF4081'><b><FIRST></b></font><font size='7' " +
                "color='#FFFFFF'><SECOND></font>";
        html = html.replaceAll("<FIRST>", items.get(0).getItemName().substring(0, 1)).replaceAll
                ("<SECOND>", items.get(0).getItemName().substring(1));
        name.setText(Html.fromHtml(html));
        SliderPagerAdapter adapter = new SliderPagerAdapter(items, this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String html = "<font size='7' color='#FF4081'><FIRST></font><font size='6' " +
                        "color='#FFFFFF'><SECOND></font>";
                html = html.replaceAll("<FIRST>", items.get(position).getItemName().substring(0, 1)).replaceAll
                        ("<SECOND>", items.get(position).getItemName().substring(1));
                name.setText(Html.fromHtml(html));
                speak(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setPageTransformer(true, new DepthPageTransformer());

        final ImageButton replay = (ImageButton) findViewById(R.id.replay);
        final Button next = (Button) findViewById(R.id.next);
        final Button previous = (Button) findViewById(R.id.previous);
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

    private void speak(int position) {
        try {
            String s = items.get(position).getItemName().substring(0, 1) + " For " + items.get(position).getItemName();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Voice v = textToSpeech.getVoices().iterator().next();
                    textToSpeech.setVoice(v);
                    textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
            } else {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No TTS Found!!!", Toast.LENGTH_LONG).show();
        }
    }

}
