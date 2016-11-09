package com.ashoksm.atozforkids;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashoksm.atozforkids.utils.DataStore;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;
import com.ashoksm.atozforkids.utils.RandomNumber;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.plattysoft.leonids.ParticleSystem;

import java.util.Locale;
import java.util.Set;

public class LetsCountActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout row1;
    private LinearLayout row2;
    private LinearLayout row3;
    private LinearLayout row4;
    private LinearLayout row5;
    private LinearLayout row6;

    private FrameLayout frame1;
    private FrameLayout frame2;
    private FrameLayout frame3;
    private FrameLayout frame4;
    private FrameLayout frame5;
    private FrameLayout frame6;
    private FrameLayout frame7;
    private FrameLayout frame8;
    private FrameLayout frame9;
    private FrameLayout frame10;
    private FrameLayout frame11;
    private FrameLayout frame12;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;
    private ImageView image11;
    private ImageView image12;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;
    private TextView text10;
    private TextView text11;
    private TextView text12;

    private int position;
    private int count;
    private TextToSpeech textToSpeech;
    private int imgResource;

    private Bitmap star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_count);

        //load Ad
        loadAd();

        initComponents();
        position = 1;

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i(getClass().getName(), "TextToSpeech onInit status::::::::" + status);
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
                    Log.i(getClass().getName(),
                            "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        setRandomImage();
        populateGrid();

        star = DecodeSampledBitmapFromResource.execute(getResources(), R.drawable.star, 10, 10);
    }

    private void initComponents() {
        row1 = (LinearLayout) findViewById(R.id.row_1);
        row2 = (LinearLayout) findViewById(R.id.row_2);
        row3 = (LinearLayout) findViewById(R.id.row_3);
        row4 = (LinearLayout) findViewById(R.id.row_4);
        row5 = (LinearLayout) findViewById(R.id.row_5);
        row6 = (LinearLayout) findViewById(R.id.row_6);

        frame1 = (FrameLayout) findViewById(R.id.frame_1);
        frame2 = (FrameLayout) findViewById(R.id.frame_2);
        frame3 = (FrameLayout) findViewById(R.id.frame_3);
        frame4 = (FrameLayout) findViewById(R.id.frame_4);
        frame5 = (FrameLayout) findViewById(R.id.frame_5);
        frame6 = (FrameLayout) findViewById(R.id.frame_6);
        frame7 = (FrameLayout) findViewById(R.id.frame_7);
        frame8 = (FrameLayout) findViewById(R.id.frame_8);
        frame9 = (FrameLayout) findViewById(R.id.frame_9);
        frame10 = (FrameLayout) findViewById(R.id.frame_10);
        frame11 = (FrameLayout) findViewById(R.id.frame_11);
        frame12 = (FrameLayout) findViewById(R.id.frame_12);

        image1 = (ImageView) findViewById(R.id.image_1);
        image2 = (ImageView) findViewById(R.id.image_2);
        image3 = (ImageView) findViewById(R.id.image_3);
        image4 = (ImageView) findViewById(R.id.image_4);
        image5 = (ImageView) findViewById(R.id.image_5);
        image6 = (ImageView) findViewById(R.id.image_6);
        image7 = (ImageView) findViewById(R.id.image_7);
        image8 = (ImageView) findViewById(R.id.image_8);
        image9 = (ImageView) findViewById(R.id.image_9);
        image10 = (ImageView) findViewById(R.id.image_10);
        image11 = (ImageView) findViewById(R.id.image_11);
        image12 = (ImageView) findViewById(R.id.image_12);

        text1 = (TextView) findViewById(R.id.text_1);
        text2 = (TextView) findViewById(R.id.text_2);
        text3 = (TextView) findViewById(R.id.text_3);
        text4 = (TextView) findViewById(R.id.text_4);
        text5 = (TextView) findViewById(R.id.text_5);
        text6 = (TextView) findViewById(R.id.text_6);
        text7 = (TextView) findViewById(R.id.text_7);
        text8 = (TextView) findViewById(R.id.text_8);
        text9 = (TextView) findViewById(R.id.text_9);
        text10 = (TextView) findViewById(R.id.text_10);
        text11 = (TextView) findViewById(R.id.text_11);
        text12 = (TextView) findViewById(R.id.text_12);

        image1.setTag(text1);
        image2.setTag(text2);
        image3.setTag(text3);
        image4.setTag(text4);
        image5.setTag(text5);
        image6.setTag(text6);
        image7.setTag(text7);
        image8.setTag(text8);
        image9.setTag(text9);
        image10.setTag(text10);
        image11.setTag(text11);
        image12.setTag(text12);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);
        image9.setOnClickListener(this);
        image10.setOnClickListener(this);
        image11.setOnClickListener(this);
        image12.setOnClickListener(this);
    }

    private void setRandomImage() {
        int i = RandomNumber.randInt(0, 4);
        switch (i) {
            case 0:
                imgResource = DataStore.getInstance().getAlphabets().get(RandomNumber
                        .randInt(0, DataStore.getInstance().getAlphabets().size() - 1))
                        .getImageResource();
                break;
            case 1:
                imgResource = DataStore.getInstance().getFruits().get(RandomNumber
                        .randInt(0, DataStore.getInstance().getFruits().size() - 1))
                        .getImageResource();
                break;
            case 2:
                imgResource = DataStore.getInstance().getAnimals().get(RandomNumber
                        .randInt(0, DataStore.getInstance().getAnimals().size() - 1))
                        .getImageResource();
                break;
            case 3:
                imgResource = DataStore.getInstance().getVegetables().get(RandomNumber
                        .randInt(0, DataStore.getInstance().getVegetables().size() - 1))
                        .getImageResource();
                break;
            case 4:
                imgResource = DataStore.getInstance().getVehicles().get(RandomNumber
                        .randInt(0, DataStore.getInstance().getVehicles().size() - 1))
                        .getImageResource();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        view.setEnabled(false);
        TextView textView = (TextView) view.getTag();
        count++;
        textView.setVisibility(View.VISIBLE);
        textView.setText(String.valueOf(count));
        speak(null);
        if (count == position) {
            count = 0;
            position++;
            if (position > 10) {
                new ParticleSystem(LetsCountActivity.this, 200, star, 2000)
                        .setSpeedRange(0.1f, 0.2f).oneShot(row4, 200);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (position <= 10) {
                        populateGrid();
                    } else {
                        speak("Well Done!!! Do you want to play again?");
                        new AlertDialog.Builder(LetsCountActivity.this)
                                .setTitle("Well Done!!!")
                                .setMessage("Do you want to play again?")
                                .setPositiveButton(R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                position = 1;
                                                row1.setVisibility(View.GONE);
                                                row2.setVisibility(View.GONE);
                                                row3.setVisibility(View.GONE);
                                                row4.setVisibility(View.GONE);
                                                row5.setVisibility(View.GONE);
                                                row6.setVisibility(View.GONE);
                                                setRandomImage();
                                                populateGrid();
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
                    }
                }
            }, 2000);
        }
    }

    private void populateGrid() {
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);
        text5.setVisibility(View.GONE);
        text6.setVisibility(View.GONE);
        text7.setVisibility(View.GONE);
        text8.setVisibility(View.GONE);
        text9.setVisibility(View.GONE);
        text10.setVisibility(View.GONE);
        text11.setVisibility(View.GONE);
        text12.setVisibility(View.GONE);

        int size;
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT && !isLargeScreen()) {
            size = 2000;
            if (position == 1) {
                size = 700;
            } else if (position == 2) {
                size = 1400;
            }
        } else {
            size = 1000;
            if (position == 1) {
                size = 300;
            } else if (position == 2) {
                size = 600;
            }
        }
        switch (position) {
            case 1:
                one(size / position);
                break;
            case 2:
                two(size / position);
                break;
            case 3:
                three(size / position);
                break;
            case 4:
                four(size / position);
                break;
            case 5:
                five(size / position);
                break;
            case 6:
                six(size / position);
                break;
            case 7:
                seven(size / position);
                break;
            case 8:
                eight(size / position);
                break;
            case 9:
                nine(size / position);
                break;
            case 10:
                ten(size / position);
                break;
        }
    }

    private void speak(String s) {
        if (s == null) {
            s = String.valueOf(count);
        }
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

    private void one(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.GONE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.GONE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
    }

    private void two(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;

        text1.setText("");
        text3.setText("");
    }

    private void three(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image4.setImageBitmap(bitmap);
        image4.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;

        text1.setText("");
        text3.setText("");
        text4.setText("");
    }

    private void four(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.VISIBLE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.GONE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image4.setImageBitmap(bitmap);
        image4.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;

        text1.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
    }

    private void five(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.GONE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image2.setImageBitmap(bitmap);
        image2.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image6.setImageBitmap(bitmap);
        image6.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image2.getLayoutParams().height = size;
        image2.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image6.getLayoutParams().height = size;
        image6.getLayoutParams().width = size;

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text5.setText("");
        text6.setText("");
    }

    private void six(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.GONE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.VISIBLE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.GONE);
        frame7.setVisibility(View.VISIBLE);
        frame8.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image4.setImageBitmap(bitmap);
        image4.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image7.setImageBitmap(bitmap);
        image7.setEnabled(true);
        image8.setImageBitmap(bitmap);
        image8.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image7.getLayoutParams().height = size;
        image7.getLayoutParams().width = size;
        image8.getLayoutParams().height = size;
        image8.getLayoutParams().width = size;

        text1.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text7.setText("");
        text8.setText("");
    }

    private void seven(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.VISIBLE);
        row5.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.GONE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.VISIBLE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.GONE);
        frame7.setVisibility(View.VISIBLE);
        frame8.setVisibility(View.VISIBLE);
        frame9.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image4.setImageBitmap(bitmap);
        image4.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image7.setImageBitmap(bitmap);
        image7.setEnabled(true);
        image8.setImageBitmap(bitmap);
        image8.setEnabled(true);
        image9.setImageBitmap(bitmap);
        image9.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image7.getLayoutParams().height = size;
        image7.getLayoutParams().width = size;
        image8.getLayoutParams().height = size;
        image8.getLayoutParams().width = size;
        image9.getLayoutParams().height = size;
        image9.getLayoutParams().width = size;

        text1.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text7.setText("");
        text8.setText("");
        text9.setText("");
    }

    private void eight(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.VISIBLE);
        row5.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.GONE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.VISIBLE);
        frame7.setVisibility(View.VISIBLE);
        frame8.setVisibility(View.GONE);
        frame9.setVisibility(View.VISIBLE);
        frame10.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image2.setImageBitmap(bitmap);
        image2.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image6.setImageBitmap(bitmap);
        image6.setEnabled(true);
        image7.setImageBitmap(bitmap);
        image7.setEnabled(true);
        image9.setImageBitmap(bitmap);
        image9.setEnabled(true);
        image10.setImageBitmap(bitmap);
        image10.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image2.getLayoutParams().height = size;
        image2.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image6.getLayoutParams().height = size;
        image6.getLayoutParams().width = size;
        image7.getLayoutParams().height = size;
        image7.getLayoutParams().width = size;
        image9.getLayoutParams().height = size;
        image9.getLayoutParams().width = size;
        image10.getLayoutParams().height = size;
        image10.getLayoutParams().width = size;

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("");
        text9.setText("");
        text10.setText("");
    }

    private void nine(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.VISIBLE);
        row5.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.VISIBLE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.GONE);
        frame7.setVisibility(View.VISIBLE);
        frame8.setVisibility(View.VISIBLE);
        frame9.setVisibility(View.VISIBLE);
        frame10.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image2.setImageBitmap(bitmap);
        image2.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image4.setImageBitmap(bitmap);
        image4.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image7.setImageBitmap(bitmap);
        image7.setEnabled(true);
        image8.setImageBitmap(bitmap);
        image8.setEnabled(true);
        image9.setImageBitmap(bitmap);
        image9.setEnabled(true);
        image10.setImageBitmap(bitmap);
        image10.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image2.getLayoutParams().height = size;
        image2.getLayoutParams().width = size;
        image3.getLayoutParams().height = size;
        image3.getLayoutParams().width = size;
        image4.getLayoutParams().height = size;
        image4.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image7.getLayoutParams().height = size;
        image7.getLayoutParams().width = size;
        image9.getLayoutParams().height = size;
        image8.getLayoutParams().height = size;
        image8.getLayoutParams().width = size;
        image9.getLayoutParams().width = size;
        image10.getLayoutParams().height = size;
        image10.getLayoutParams().width = size;

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text7.setText("");
        text8.setText("");
        text9.setText("");
        text10.setText("");
    }

    private void ten(int size) {
        row1.setVisibility(View.VISIBLE);
        row2.setVisibility(View.VISIBLE);
        row3.setVisibility(View.VISIBLE);
        row4.setVisibility(View.VISIBLE);
        row5.setVisibility(View.VISIBLE);
        row6.setVisibility(View.VISIBLE);

        frame1.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        frame3.setVisibility(View.VISIBLE);
        frame4.setVisibility(View.GONE);
        frame5.setVisibility(View.VISIBLE);
        frame6.setVisibility(View.VISIBLE);
        frame7.setVisibility(View.VISIBLE);
        frame8.setVisibility(View.VISIBLE);
        frame9.setVisibility(View.VISIBLE);
        frame10.setVisibility(View.GONE);
        frame11.setVisibility(View.VISIBLE);
        frame12.setVisibility(View.VISIBLE);

        Bitmap bitmap = DecodeSampledBitmapFromResource
                .execute(getResources(), imgResource, size, size);

        image1.setImageBitmap(bitmap);
        image1.setEnabled(true);
        image2.setImageBitmap(bitmap);
        image2.setEnabled(true);
        image3.setImageBitmap(bitmap);
        image3.setEnabled(true);
        image5.setImageBitmap(bitmap);
        image5.setEnabled(true);
        image6.setImageBitmap(bitmap);
        image6.setEnabled(true);
        image7.setImageBitmap(bitmap);
        image7.setEnabled(true);
        image8.setImageBitmap(bitmap);
        image8.setEnabled(true);
        image9.setImageBitmap(bitmap);
        image9.setEnabled(true);
        image11.setImageBitmap(bitmap);
        image11.setEnabled(true);
        image12.setImageBitmap(bitmap);
        image12.setEnabled(true);

        image1.getLayoutParams().height = size;
        image1.getLayoutParams().width = size;
        image2.getLayoutParams().height = size;
        image2.getLayoutParams().width = size;
        image3.getLayoutParams().height = 350;
        image3.getLayoutParams().width = size;
        image5.getLayoutParams().height = size;
        image5.getLayoutParams().width = size;
        image6.getLayoutParams().height = size;
        image6.getLayoutParams().width = size;
        image7.getLayoutParams().height = size;
        image7.getLayoutParams().width = size;
        image8.getLayoutParams().height = size;
        image8.getLayoutParams().width = size;
        image9.getLayoutParams().height = size;
        image9.getLayoutParams().width = size;
        image11.getLayoutParams().height = size;
        image11.getLayoutParams().width = size;
        image12.getLayoutParams().height = size;
        image12.getLayoutParams().width = size;

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("");
        text8.setText("");
        text9.setText("");
        text11.setText("");
        text12.setText("");
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

    private boolean isLargeScreen() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration
                        .SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE ||
                getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE;
    }
}
