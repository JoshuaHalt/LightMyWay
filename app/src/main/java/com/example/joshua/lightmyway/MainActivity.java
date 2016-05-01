package com.example.joshua.lightmyway;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private Camera camera = null;
    private boolean isFlashOn = false;
    private Camera.Parameters params;

    private Button btnSettings;
    private Button btnToggle;
    private TextView tvBatteryMessage;
    private SeekBar sbBrightness;

    private final Float SCREEN_BRIGHTNESS_MULTIPLIER = .392156862745098039215F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_my_way);
        setTitle("Light My Way");

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnToggle = (Button) findViewById(R.id.btnToggle);
        tvBatteryMessage = (TextView) findViewById(R.id.tvBatteryMessage);
        sbBrightness = (SeekBar) findViewById(R.id.sbBrightness);

        setSeekBarBrightness();

        btnSettings.setOnClickListener(settingsClick);
        btnToggle.setOnClickListener(toggleLight);
        sbBrightness.setOnSeekBarChangeListener(sbChanged);
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Toast.makeText(getApplicationContext(),
                        "Light error: " + e.getMessage() + ". Please let the developer know.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    View.OnClickListener toggleLight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCamera();

            if (isFlashOn)
                off();
            else
                on();
        }
    };

    View.OnClickListener settingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
    };

    SeekBar.OnSeekBarChangeListener sbChanged = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = (float) progress / 100;
            getWindow().setAttributes(layout);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void setSeekBarBrightness() {
        try {
            Integer screenBrightness = Settings.System.getInt(getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);

            sbBrightness.setProgress((int) (screenBrightness * SCREEN_BRIGHTNESS_MULTIPLIER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void on() {
        if (camera == null || params == null)
            return;

        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashOn = true;

        btnToggle.setText("Dark");
        tvBatteryMessage.setVisibility(View.VISIBLE);
    }

    private void off() {
        if (camera == null || params == null)
            return;

        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashOn = false;

        btnToggle.setText("Light");
        tvBatteryMessage.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (Helper.getPref(getApplicationContext(), Helper.LIGHT_ALWAYS_ON) != 1) {
            off();
            releaseCamera();
        }

        super.onBackPressed();
    }

    @Override
    public void onPause() {
        if (Helper.getPref(getApplicationContext(), Helper.LIGHT_ALWAYS_ON) != 1) {
            off();
            releaseCamera();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        setSeekBarBrightness();

        super.onResume();
    }
}