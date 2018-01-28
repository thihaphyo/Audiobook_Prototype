package com.prototype.audiobook.AudioPlayer;

import android.media.MediaPlayer;
import android.widget.SeekBar;

/**
 * Created by Z3uS on 1/26/2018.
 */

public class AudioPlayerSeekBarDescriber implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mp;
    private  SeekBar SongProgressBar;

    public AudioPlayerSeekBarDescriber(MediaPlayer mp, SeekBar SongProgressBar) {
        this.mp = mp;
        this.SongProgressBar = SongProgressBar;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (b) {
            mp.seekTo(progress);
            SongProgressBar.setProgress(progress);
        }
    }
    @Override public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override public void onStopTrackingTouch(SeekBar seekBar) {}
}
