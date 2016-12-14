package com.ashoksm.atozforkids.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.ashoksm.atozforkids.R;

public class SettingsDialog extends Dialog implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SettingsDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_settings);

        sharedPreferences = context.getSharedPreferences("com.ashoksm.atozforkids.ABCFlashCards",
                Context.MODE_PRIVATE);

        if (getWindow() != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(params);
        }

        final Switch random = (Switch) findViewById(R.id.random_button);
        random.setChecked(sharedPreferences.getBoolean("random", false));
        random.setOnCheckedChangeListener(this);

        final Switch sound = (Switch) findViewById(R.id.sound_button);
        sound.setChecked(sharedPreferences.getBoolean("sound", true));
        sound.setOnCheckedChangeListener(this);

        final Switch autoPlay = (Switch) findViewById(R.id.auto_play_button);
        autoPlay.setChecked(sharedPreferences.getBoolean("auto_play", false));
        autoPlay.setOnCheckedChangeListener(this);

        Spinner interval = (Spinner) findViewById(R.id.interval);
        interval.setSelection(sharedPreferences.getInt("interval", 3) - 1);
        interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("interval", position + 1);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (buttonView.getId()) {
            case R.id.random_button:
                editor.putBoolean("random", isChecked);
                break;
            case R.id.sound_button:
                editor.putBoolean("sound", isChecked);
                break;
            case R.id.auto_play_button:
                editor.putBoolean("auto_play", isChecked);
                break;
        }
        editor.apply();
    }
}
