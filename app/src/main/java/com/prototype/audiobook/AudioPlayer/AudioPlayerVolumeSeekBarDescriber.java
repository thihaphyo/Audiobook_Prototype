package com.prototype.audiobook.AudioPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.SeekBar;

/**
 * Created by Z3uS on 1/26/2018.
 */

public class AudioPlayerVolumeSeekBarDescriber implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mp;
    private SeekBar VolumeBar;
    private Context context;

    public AudioPlayerVolumeSeekBarDescriber(Context context,MediaPlayer mp,SeekBar VolumeBar) {
        this.context = context;
        this.mp=mp;
        this.VolumeBar=VolumeBar;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (b) {
            AudioBridge.SetSystemVolume(context,progress);
            float volume=Float.parseFloat(String.valueOf(AudioBridge.GetVolume(context)));
            mp.setVolume(volume,volume);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
