package com.prototype.audiobook.AudioPlayer;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prototype.audiobook.R;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements  AudioPlayerInterface {

    @BindView(R.id.positionBar)
    SeekBar SongProgressBar;
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.txtStart)
    TextView txtStart;
    @BindView(R.id.txtEnd)
    TextView txtEnd;
    @BindView(R.id.album)
    ImageView imgView;
    @BindView(R.id.VolumeBar)
    SeekBar VolumeBar;
    @BindView(R.id.musicPlayerToolBar)
    Toolbar musicPlayerToolbar;
    @BindView(R.id.btnSpeedControl)
    Button btnSpeedControl;
    @BindView(R.id.btnFastForward)
    Button btnFastForward;
    @BindView(R.id.btnFastBackward)
    Button btnFastBackward;

    private MediaPlayer mp;
    private Handler handler;
    private GestureDetectorCompat gestureDetector;
    private int ToalTime;
    public Animation animaton;
    private AudioBridge audioBridge;
    private float[] SpeedSettings = {1.0f,1.2f,1.4f,1.6f,1.8f,2.0f};
    private  int index=0;
    float volume_level= 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       init();
        SongProgressBar.setOnSeekBarChangeListener(new AudioPlayerSeekBarDescriber(mp,SongProgressBar));
        VolumeBar.setOnSeekBarChangeListener(new AudioPlayerVolumeSeekBarDescriber(this,mp,VolumeBar) );
        Thread thread = new Thread(new AudioPlayerThread());
        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int CurrentPosition = msg.what;
                SongProgressBar.setProgress(CurrentPosition);
                String strelasped = CreateFromTimeLabel(CurrentPosition);
                txtStart.setText(strelasped);
                String strRemain = CreateFromTimeLabel(ToalTime - CurrentPosition);
                txtEnd.setText("- " + strRemain);
            }
        };
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public String CreateFromTimeLabel(int currentPosition) {
        String res = "";
        int min = currentPosition / 1000 / 60;
        int sec = currentPosition / 1000 % 60;
        res = min + ":";
        if (sec < 10) {
            res += "0";
        }
        res += sec;
        return res;
    }

    @OnClick(R.id.btnPlay)
    public void onViewClicked() {
        Play_Pause_Action(mp);
    }

    @OnClick(R.id.btnSpeedControl)
    public void  onSpeedControlClicked(){SpeedControl(mp);}

    @OnClick(R.id.btnFastForward)
    public void  onFastForwardlClicked(){FastForward(mp);}

    @OnClick(R.id.btnFastBackward)
    public void onFastBackwardClicked(){FastBackward(mp);}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("Key",event.toString());
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            int index=AudioBridge.GetVolume(this);
            volume_level=Float.parseFloat(String.valueOf(AudioBridge.GetVolume(this)));
            mp.setVolume(volume_level,volume_level);
            VolumeBar.setProgress(index+1);
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            int index=AudioBridge.GetVolume(this);
            volume_level=Float.parseFloat(String.valueOf(AudioBridge.GetVolume(this)));
            mp.setVolume(volume_level,volume_level);
            VolumeBar.setProgress(index-1);
        }
        return false;
    }

    @Override
    public void Play_Pause_Action(MediaPlayer mp) {
         audioBridge = new AudioBridge(mp,imgView,animaton,btnPlay,R.drawable.ic_play_arrow_black_24px,R.drawable.ic_pause_black_24px);
    }

    @Override
    public void SpeedControl(MediaPlayer mediaPlayer)
    {
        index=index+1;
        float speed =(index >= SpeedSettings.length)  ? SpeedSettings[0] : SpeedSettings[index];
        index = (index >= SpeedSettings.length)  ? 0: index;
        AudioBridge speedBridge = new AudioBridge(mediaPlayer,index,speed,btnSpeedControl);
    }

    @Override
    public void FastForward(MediaPlayer mp)
    {
      AudioBridge fastforward = new AudioBridge(mp,mp.getCurrentPosition(),SongProgressBar,true);
    }

    @Override
     public void FastBackward(MediaPlayer mp)
    {
        AudioBridge fastbackward = new AudioBridge(mp,mp.getCurrentPosition(),SongProgressBar,false);
    }
    @Override
    public void init() {

        index=0;
        setContentView(R.layout.activity_audio_player);
        ButterKnife.bind(this);
        musicPlayerToolbar.setTitle("Audio Player");
        setSupportActionBar(musicPlayerToolbar);
        volume_level = Float.parseFloat(String.valueOf(AudioBridge.GetVolume(this)));
        VolumeBar.setMax(AudioBridge.GetMaxVolume(this));
        VolumeBar.setProgress(AudioBridge.GetVolume(this));
        mp = MediaPlayer.create(this, R.raw.myittartoke);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(volume_level,volume_level);
        ToalTime = mp.getDuration();
        animaton = AnimationUtils.loadAnimation(this, R.anim.rotate);
        gestureDetector = new GestureDetectorCompat(this, new AudioGestureDescriber(mp,SongProgressBar,imgView,animaton,btnPlay,R.drawable.ic_play_arrow_black_24px,R.drawable.ic_pause_black_24px));
        SongProgressBar.setMax(ToalTime);
    }
//======================Thread===========================================================================//
    private class AudioPlayerThread implements Runnable {
        @Override
        public void run() {
            while (mp != null) {
                try {
                    Message msg = new Message();
                    msg.what = mp.getCurrentPosition();
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                        Log.d("Error", Arrays.toString(ex.getStackTrace()));
                }
            }
        }
    }
}