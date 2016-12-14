package com.ashoksm.atozforkids;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.RandomNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pair);

        bitmapCache = new SparseArray<>();
        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);

        sharedPreferences = getSharedPreferences("com.ashoksm.atozforkids.ABCFlashCards",
                Context.MODE_PRIVATE);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i(getClass().getName(), "TextToSpeech onInit status::::::::" + status);
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
                } else {
                    Log.i(getClass().getName(),
                            "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

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

        if (sharedPreferences.getBoolean("sound", true)) {
            mediaPlayer = MediaPlayer.create(this, R.raw.applause);
        }
    }

    private void renderView() {
        imageViews = new ArrayList<>();
        int size;
        if (totalCount == 4) {
            if (isLargeScreenAndPortrait()) {
                size = 375;
            } else if (isLargeScreenAndLandscape()) {
                size = 275;
            } else {
                size = 400;
            }
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
            if (isLargeScreenAndPortrait()) {
                size = 325;
            } else if (isLargeScreenAndLandscape()) {
                size = 275;
            } else {
                size = 350;
            }
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
            if (isLargeScreenAndPortrait()) {
                size = 250;
            } else if (isLargeScreenAndLandscape()) {
                size = 225;
            } else {
                size = 300;
            }
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
            if (isLargeScreenAndPortrait()) {
                count = 16;
                size = 200;
            } else if (isLargeScreenAndLandscape()) {
                findViewById(R.id.row_3).setVisibility(View.VISIBLE);
                count = 12;
                size = 175;
            } else if (getResources().getConfiguration().orientation ==
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
            if (isLargeScreenAndPortrait()) {
                size = 175;
            } else {
                size = 200;
            }
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
            if (itemsDTOs.contains(itemsDTO)) {
                continue;
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
        if (views.size() != 2) {
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
                            speak("Try Again!!!");
                        } else {
                            speak(DataStore.getInstance().getStatusValues().get(RandomNumber
                                    .randInt(0, DataStore.getInstance().getStatusValues().size() - 1)));
                            views.clear();
                            currentCount++;
                            if (totalCount / 2 == currentCount) {
                                showPopUp = false;
                                if (mediaPlayer != null) {
                                    mediaPlayer.start();
                                }
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
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imageViews.get(0).setEnabled(true);
                                                    clickListener(imageViews.get(0));
                                                }
                                            }, 3000);
                                        } else {
                                            speak("Do you want to play again?");
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
                                                                    new Handler().postDelayed(
                                                                            new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    imageViews
                                                                                            .get(0)
                                                                                            .setEnabled(
                                                                                                    true);
                                                                                    clickListener(
                                                                                            imageViews
                                                                                                    .get(0));
                                                                                }
                                                                            }, 3000);
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

    private boolean isLargeScreenAndPortrait() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE &&
                getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean isLargeScreenAndLandscape() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE &&
                getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE;
    }

    private void speak(String s) {
        if (sharedPreferences.getBoolean("sound", true)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(s, TextToSpeech.QUEUE_ADD, null, s);
                } else {
                    textToSpeech.speak(s, TextToSpeech.QUEUE_ADD, null);
                }
            } catch (Exception e) {
                Toast.makeText(this, "No TTS Found!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
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
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
