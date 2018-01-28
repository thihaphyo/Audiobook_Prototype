package com.prototype.audiobook.commons.Common_Audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import static android.content.Context.AUDIO_SERVICE;
import static com.prototype.audiobook.commons.Constants.Constants.VelocityMin;
import static com.prototype.audiobook.commons.Constants.Constants.filinfMin;

/**
 * Created by Z3uS on 1/25/2018.
 */

public abstract class AudioBookManager {

    protected  void Play_Pause_Action(MediaPlayer mp, ImageView imgView, Animation animation, Button btnPlay,int pic1,int pic2) {
        if(mp.isPlaying())
        {
            mp.pause();
            imgView.clearAnimation();
            btnPlay.setBackgroundResource(pic1);
        }else{
            mp.start();
            imgView.startAnimation(animation);
            btnPlay.setBackgroundResource(pic2);
        }
    }

    protected  boolean AudioGestureCalculation(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1)
    {
        boolean backward=false;
        boolean forward=false;
        boolean flag =false;

        float hdiff = motionEvent1.getX() - motionEvent.getX();
        float vdiff = motionEvent1.getY() - motionEvent.getY();

        float absHDiff = Math.abs(hdiff);
        float absVDiff = Math.abs(vdiff);

        float velocityx = Math.abs(v);
        float velocityy = Math.abs(v1);

        if (absHDiff > absVDiff && absHDiff > filinfMin && velocityx > VelocityMin) {
            if (hdiff > 0) {
                backward = true;
            } else {
                forward = true;
            }
        } else if (absVDiff > filinfMin && velocityy > VelocityMin) {
            if (vdiff > 0) {
                backward = true;
            } else {
                forward = true;
            }
        }
        flag = (backward) ? true : false;//if back ? true else false

        return flag;
    }

    protected  void fastForward(int position, MediaPlayer mp, SeekBar SongProgressBar)
    {
        if (position + 5000 <= mp.getDuration()) {
            mp.seekTo(position + 5000);
            SongProgressBar.setProgress(position + 5000);
        } else {
            mp.seekTo(mp.getDuration());
            SongProgressBar.setProgress(mp.getDuration());
        }
    }

    protected  void fastBackward(int position, MediaPlayer mp, SeekBar SongProgressBar)
    {
        if (position - 5000 <= mp.getDuration()) {
            mp.seekTo(position - 5000);
            SongProgressBar.setProgress(position - 5000);
        } else {
            mp.seekTo(mp.getDuration());
            SongProgressBar.setProgress(mp.getDuration());
        }
    }

    protected static   int GetSystemVolume(Context context)
    {
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        return am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    protected  static int GetSystemMaxVolume(Context context)
    {
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        return am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    protected  static  void SetSystemVolumeData(Context context,int progress)
    {
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
       am.  setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

    }

    protected  void SpeedControl(MediaPlayer mediaPlayer,int index,float speed,Button btnSpeedControl){

        btnSpeedControl.setText(String.valueOf(speed));
        // this checks on API 23 and up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
            } else {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                mediaPlayer.pause();
            }
        }
    }
}
