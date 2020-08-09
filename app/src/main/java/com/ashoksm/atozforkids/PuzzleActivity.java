package com.ashoksm.atozforkids;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.ashoksm.atozforkids.adapter.MainGridAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.RecyclerItemClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.android.gms.ads.mediation.MediationAdConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE;

public class PuzzleActivity extends AppCompatActivity {

    private static final List<ItemsDTO> TITLES = new ArrayList<>();

    static {
        TITLES.add(new ItemsDTO("Spell Me", R.drawable.puzzles));
        TITLES.add(new ItemsDTO("Find Me", R.drawable.puzzles));
        TITLES.add(new ItemsDTO("Let's Count", R.drawable.puzzles));
        TITLES.add(new ItemsDTO("Memory Game", R.drawable.puzzles));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        loadAd();

        final RecyclerView recyclerView = findViewById(R.id.gridView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        MainGridAdapter adapter = new MainGridAdapter(getResources(), TITLES);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                switch (position) {
                                    case 0:
                                        startActivity(DragAndDropActivity.class);
                                        break;
                                    case 1:
                                        startActivity(FindImageActivity.class);
                                        break;
                                    case 2:
                                        startActivity(LetsCountActivity.class);
                                        break;
                                    case 3:
                                        startActivity(FindPairActivity.class);
                                        break;
                                }
                            }
                        })
        );
    }

    private void startActivity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, 0);
    }

    private void loadAd() {
        // Initialize ads
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
    }
}
