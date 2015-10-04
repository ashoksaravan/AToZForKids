package com.ashoksm.atozforkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ashoksm.atozforkids.adapter.SliderPagerAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DepthPageTransformer;

import java.util.List;

public class SliderActivity extends AppCompatActivity {

    private List<ItemsDTO> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Intent intent = getIntent();

        String itemName = intent.getStringExtra(MainActivity.EXTRA_ITEM_NAME);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(itemName);
        }
        final TextView name = (TextView) findViewById(R.id.name);
        ViewPager viewPager = (ViewPager) findViewById(R.id.slider);

        switch (itemName) {
            case "Alphabets" :
                items = DataStore.getInstance().getAlphabets();
                break;
        }
        name.setText(items.get(0).getItemName());
        SliderPagerAdapter adapter = new SliderPagerAdapter(items, this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                name.setText(items.get(position).getItemName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

}
