package com.prototype.audiobook.AudioPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.prototype.audiobook.commons.Common_Audio.AudioBookManager;

/**
 * Created by Z3uS on 1/25/2018.
 */

public class AudioBridge extends AudioBookManager {

    private MediaPlayer mp;
    private ImageView imgView;
    private Animation animation;
    private Button btnPlay;
    private int pic1,index,position;
    private int pic2;
   private float SpeedSettings;

    public AudioBridge(MediaPlayer mp, ImageView imgView, Animation animation, Button btnPlay, int pic1, int pic2) {
        this.mp = mp;
        this.imgView = imgView;
        this.animation = animation;
        this.btnPlay = btnPlay;
        this.pic1 = pic1;
        this.pic2 = pic2;

        Play_Pause_Action(this.mp,this.imgView,this.animation,this.btnPlay, this.pic1,this.pic2 );
    }
    public AudioBridge(MediaPlayer mp,int index,float SpeedSettings,Button btnSpeedControl)
    {
        this.mp=mp;
        this.index = index;
        this.SpeedSettings = SpeedSettings;
        this.btnPlay = btnSpeedControl;

        SpeedControl(this.mp,this.index,this.SpeedSettings,this.btnPlay);
    }

     public AudioBridge(MediaPlayer mp, int position, SeekBar SongProgressBar,Boolean flag)
     {

         if(flag)
         {
             fastForward(position,mp,SongProgressBar);
         }else{
             fastBackward(position,mp,SongProgressBar);
         }
     }

    public static int  GetVolume(Context context)
    {
        return GetSystemVolume(context);
    }

    public static int GetMaxVolume(Context context) {

        return GetSystemMaxVolume(context);
    }

    public static  void SetSystemVolume(Context context,int progress)
    {
        SetSystemVolumeData(context,progress);
    }



}
