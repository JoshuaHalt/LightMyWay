package com.example.joshua.lightmyway;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class SettingsActivity extends Activity {
    CheckBox cbLightAlwaysOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
}