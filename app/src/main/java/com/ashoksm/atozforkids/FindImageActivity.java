package com.ashoksm.atozforkids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.ashoksm.atozforkids.dto.ItemsDTO;

import java.util.HashSet;
import java.util.Set;

public class FindImageActivity extends AppCompatActivity {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image3 = (ImageView) findViewById(R.id.image3);

        Set<ItemsDTO> itemsDTOs = new HashSet<>();

    }
}
