package com.ashoksm.atozforkids;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.RandomNumber;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DragAndDropActivity extends AppCompatActivity {


    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static {

    }

    private ImageView right;
    private Animation fadeInAnimation;
    private TextToSpeech textToSpeech;
    private int count = 0;
    private int currentCount = 0;
    private int randInt;
    private ItemsDTO itemsDTO;
    private ImageView mainBG;
    private Bitmap starBlue;
    private Bitmap starRed;
    private Bitmap starGreen;
    private Bitmap starYellow;
    private int itemCount;
    private int width;
    private int height;
    private MediaPlayer mediaPlayer;
    private InterstitialAd mInterstitialAd;
    private int adCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        adCount = 1;
        loadAd();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(0.8f);
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
                    Log.i("SliderActivity",
                            "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        mainBG = (ImageView) findViewById(R.id.main_bg);
        right = (ImageView) findViewById(R.id.right);
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE &&
                (getApplicationContext().getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) >=
                        Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            itemCount = 24;
        } else if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT &&
                (getApplicationContext().getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) >=
                        Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            itemCount = 20;
        } else if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE &&
                (getApplicationContext().getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) >=
                        Configuration.SCREENLAYOUT_SIZE_LARGE) {
            itemCount = 21;
        } else {
            itemCount = 16;
        }

        loadContent();

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                right.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
                if (count == currentCount) {
                    count = 0;
                    currentCount = 0;
                    String itemName = getIntent().getStringExtra(SliderActivity.EXTRA_ITEM_NAME);
                    if (itemName != null && itemName.trim().length() > 0) {
                        speak("Well Done!!! Do you want to play again?");
                        new AlertDialog.Builder(DragAndDropActivity.this)
                                .setTitle("Well Done!!!")
                                .setMessage("Do you want to play again?")
                                .setPositiveButton(R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                loadContent();
                                            }
                                        })
                                .setNegativeButton(R.string.no,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                onBackPressed();
                                            }
                                        })
                                .setIcon(R.drawable.ic_action_done)
                                .show();
                    } else {
                        loadContent();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        starBlue = DecodeSampledBitmapFromResource.execute(getResources(), R.drawable
                .ic_star_blue, 10, 10);
        starGreen = DecodeSampledBitmapFromResource.execute(getResources(), R.drawable
                .ic_star_green, 10, 10);
        starRed = DecodeSampledBitmapFromResource.execute(getResources(), R.drawable
                .ic_star_red, 10, 10);
        starYellow = DecodeSampledBitmapFromResource.execute(getResources(), R.drawable
                .star, 10, 10);

        mediaPlayer = MediaPlayer.create(this, R.raw.applause);
    }

    private void loadContent() {
        adCount++;

        if (adCount % 5 == 0) {
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        String itemName = getIntent().getStringExtra(SliderActivity.EXTRA_ITEM_NAME);
        if (itemName != null && itemName.trim().length() > 0) {
            itemsDTO = new ItemsDTO(itemName, getIntent().getIntExtra(SliderActivity
                    .EXTRA_ITEM_IMAGE_RESOURCE, 0));
        } else {
            while (true) {
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
                if (itemsDTO.getItemName().length() <= itemCount) {
                    break;
                }
            }
        }
        mainBG.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), itemsDTO
                .getImageResource(), width, height));
        speak(itemsDTO.getItemName());

        List<String> chars = new ArrayList<>();
        for (char c : itemsDTO.getItemName().toCharArray()) {
            chars.add(String.valueOf(c));
        }

        if (chars.size() < itemCount) {
            for (int i = chars.size() + 1; i <= itemCount; i++) {
                chars.add(String.valueOf(LETTERS.charAt(RandomNumber.randInt(0, 25))));
            }
        }
        Collections.shuffle(chars);
        count = itemsDTO.getItemName().length();

        for (int i = 1; i <= itemCount; i++) {
            int optionId = getResources().getIdentifier("option_" + i, "id", getPackageName());
            int choiceId = getResources().getIdentifier("choice_" + i, "id", getPackageName());
            TextView option = (TextView) findViewById(optionId);
            TextView choice = (TextView) findViewById(choiceId);
            option.setVisibility(View.VISIBLE);
            option.setOnTouchListener(new ChoiceTouchListener());
            option.setText(chars.get(i - 1).toUpperCase());
            if (itemsDTO.getItemName().length() >= i) {
                choice.setVisibility(View.VISIBLE);
                choice.setOnDragListener(new ChoiceDragListener());
                choice.setText(String.valueOf(itemsDTO.getItemName().charAt(i - 1)).toUpperCase());
                choice.setTextColor(Color.parseColor("#E5E4E2"));
                choice.setTypeface(Typeface.DEFAULT);
                choice.setTag(null);
            } else {
                choice.setVisibility(View.GONE);
            }
        }
    }

    private void speak(String s) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(s, TextToSpeech.QUEUE_ADD, null, s);
            } else {
                textToSpeech.speak(s, TextToSpeech.QUEUE_ADD, null);
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

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                /*
                 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(data, shadowBuilder, view, 0);
                } else {
                    view.startDrag(data, shadowBuilder, view, 0);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     */
    private class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    if (dropTarget.getText().equals(dropped.getText()) && dropTarget.getTag() ==
                            null) {
                        //stop displaying the view where it was before it was dragged
                        view.setVisibility(View.INVISIBLE);
                        //update the text in the target view to reflect the data being dropped
                        dropTarget.setText(dropped.getText());
                        //make it bold to highlight the fact that an item has been dropped
                        dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                        dropTarget.setTextColor(Color.parseColor("#AA00FF"));
                        //if an item has already been dropped here, there will be a tag
                        Object tag = dropTarget.getTag();
                        //if there is already an item here, set it back visible in its original place
                        if (tag != null) {
                            //the tag is the view id already dropped here
                            int existingID = (Integer) tag;
                            //set the original view visible again
                            findViewById(existingID).setVisibility(View.VISIBLE);
                        }
                        //set the tag in the target view being dropped on - to the ID of the view being dropped
                        dropTarget.setTag(dropped.getId());
                        right.startAnimation(fadeInAnimation);
                        currentCount++;

                        speak(dropped.getText().toString());
                        if (count == currentCount) {
                            speak(itemsDTO.getItemName());
                            while (true) {
                                int temp = RandomNumber.randInt(1, DataStore.getInstance()
                                        .getStatusValues().size() - 1);
                                if (temp != randInt) {
                                    randInt = temp;
                                    break;
                                }
                            }
                            mediaPlayer.start();
                            speak(DataStore.getInstance().getStatusValues().get(randInt));
                            new ParticleSystem(DragAndDropActivity.this, 100, starBlue, 3000)
                                    .setSpeedRange(0.1f, 0.5f).oneShot(mainBG, 100);
                            new ParticleSystem(DragAndDropActivity.this, 100, starGreen, 3000)
                                    .setSpeedRange(0.1f, 0.4f).oneShot(mainBG, 100);
                            new ParticleSystem(DragAndDropActivity.this, 100, starRed, 3000)
                                    .setSpeedRange(0.1f, 0.3f).oneShot(mainBG, 100);
                            new ParticleSystem(DragAndDropActivity.this, 100, starYellow, 3000)
                                    .setSpeedRange(0.1f, 0.2f).oneShot(mainBG, 100);
                        }
                    } else {
                        speak("Wrong choice, Try Again");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
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
                count = 0;
                currentCount = 0;
                loadContent();
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
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();
            }
        });
        return interstitialAd;
    }

    private void loadInterstitial() {
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left, 0);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }
}
