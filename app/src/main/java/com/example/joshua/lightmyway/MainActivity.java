package com.example.joshua.lightmyway;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private Camera camera = null;
    private boolean isFlashOn = false;
    private Camera.Parameters params;

    private Button btnToggle;
    private TextView tvBatteryMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_my_way);
        setTitle("Light My Way");

        btnToggle = (Button) findViewById(R.id.btnToggle);
        tvBatteryMessage = (TextView) findViewById(R.id.tvBatteryMessage);

        btnToggle.setOnClickListener(toggleLight);
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

    View.OnClickListener settings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //open settings...
        }
    };

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
        off();
        releaseCamera();
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        off();
        releaseCamera();
        super.onPause();
    }
}