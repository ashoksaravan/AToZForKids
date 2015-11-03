package com.ashoksm.atozforkids;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DataStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DragAndDropActivity extends AppCompatActivity {

    private ImageView right;
    private Animation fadeInAnimation;
    private TextToSpeech textToSpeech;
    private static List<String> statusValues = new ArrayList<>();
    private int count = 0;
    private int currentCount = 0;
    private int randInt;

    static {
        statusValues.add("Well Done!!!");
        statusValues.add("Great Job!!!");
        statusValues.add("Excellent!!!");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("SliderActivity", "TextToSpeech onInit status::::::::" + status);
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setPitch(0.8f);
                } else {
                    Log.i("SliderActivity", "TextToSpeech onInit failed with status::::::::" + status);
                }
            }
        });

        right = (ImageView) findViewById(R.id.right);
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        final ImageView content = (ImageView) findViewById(R.id.content_image);

        loadContent(content);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                right.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
                if(count == currentCount) {
                    count = 0;
                    currentCount = 0;
                    loadContent(content);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void loadContent(ImageView content) {
        ItemsDTO itemsDTO;
        while(true) {
            List<ItemsDTO> itemsDTOs = null;
            int choice = randInt(1, 4);
            switch (choice) {
                case 1 :
                    itemsDTOs = DataStore.getInstance().getAlphabets();
                    break;
                case 2 :
                    itemsDTOs = DataStore.getInstance().getAnimals();
                    break;
                case 3 :
                    itemsDTOs = DataStore.getInstance().getFruits();
                    break;
                case 4 :
                    itemsDTOs = DataStore.getInstance().getVegetables();
                    break;
            }
            if (itemsDTOs != null) {
                itemsDTO = itemsDTOs.get(randInt(0, itemsDTOs.size() - 1));
                if(itemsDTO.getItemName().length() <= 10) {
                    break;
                }
            }
        }

        content.setImageResource(itemsDTO.getImageResource());
        List<String> chars = new ArrayList<>();
        for (char c : itemsDTO.getItemName().toCharArray()){
            chars.add(String.valueOf(c));
        }
        Collections.shuffle(chars);
        count = itemsDTO.getItemName().length();

        for (int i = 1; i <= 10; i++) {
            int optionId = getResources().getIdentifier("option_" + i, "id", getPackageName());
            int choiceId = getResources().getIdentifier("choice_" + i, "id", getPackageName());
            TextView option = (TextView) findViewById(optionId);
            TextView choice = (TextView) findViewById(choiceId);
            if (itemsDTO.getItemName().length() >= i) {
                option.setVisibility(View.VISIBLE);
                choice.setVisibility(View.VISIBLE);
                option.setOnTouchListener(new ChoiceTouchListener());
                choice.setOnDragListener(new ChoiceDragListener());
                option.setText(chars.get(i - 1).toUpperCase());
                choice.setText(String.valueOf(itemsDTO.getItemName().charAt(i - 1)).toUpperCase());
                choice.setTextColor(Color.parseColor("#E5E4E2"));
                choice.setTag(null);
            } else {
                option.setVisibility(View.GONE);
                choice.setVisibility(View.GONE);
            }
        }
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
                view.startDrag(data, shadowBuilder, view, 0);
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
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    if (dropTarget.getText().equals(dropped.getText())) {
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
                        while (true) {
                            int temp = randInt(1, 3) - 1;
                            if(temp != randInt) {
                                randInt = temp;
                                break;
                            }
                        }
                        String s = statusValues.get(randInt);
                        speak(s);
                    } else {
                        speak("Try Again");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
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

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
