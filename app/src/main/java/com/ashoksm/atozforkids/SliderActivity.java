package com.ashoksm.atozforkids;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashoksm.atozforkids.adapter.GridPagerAdapter;
import com.ashoksm.atozforkids.adapter.SliderPagerAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.DepthPageTransformer;
import com.ashoksm.atozforkids.utils.RandomNumber;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SliderActivity extends AppCompatActivity {

    public static int currentItem = 0;
    private List<ItemsDTO> items = null;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Intent intent = getIntent();

        int width;
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            width = display.getWidth();
            height = display.getHeight();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loadAd();
        final String itemName = intent.getStringExtra(MainActivity.EXTRA_ITEM_NAME);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(1.0f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        try {
                            Set<Voice> voices = textToSpeech.getVoices();
                            for (Voice v : voices) {
                                if (v.getLocale().equals(Locale.getDefault())) {
                                    textToSpeech.setVoice(v);
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("Voice not found", ex.getMessage());
                        }
                    }
                    speak(0, itemName);
                } else {
                    Log.i("SliderActivity",
                            "TextToSpeech onInit failed with status::::::::" + status);
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
            case "Vehicles":
                items = DataStore.getInstance().getVehicles();
                break;
            case "Body Parts":
                items = DataStore.getInstance().getBodyParts();
                break;
        }

        if ("Numbers".equalsIgnoreCase(itemName)) {
            setRandomImage(items);
            viewPager.setAdapter(new GridPagerAdapter(this, items));
        } else {
            final SliderPagerAdapter adapter =
                    new SliderPagerAdapter(items, this, textToSpeech, width, height, itemName);
            viewPager.setAdapter(adapter);
        }

        final LinearLayout actionLayout = (LinearLayout) findViewById(R.id.actionLayout);
        Bitmap imageBitmap = DecodeSampledBitmapFromResource.execute(getResources(), items.get
                (0).getImageResource(), width, height);
        Palette palette = Palette.from(imageBitmap).generate();
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
            actionLayout.setBackgroundColor(vibrantSwatch.getRgb());
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                actionLayout.setBackgroundColor(swatch.getRgb());
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                actionLayout.setBackgroundColor(items.get(position).getVibrantColor());
                speak(position, itemName);
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setPageTransformer(false, new DepthPageTransformer());

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

    private void setRandomImage(List<ItemsDTO> items) {
        for (ItemsDTO item :
                items) {
            int i = RandomNumber.randInt(0, 3);
            switch (i) {
                case 0:
                    item.setImageResource(DataStore.getInstance().getAlphabets().get(RandomNumber
                            .randInt(0, DataStore.getInstance().getAlphabets().size() - 1))
                            .getImageResource());
                    break;
                case 1:
                    item.setImageResource(DataStore.getInstance().getFruits().get(RandomNumber
                            .randInt(0, DataStore.getInstance().getFruits().size() - 1))
                            .getImageResource());
                    break;
                case 2:
                    item.setImageResource(DataStore.getInstance().getAnimals().get(RandomNumber
                            .randInt(0, DataStore.getInstance().getAnimals().size() - 1))
                            .getImageResource());
                    break;
                case 3:
                    item.setImageResource(DataStore.getInstance().getVegetables().get(RandomNumber
                            .randInt(0, DataStore.getInstance().getVegetables().size() - 1))
                            .getImageResource());
                    break;
            }
        }
    }

    private void speak(int position, String itemName) {
        try {
            String s;
            if (itemName.equalsIgnoreCase("Alphabets")) {
                s = items.get(position).getItemName().substring(0, 1) + " For " +
                        items.get(position).getItemName();
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

    private void loadAd() {
        // load ad
        final LinearLayout adParent = (LinearLayout) this.findViewById(R.id.adLayout);
        final AdView ad = new AdView(this);
        ad.setAdUnitId(getString(R.string.admob_banner_id));
        ad.setAdSize(AdSize.SMART_BANNER);

        final AdListener listener = new AdListener() {
            @Override
            public void onAdLoaded() {
                adParent.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adParent.setVisibility(View.GONE);
                super.onAdFailedToLoad(errorCode);
            }
        };

        ad.setAdListener(listener);

        adParent.addView(ad);
        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest adRequest = builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        ad.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentItem = 0;
    }
}
