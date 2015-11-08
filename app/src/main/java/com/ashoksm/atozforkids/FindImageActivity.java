package com.ashoksm.atozforkids;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
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

public class FindImageActivity extends AppCompatActivity {

    private static List<String> statusValues = new ArrayList<>();

    static {
        statusValues.add("Well Done!!!");
        statusValues.add("Great Job!!!");
        statusValues.add("Excellent!!!");
        statusValues.add("Marvellous!!!");
        statusValues.add("Bravo!!!");
    }

    private ImageView right;
    private Animation fadeInAnimation;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image11;
    private ImageView image21;
    private ImageView image31;
    private ImageView image41;
    private ItemsDTO itemsDTO;
    private TextToSpeech textToSpeech;
    private View view;
    private int randInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loadAd();

        view = findViewById(R.id.find_image_main_view);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image11 = (ImageView) findViewById(R.id.image11);
        image21 = (ImageView) findViewById(R.id.image21);
        image31 = (ImageView) findViewById(R.id.image31);
        image41 = (ImageView) findViewById(R.id.image41);
        right = (ImageView) findViewById(R.id.right);
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        loadImages(false);

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
                    speak("Click " + itemsDTO.getItemName());
                } else {
                    Log.i("SliderActivity", "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                right.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
                unbindDrawables(view);
                System.gc();
                loadImages(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().toString().equalsIgnoreCase(itemsDTO.getItemName())) {
                    while (true) {
                        int temp = RandomNumber.randInt(1, statusValues.size() - 1);
                        if (temp != randInt) {
                            randInt = temp;
                            break;
                        }
                    }
                    speak(statusValues.get(randInt));
                    right.setVisibility(View.VISIBLE);
                    right.startAnimation(fadeInAnimation);
                } else {
                    speak("Try Again");
                    image11.setVisibility(View.VISIBLE);
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().toString().equalsIgnoreCase(itemsDTO.getItemName())) {
                    while (true) {
                        int temp = RandomNumber.randInt(1, statusValues.size() - 1);
                        if (temp != randInt) {
                            randInt = temp;
                            break;
                        }
                    }
                    speak(statusValues.get(randInt));
                    right.setVisibility(View.VISIBLE);
                    right.startAnimation(fadeInAnimation);
                } else {
                    speak("Try Again");
                    image21.setVisibility(View.VISIBLE);
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().toString().equalsIgnoreCase(itemsDTO.getItemName())) {
                    while (true) {
                        int temp = RandomNumber.randInt(1, statusValues.size() - 1);
                        if (temp != randInt) {
                            randInt = temp;
                            break;
                        }
                    }
                    speak(statusValues.get(randInt));
                    right.setVisibility(View.VISIBLE);
                    right.startAnimation(fadeInAnimation);
                } else {
                    speak("Try Again");
                    image31.setVisibility(View.VISIBLE);
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().toString().equalsIgnoreCase(itemsDTO.getItemName())) {
                    while (true) {
                        int temp = RandomNumber.randInt(1, statusValues.size() - 1);
                        if (temp != randInt) {
                            randInt = temp;
                            break;
                        }
                    }
                    speak(statusValues.get(randInt));
                    right.setVisibility(View.VISIBLE);
                    right.startAnimation(fadeInAnimation);
                } else {
                    speak("Try Again");
                    image41.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadImages(boolean speak) {
        List<ItemsDTO> items = new ArrayList<>();
        items.add(DataStore.getInstance().getAlphabets().get(RandomNumber.randInt(0, DataStore.getInstance()
                .getAlphabets().size() - 1)));
        items.add(DataStore.getInstance().getAnimals().get(RandomNumber.randInt(0, DataStore.getInstance()
                .getAnimals().size() - 1)));
        items.add(DataStore.getInstance().getFruits().get(RandomNumber.randInt(0, DataStore.getInstance()
                .getFruits().size() - 1)));
        items.add(DataStore.getInstance().getVegetables().get(RandomNumber.randInt(0, DataStore.getInstance()
                .getVegetables().size() - 1)));

        Collections.shuffle(items);
        itemsDTO = items.get(RandomNumber.randInt(0, 3));

        if (speak) {
            speak("Click " + itemsDTO.getItemName());
        }

        image1.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), items.get(0).getImageResource(), 200,
                        200));
        image1.setTag(items.get(0).getItemName());
        image2.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), items.get(1).getImageResource(),
                200,
                200));
        image2.setTag(items.get(1).getItemName());
        image3.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), items.get(2).getImageResource(),
                200,
                200));
        image3.setTag(items.get(2).getItemName());
        image4.setImageBitmap(DecodeSampledBitmapFromResource.execute(getResources(), items.get(3).getImageResource(),
                200,
                200));
        image4.setTag(items.get(3).getItemName());

        image11.setVisibility(View.GONE);
        image21.setVisibility(View.GONE);
        image31.setVisibility(View.GONE);
        image41.setVisibility(View.GONE);
    }

    private void speak(String s) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, s);
            } else {
                textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No TTS Found!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
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
}
