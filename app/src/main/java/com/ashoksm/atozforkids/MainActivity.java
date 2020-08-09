package com.ashoksm.atozforkids;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ashoksm.atozforkids.adapter.MainGridAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.RecyclerItemClickListener;
import com.ashoksm.atozforkids.utils.SettingsDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.android.gms.ads.mediation.MediationAdConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE;

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
        titles.add(new ItemsDTO("Vehicles", R.drawable.vehicles));
        titles.add(new ItemsDTO("Solar System", R.drawable.solar_system));
        titles.add(new ItemsDTO("Body Parts", R.drawable.body_parts));
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

        //Initialize ads
        RequestConfiguration requestConfiguration = MobileAds.getRequestConfiguration()
                .toBuilder()
                .setTestDeviceIds(Collections.singletonList("9BC8733BB5188E6CD270A2AEAB2B1FC8"))
                .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                .build();

        // load banner Ad
        MobileAds.setRequestConfiguration(requestConfiguration);
        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = newInterstitialAd();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final RecyclerView recyclerView = findViewById(R.id.gridView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
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

    private void startActivity(Class<? extends AppCompatActivity> clazz, String itemName) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(EXTRA_ITEM_NAME, itemName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, 0);
    }


    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
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

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left, 0);
        mInterstitialAd = newInterstitialAd();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                            Log.i("SliderActivity",
                                    "TextToSpeech onInit failed with status::::::::" + status);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            SettingsDialog settingsDialog = new SettingsDialog(this);
            settingsDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}