package com.prototype;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Z3uS on 1/28/2018.
 */

public class PrototypeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "AudioBookByPTH");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }else{
            Log.v("File","Already Exists");
        }
    }
}
