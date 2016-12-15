package com.ashoksm.atozforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SliderActivity extends AppCompatActivity {

    public static int CURRENT_ITEM;
    private List<ItemsDTO> items = null;
    private TextToSpeech textToSpeech;
    public static final String EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME";
    public static final String EXTRA_ITEM_IMAGE_RESOURCE = "EXTRA_ITEM_IMAGE_RESOURCE";
    public static MediaPlayer MEDIA_PLAYER;
    private boolean exitPressedOnce;
    private SharedPreferences sharedPref;
    private Timer timer;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Intent intent = getIntent();

        loadAd();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        CURRENT_ITEM = 0;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPref = getSharedPreferences("com.ashoksm.atozforkids.ABCFlashCards",
                Context.MODE_PRIVATE);
        viewPager = (ViewPager) findViewById(R.id.slider);
        final TextView spell = (TextView) findViewById(R.id.spell);
        final BottomNavigationView bottomNavView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);
        final String itemName = intent.getStringExtra(MainActivity.EXTRA_ITEM_NAME);

        initTTS(itemName);
        loadItems(itemName);

        if ("Numbers".equalsIgnoreCase(itemName)) {
            setRandomImage(items);
            viewPager.setAdapter(new GridPagerAdapter(this, items));
            spell.setVisibility(View.GONE);
        } else {
            final SliderPagerAdapter adapter =
                    new SliderPagerAdapter(items, this, textToSpeech, width, height, itemName);
            viewPager.setAdapter(adapter);
        }


        bottomNavView.setItemBackgroundResource(R.drawable.transparent);
        Bitmap imageBitmap = DecodeSampledBitmapFromResource.execute(getResources(), items.get
                (0).getImageResource(), width, height);
        Palette palette = Palette.from(imageBitmap).generate();
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
            bottomNavView.setBackgroundColor(vibrantSwatch.getRgb());
            ColorStateList colorStateList =
                    ColorStateList.valueOf(vibrantSwatch.getTitleTextColor());
            bottomNavView.setItemIconTintList(colorStateList);
            bottomNavView.setItemTextColor(colorStateList);
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                bottomNavView.setBackgroundColor(swatch.getRgb());
                ColorStateList colorStateList =
                        ColorStateList.valueOf(swatch.getTitleTextColor());
                bottomNavView.setItemIconTintList(colorStateList);
                bottomNavView.setItemTextColor(colorStateList);
            }
        }

        int vibrantColor = palette.getVibrantColor(0x000000);
        if (vibrantColor == 0x000000) {
            vibrantColor = palette.getMutedColor(Color.parseColor("#212121"));
        }
        spell.setTextColor(vibrantColor);

        addPageChangeListener(spell, bottomNavView, itemName);
        viewPager.setPageTransformer(false, new DepthPageTransformer());
        addBottomNavListener(bottomNavView, itemName);

        spell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemsDTO itemsDTO = items.get(CURRENT_ITEM);
                Intent intent = new Intent(getApplicationContext(), DragAndDropActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SliderActivity.EXTRA_ITEM_NAME, itemsDTO.getItemName());
                intent.putExtra(SliderActivity.EXTRA_ITEM_IMAGE_RESOURCE,
                        itemsDTO.getImageResource());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, 0);
            }
        });

        if (items.get(CURRENT_ITEM).getAudioResource() != 0
                && sharedPref.getBoolean("sound", true)) {
            MEDIA_PLAYER = MediaPlayer.create(this, items.get(CURRENT_ITEM).getAudioResource());
            playAudio();
        }

        if (sharedPref.getBoolean("auto_play", false)) {
            enableAutoPageSwitcher();
        }
    }

    private void initTTS(final String itemName) {
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
    }

    private void addBottomNavListener(BottomNavigationView bottomNavView, final String itemName) {
        bottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_previous:
                                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                break;
                            case R.id.action_next:
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                break;
                            case R.id.action_speaker:
                                speak(CURRENT_ITEM, itemName);
                                playAudio();
                                break;
                        }
                        return false;
                    }
                });
    }

    private void addPageChangeListener(final TextView spell, final BottomNavigationView bottomNavView,
                                       final String itemName) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int posOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (items.get(position).getColorStateList() != null) {
                    bottomNavView.setBackgroundColor(items.get(position).getVibrantColor());
                    bottomNavView.setItemIconTintList(items.get(position).getColorStateList());
                    bottomNavView.setItemTextColor(items.get(position).getColorStateList());
                    spell.setTextColor(items.get(position).getVibrantColor());
                } else {
                    bottomNavView
                            .setBackgroundColor(getColor(getApplicationContext(), R.color.primary));
                    bottomNavView.setItemIconTintList(ColorStateList.valueOf(getColor
                            (getApplicationContext(), R.color.icons)));
                    bottomNavView.setItemTextColor(ColorStateList.valueOf(getColor
                            (getApplicationContext(), R.color.icons)));
                }
                speak(position, itemName);
                CURRENT_ITEM = position;
                if (MEDIA_PLAYER != null) {
                    MEDIA_PLAYER.stop();
                    MEDIA_PLAYER.release();
                    MEDIA_PLAYER = null;
                }
                if (items.get(CURRENT_ITEM).getAudioResource() != 0
                        && sharedPref.getBoolean("sound", true)) {
                    MEDIA_PLAYER = MediaPlayer.create(SliderActivity.this, items.get(CURRENT_ITEM)
                            .getAudioResource());
                }
                playAudio();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void loadItems(String itemName) {
        List<ItemsDTO> dtos = null;
        switch (itemName) {
            case "Alphabets":
                dtos = DataStore.getInstance().getAlphabets();
                break;
            case "Colors":
                dtos = DataStore.getInstance().getColors();
                break;
            case "Shapes":
                dtos = DataStore.getInstance().getShapes();
                break;
            case "Numbers":
                dtos = DataStore.getInstance().getNumbers();
                break;
            case "Animals":
                dtos = DataStore.getInstance().getAnimals();
                break;
            case "Fruits":
                dtos = DataStore.getInstance().getFruits();
                break;
            case "Vegetables":
                dtos = DataStore.getInstance().getVegetables();
                break;
            case "Vehicles":
                dtos = DataStore.getInstance().getVehicles();
                break;
            case "Body Parts":
                dtos = DataStore.getInstance().getBodyParts();
                break;
        }
        if (sharedPref.getBoolean("random", false)) {
            if (dtos != null) {
                items = new ArrayList<>();
                items.addAll(dtos);
                Collections.shuffle(items);
            }
        } else {
            items = dtos;
        }
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
        if (sharedPref.getBoolean("sound", true)) {
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

    private int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    private void playAudio() {
        if (MEDIA_PLAYER != null && items.get(CURRENT_ITEM).getAudioResource() != 0) {
            MEDIA_PLAYER.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MEDIA_PLAYER != null) {
            MEDIA_PLAYER.stop();
            MEDIA_PLAYER.release();
            MEDIA_PLAYER = null;
        }
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MEDIA_PLAYER != null) {
            MEDIA_PLAYER.pause();
        }
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (!exitPressedOnce) {
            exitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    private void enableAutoPageSwitcher() {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), (sharedPref.getInt("interval", 3) + 2) * 1000,
                sharedPref.getInt("interval", 3) * 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a separate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (viewPager.getCurrentItem() > items.size()) {
                        timer.cancel();
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
            });

        }
    }
}
