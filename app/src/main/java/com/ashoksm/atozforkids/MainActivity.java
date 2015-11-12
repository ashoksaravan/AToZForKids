package com.ashoksm.atozforkids;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashoksm.atozforkids.adapter.MainGridAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.GridSpacingItemDecoration;
import com.ashoksm.atozforkids.utils.RecyclerItemClickListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME";
    private static final List<ItemsDTO> titles = new ArrayList<>();
    private static final int MY_DATA_CHECK_CODE = 111;

    static {
        titles.add(new ItemsDTO("Alphabets", R.drawable.abc));
        titles.add(new ItemsDTO("Numbers", R.drawable.numbers));
        titles.add(new ItemsDTO("Colors", R.drawable.colors));
        titles.add(new ItemsDTO("Shapes", R.drawable.shapes));
        titles.add(new ItemsDTO("Animals", R.drawable.animals));
        titles.add(new ItemsDTO("Fruits", R.drawable.fruits));
        titles.add(new ItemsDTO("Vegetables", R.drawable.vegetables));
        titles.add(new ItemsDTO("Puzzles", R.drawable.puzzles));
    }

    private TextToSpeech mTts;
    private InterstitialAd mInterstitialAd;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        loadAd();
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        int width;
        int height;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
        ImageView mainBg = (ImageView) findViewById(R.id.main_bg);
        mainBg.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), R.drawable.bg, width, height));

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gridView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 50, true));
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        MainGridAdapter adapter = new MainGridAdapter(getResources(), titles);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                itemName = titles.get(position).getItemName();
                                showInterstitial();
                            }
                        })
        );
    }

    private void startActivity(Class clazz, String itemName) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(EXTRA_ITEM_NAME, itemName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, 0);
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

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdClosed() {
                if ("Puzzles".equals(itemName)) {
                    startActivity(PuzzleActivity.class, itemName);
                } else {
                    startActivity(SliderActivity.class, itemName);
                }
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            if ("Puzzles".equals(itemName)) {
                startActivity(PuzzleActivity.class, itemName);
            } else {
                startActivity(SliderActivity.class, itemName);
            }
        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left, 0);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            mTts.setLanguage(Locale.getDefault());
                            mTts.setPitch(0.8f);
                        } else {
                            Log.i("SliderActivity", "TextToSpeech onInit failed with status::::::::" + status);
                        }
                    }
                });
            } else {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

    }

    private void speak(String s) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTts.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
            } else {
                mTts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No TTS Found!!!", Toast.LENGTH_LONG).show();
        }
    }
}