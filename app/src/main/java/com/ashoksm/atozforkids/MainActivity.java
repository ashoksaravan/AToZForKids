package com.ashoksm.atozforkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ashoksm.atozforkids.adapter.MainGridAdapter;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<ItemsDTO> titles = new ArrayList<>();

    public static final String EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME";

    static {
        titles.add(new ItemsDTO("Alphabets", R.drawable.abc));
        titles.add(new ItemsDTO("Colors", R.drawable.abc));
        titles.add(new ItemsDTO("Shapes", R.drawable.abc));
        titles.add(new ItemsDTO("Numbers", R.drawable.numbers));
        titles.add(new ItemsDTO("Fruits", R.drawable.abc));
        titles.add(new ItemsDTO("Vegetables", R.drawable.abc));
        titles.add(new ItemsDTO("Animals", R.drawable.abc));
        titles.add(new ItemsDTO("Puzzles", R.drawable.abc));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gridView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        MainGridAdapter adapter = new MainGridAdapter(titles);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String itemName = titles.get(position).getItemName();
                                startActivity(SliderActivity.class, itemName);
                            }
                        })
        );
        //int id = getResources().getIdentifier("yourpackagename:drawable/" + StringGenerated, null, null);
    }

    private void startActivity(Class clazz, String itemName) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(EXTRA_ITEM_NAME, itemName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, 0);
    }
}
