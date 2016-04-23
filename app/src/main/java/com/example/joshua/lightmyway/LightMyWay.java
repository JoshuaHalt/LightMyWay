package com.example.joshua.lightmyway;

import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class LightMyWay extends ActionBarActivity {
    private Camera camera = null;
    private boolean isFlashOn = false;
    private Camera.Parameters params;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_my_way);
        setTitle("Light My Way");
    }

    private void getCamera() {
        try {
            camera = Camera.open();
            params = camera.getParameters();
        } catch (RuntimeException e) {
            Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
        }
    }
    public void ToggleLight(View view)
    {
        getCamera();
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
        else {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    @Override
    public void onBackPressed()
    {
        camera.release();
        super.onBackPressed();
    }

    @Override
    public void onPause()
    {
        camera.release();
        super.onPause();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getCamera();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getCamera();
    }
}
