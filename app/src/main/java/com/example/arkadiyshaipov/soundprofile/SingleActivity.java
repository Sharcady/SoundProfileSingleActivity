package com.example.arkadiyshaipov.soundprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class SingleActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar alarm;
    SeekBar music;
    SeekBar ring;
    SeekBar system;
    SeekBar voice;
    AudioManager adMngr;
    SharedPreferences sPref;
    Button saveBut;
    Button partyBut;
    Button muteBut;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_VOLUME = "volume";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        adMngr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        alarm = (SeekBar) findViewById(R.id.alarmBar);
        music = (SeekBar) findViewById(R.id.musicBar);
        ring = (SeekBar) findViewById(R.id.ringBar);
        system = (SeekBar) findViewById(R.id.systBar);
        voice = (SeekBar) findViewById(R.id.voiceBar);

        initBar(alarm, AudioManager.STREAM_ALARM);
        initBar(music, AudioManager.STREAM_MUSIC);
        initBar(ring, AudioManager.STREAM_RING);
        initBar(system, AudioManager.STREAM_SYSTEM);
        initBar(voice, AudioManager.STREAM_VOICE_CALL);

        this.saveBut = (Button) findViewById(R.id.SaveBtn);
        this.saveBut.setOnClickListener(this);

        this.partyBut = (Button) findViewById(R.id.partyBut);
        this.partyBut.setOnClickListener(this);

        this.muteBut = (Button) findViewById(R.id.muteBut);
        this.muteBut.setOnClickListener(this);

        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void initBar (SeekBar bar, final int stream) {
        bar.setMax(adMngr.getStreamMaxVolume(stream));
        bar.setProgress(adMngr.getStreamVolume(stream));

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adMngr.setStreamVolume(stream, progress, AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.SaveBtn:
                saveAlarm();
                saveMusic();
                saveRing();
                saveSystem();
                saveVoice();
                break;
            case R.id.muteBut:
                muteAudio();
                saveAlarm();
                saveMusic();
                saveRing();
                saveSystem();
                saveVoice();
                break;
            case R.id.partyBut:
                partyAudio();
                saveAlarm();
                saveMusic();
                saveRing();
                saveSystem();
                saveVoice();
                break;
            default:
                break;
        }


    }

    public void muteAudio(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adMngr.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_RING, 0, 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_SYSTEM,0, 0);

        } else {
            adMngr.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            adMngr.setStreamMute(AudioManager.STREAM_ALARM, true);
            adMngr.setStreamMute(AudioManager.STREAM_MUSIC, true);
            adMngr.setStreamMute(AudioManager.STREAM_RING, true);
            adMngr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void partyAudio(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adMngr.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, adMngr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION), 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_ALARM, adMngr.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_MUSIC, adMngr.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_RING, adMngr.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
            adMngr.adjustStreamVolume(AudioManager.STREAM_SYSTEM, adMngr.getStreamMaxVolume(AudioManager.STREAM_SYSTEM), 0);
        } else {
            adMngr.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            adMngr.setStreamMute(AudioManager.STREAM_ALARM, false);
            adMngr.setStreamMute(AudioManager.STREAM_MUSIC, false);
            adMngr.setStreamMute(AudioManager.STREAM_RING, false);
            adMngr.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }

    //alternative method without solving API23 issue
    private void muteAll()
    {
        //adMngr.setStreamMute(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        /*adMngr.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        adMngr.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        adMngr.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        adMngr.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        adMngr.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);*/
    }
    //alternative method without solving API23 issue
    private void partyNoise()
    {
        adMngr.setStreamVolume(AudioManager.STREAM_ALARM, adMngr.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_PLAY_SOUND);
        adMngr.setStreamVolume(AudioManager.STREAM_MUSIC, adMngr.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
        adMngr.setStreamVolume(AudioManager.STREAM_RING, adMngr.getStreamMaxVolume(AudioManager.STREAM_RING), AudioManager.FLAG_PLAY_SOUND);
        adMngr.setStreamVolume(AudioManager.STREAM_SYSTEM, adMngr.getStreamMaxVolume(AudioManager.STREAM_SYSTEM), AudioManager.FLAG_PLAY_SOUND);
        adMngr.setStreamVolume(AudioManager.STREAM_VOICE_CALL, adMngr.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), AudioManager.FLAG_PLAY_SOUND);
    }
    private void saveAlarm()
    {
        this.sPref = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(APP_PREFERENCES_VOLUME,AudioManager.STREAM_ALARM);
        ed.commit();
    }
    private void saveMusic()
    {
        this.sPref = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(APP_PREFERENCES_VOLUME,AudioManager.STREAM_MUSIC);
        ed.commit();
    }
    private void saveRing()
    {
        this.sPref = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(APP_PREFERENCES_VOLUME,AudioManager.STREAM_RING);
        ed.commit();
    }
    private void saveSystem()
    {
        this.sPref = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(APP_PREFERENCES_VOLUME,AudioManager.STREAM_SYSTEM);
        ed.commit();
    }
    private void saveVoice()
    {
        this.sPref = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(APP_PREFERENCES_VOLUME,AudioManager.STREAM_VOICE_CALL);
        ed.commit();
        Toast.makeText(this, "Preferences activated", Toast.LENGTH_SHORT).show();
    }
}
