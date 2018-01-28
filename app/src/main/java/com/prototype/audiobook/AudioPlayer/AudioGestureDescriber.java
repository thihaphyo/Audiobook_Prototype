package com.prototype.audiobook.AudioPlayer;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.prototype.audiobook.commons.Common_Audio.AudioBookManager;

/**
 * Created by Z3uS on 1/25/2018.
 */

public class AudioGestureDescriber extends AudioBookManager implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
    private MediaPlayer mp;
    private SeekBar SongProgressBar;
    private int position;
    private ImageView imgView;
    private  Animation animation;
    private Button btnPlay;
    int pic1,pic2;

    public AudioGestureDescriber(MediaPlayer mp, SeekBar SongProgressBar,ImageView imgView, Animation animation, Button btnPlay, int pic1, int pic2) {
        this.mp = mp;
        this.SongProgressBar = SongProgressBar;
        this.imgView = imgView;
        this.animation = animation;
        this.btnPlay = btnPlay;
        this.pic1 = pic1;//play
        this.pic2 = pic2;//pause
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            boolean flag = AudioGestureCalculation(motionEvent,motionEvent1,v,v1);
            if (flag )//fast forward
            {
                position = mp.getCurrentPosition();
                fastForward(position,mp,SongProgressBar);

            } else //fast backward
            {
                position = mp.getCurrentPosition();
                fastBackward(position,mp,SongProgressBar);
            }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        AudioBridge audioBridge = new AudioBridge(mp,imgView,animation,btnPlay,pic1,pic2);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}
