package com.prototype.audiobook.AudioPlayer;

import android.media.MediaPlayer;

/**
 * Created by Z3uS on 1/25/2018.
 */

public interface AudioPlayerInterface  {

    void Play_Pause_Action(MediaPlayer mediaPlayer);

    void SpeedControl(MediaPlayer mediaPlayer);

    void FastForward(MediaPlayer mediaPlayer);

    void FastBackward(MediaPlayer mediaPlayer);

    void init();

    String CreateFromTimeLabel(int currentPosition);

}
