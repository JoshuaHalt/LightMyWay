package com.example.joshua.lightmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class SettingsActivity extends Activity {
    CheckBox cbLightAlwaysOn;
    Float screenBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getIntent().hasExtra(Helper.EXTRA_SCREEN_BRIGHTNESS_LEVEL)) {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            screenBrightness = getIntent().getFloatExtra(Helper.EXTRA_SCREEN_BRIGHTNESS_LEVEL, 0);
            layout.screenBrightness = screenBrightness;
            getWindow().setAttributes(layout);
        }

        cbLightAlwaysOn = (CheckBox) findViewById(R.id.cbLightAlwaysOn);

        cbLightAlwaysOn.setOnCheckedChangeListener(cbLightAlwaysOnCheckedChanged);

        if (Helper.getPref(getApplicationContext(), Helper.LIGHT_ALWAYS_ON) == 1)
            cbLightAlwaysOn.setChecked(true);
    }

    CheckBox.OnCheckedChangeListener cbLightAlwaysOnCheckedChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                Helper.setPref(getApplicationContext(), Helper.LIGHT_ALWAYS_ON, 1);
            else
                Helper.setPref(getApplicationContext(), Helper.LIGHT_ALWAYS_ON, 0);
        }
    };

    @Override
    public void onBackPressed() {
        if (screenBrightness != null) {
            Intent intent = new Intent();
            intent.putExtra(Helper.EXTRA_SCREEN_BRIGHTNESS_LEVEL, screenBrightness);
            setResult(RESULT_OK, intent);
            finish();
        }

        super.onBackPressed();
    }
}