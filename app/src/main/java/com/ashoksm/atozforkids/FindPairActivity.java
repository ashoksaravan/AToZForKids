package com.ashoksm.atozforkids;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.RandomNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindPairActivity extends AppCompatActivity
        implements OnClickListener, AnimationListener {

    private Animation animation1;
    private Animation animation2;
    private View view;
    private int totalCount;
    private int currentCount;
    private int viewCount;
    private List<ItemsDTO> itemsDTOs;
    private List<View> views = new ArrayList<>();
    private List<ImageView> imageViews = null;
    private SparseArray<Bitmap> bitmapCache;
    private boolean showPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pair);

        bitmapCache = new SparseArray<>();
        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);

        viewCount = 0;
        totalCount = 4;
        populateImage();
        renderView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageViews.get(0).setEnabled(true);
                clickListener(imageViews.get(0));
            }
        }, 3000);
    }

    private void renderView() {
        imageViews = new ArrayList<>();
        int size;
        if (totalCount == 4) {
            size = 300;
            bitmapCache = new SparseArray<>();
            bitmapCache.put(R.drawable.question_mark, DecodeSampledBitmapFromResource.execute
                    (getResources(), R.drawable.question_mark, size, size));
            findViewById(R.id.row_1).setVisibility(View.VISIBLE);
            findViewById(R.id.row_2).setVisibility(View.VISIBLE);
            findViewById(R.id.row_3).setVisibility(View.GONE);
            findViewById(R.id.row_4).setVisibility(View.GONE);

            int j = 0;
            for (int i = 1; i <= 8; i++) {
                int imageId = getResources().getIdentifier("image" + i, "id", getPackageName());
                if (i != 3 && i != 4 && i != 7 && i != 8) {
                    loadImage(size, j, imageId);
                    j++;
                } else {
                    findViewById(imageId).setVisibility(View.GONE);
                }
            }
        } else if (totalCount == 6) {
            size = 300;
            bitmapCache = new SparseArray<>();
            bitmapCache.put(R.drawable.question_mark, DecodeSampledBitmapFromResource.execute
                    (getResources(), R.drawable.question_mark, size, size));
            int count;
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                findViewById(R.id.row_3).setVisibility(View.VISIBLE);
                findViewById(R.id.row_4).setVisibility(View.GONE);
                count = 12;
            } else {
                count = 8;
            }

            int j = 0;
            for (int i = 1; i <= count; i++) {
                int imageId = getResources().getIdentifier("image" + i, "id", getPackageName());
                if ((getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT && i != 3 && i != 4 && i != 7 && i != 8
                        && i != 11 && i != 12) || (getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE && i != 4 && i != 8)) {
                    loadImage(size, j, imageId);
                    j++;
                } else {
                    findViewById(imageId).setVisibility(View.GONE);
                }
            }
        } else if (totalCount == 8) {
            size = 300;
            bitmapCache = new SparseArray<>();
            bitmapCache.put(R.drawable.question_mark, DecodeSampledBitmapFromResource.execute
                    (getResources(), R.drawable.question_mark, size, size));

            int count;
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                findViewById(R.id.row_4).setVisibility(View.VISIBLE);
                count = 16;
            } else {
                count = 8;
            }

            int j = 0;
            for (int i = 1; i <= count; i++) {
                int imageId = getResources().getIdentifier("image" + i, "id", getPackageName());
                if ((i != 3 && i != 4 && i != 7 && i != 8 && i != 11 && i != 12 && i != 15 &&
                        i != 16) || getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE) {
                    loadImage(size, j, imageId);
                    j++;
                } else {
                    findViewById(imageId).setVisibility(View.GONE);
                }
            }
        } else if (totalCount == 12) {
            bitmapCache = new SparseArray<>();

            int count;
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                findViewById(R.id.row_3).setVisibility(View.VISIBLE);
                count = 12;
                size = 200;
            } else {
                count = 16;
                size = 250;
            }
            bitmapCache.put(R.drawable.question_mark, DecodeSampledBitmapFromResource.execute
                    (getResources(), R.drawable.question_mark, size, size));

            int j = 0;
            for (int i = 1; i <= count; i++) {
                int imageId = getResources().getIdentifier("image" + i, "id", getPackageName());
                if ((i != 3 && i != 7 && i != 11 && i != 16) ||
                        getResources().getConfiguration().orientation ==
                                Configuration.ORIENTATION_LANDSCAPE) {
                    loadImage(size, j, imageId);
                    j++;
                } else {
                    findViewById(imageId).setVisibility(View.GONE);
                }
            }
        } else if (totalCount == 16) {
            size = 200;
            bitmapCache = new SparseArray<>();
            bitmapCache.put(R.drawable.question_mark, DecodeSampledBitmapFromResource.execute
                    (getResources(), R.drawable.question_mark, size, size));

            int j = 0;
            for (int i = 1; i <= 16; i++) {
                int imageId = getResources().getIdentifier("image" + i, "id", getPackageName());
                loadImage(size, j, imageId);
                j++;
            }
        }
    }

    private void loadImage(int size, int j, int imageId) {
        Bitmap bitmap = bitmapCache.get(itemsDTOs.get(j).getImageResource());
        if (bitmap == null) {
            bitmap = DecodeSampledBitmapFromResource.execute(getResources(),
                    itemsDTOs.get(j).getImageResource(), size, size);
            bitmapCache.put(itemsDTOs.get(j).getImageResource(), bitmap);
        }
        ImageView imageView = (ImageView) findViewById(imageId);
        imageView.setOnClickListener(this);
        imageView.setImageBitmap(bitmap);
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;
        imageView.setEnabled(false);
        imageView.setTag(itemsDTOs.get(j));
        imageView.setVisibility(View.VISIBLE);
        imageViews.add(imageView);
    }

    private void populateImage() {
        itemsDTOs = new ArrayList<>();
        int i = 0;
        while (i < totalCount / 2) {
            ItemsDTO itemsDTO = null;
            int choice = RandomNumber.randInt(1, 5);
            switch (choice) {
                case 1:
                    itemsDTO = DataStore.getInstance().getAlphabets().get(RandomNumber
                            .randInt(0, DataStore.getInstance().getAlphabets().size() - 1));
                    break;
                case 2:
                    itemsDTO = DataStore.getInstance().getAnimals().get(RandomNumber.randInt(0,
                            DataStore.getInstance().getAnimals().size() - 1));
                    break;
                case 3:
                    itemsDTO = DataStore.getInstance().getFruits().get(RandomNumber.randInt(0,
                            DataStore.getInstance().getFruits().size() - 1));
                    break;
                case 4:
                    itemsDTO = DataStore.getInstance().getVegetables().get(RandomNumber.randInt(0,
                            DataStore.getInstance().getVegetables().size() - 1));
                    break;
                case 5:
                    itemsDTO = DataStore.getInstance().getVehicles().get(RandomNumber.randInt(0,
                            DataStore.getInstance().getVehicles().size() - 1));
                    break;
            }
            if (itemsDTO != null) {
                itemsDTO.setShowBack(true);
                itemsDTOs.add(itemsDTO);
                try {
                    itemsDTOs.add((ItemsDTO) itemsDTO.clone());
                } catch (CloneNotSupportedException e) {
                    Log.e(getClass().getName(), e.getLocalizedMessage(), e);
                }
            }
            i++;
        }
        Collections.shuffle(this.itemsDTOs);
    }

    @Override
    public void onClick(final View v) {
        v.setEnabled(false);
        clickListener(v);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                views.add(v);
                if (views.size() == 2) {
                    ItemsDTO itemsDTO = (ItemsDTO) views.get(0).getTag();
                    ItemsDTO itemsDTO1 = (ItemsDTO) views.get(1).getTag();
                    if (!itemsDTO.getItemName().equalsIgnoreCase(itemsDTO1.getItemName())) {
                        v.clearAnimation();
                        v.setAnimation(animation1);
                        v.startAnimation(animation1);
                        v.setEnabled(true);
                        views.get(0).setEnabled(true);
                    } else {
                        views.clear();
                        currentCount++;
                        if (totalCount / 2 == currentCount) {
                            showPopUp = false;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentCount = 0;
                                    viewCount = 0;
                                    switch (totalCount) {
                                        case 4:
                                            totalCount = 6;
                                            break;
                                        case 6:
                                            totalCount = 8;
                                            break;
                                        case 8:
                                            totalCount = 12;
                                            break;
                                        case 12:
                                            totalCount = 16;
                                            if (getResources().getConfiguration().orientation ==
                                                    Configuration.ORIENTATION_LANDSCAPE) {
                                                showPopUp = true;
                                            }
                                            break;
                                        default:
                                            showPopUp = true;
                                    }
                                    if (!showPopUp) {
                                        populateImage();
                                        renderView();
                                        imageViews.get(0).setEnabled(true);
                                        clickListener(imageViews.get(0));
                                    } else {
                                        new AlertDialog.Builder(FindPairActivity.this)
                                                .setTitle("Well Done!!!")
                                                .setMessage("Do you want to play again?")
                                                .setPositiveButton(R.string.yes,
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(
                                                                    DialogInterface dialog,
                                                                    int which) {
                                                                dialog.dismiss();
                                                                totalCount = 4;
                                                                populateImage();
                                                                renderView();
                                                                imageViews.get(0).setEnabled(true);
                                                                clickListener(imageViews.get(0));
                                                            }
                                                        })
                                                .setNegativeButton(R.string.no,
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(
                                                                    DialogInterface dialog,
                                                                    int which) {
                                                                dialog.dismiss();
                                                                onBackPressed();
                                                            }
                                                        })
                                                .setIcon(R.drawable.ic_action_done)
                                                .show();
                                    }
                                }
                            }, 3000);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void clickListener(View v) {
        view = v;
        v.clearAnimation();
        v.setAnimation(animation1);
        v.startAnimation(animation1);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation1) {
            ItemsDTO itemsDTO = (ItemsDTO) view.getTag();
            if (itemsDTO.isShowBack()) {
                ((ImageView) view).setImageBitmap(bitmapCache.get(R.drawable.question_mark));
                itemsDTO.setShowBack(false);
            } else {
                ((ImageView) view).setImageBitmap(bitmapCache.get(itemsDTO.getImageResource()));
                itemsDTO.setShowBack(true);
            }
            view.clearAnimation();
            view.setAnimation(animation2);
            view.startAnimation(animation2);
        } else {
            viewCount++;
            if (totalCount > viewCount) {
                imageViews.get(viewCount).setEnabled(true);
                clickListener(imageViews.get(viewCount));
            } else if (views.size() == 2) {
                clickListener(views.get(0));
                views.clear();
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

}
