package com.example.mysangeet;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    ImageButton play_pause_btn,previous_btn,next_btn;
    ImageView song_image;
    TextView song_name,played_time,total_time;
    int song_position;
    SeekBar song_seek_bar;
    MediaPlayer mediaPlayer;
    ArrayList<File> songs;
    Uri uri;
    String currentSong;
    Intent intent;
    private Handler handler = new Handler();
    private  Runnable runnable;
    ImageView gif_anim;

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        onStopCall();
        mediaPlayer.start();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        play_pause_btn = findViewById(R.id.play_pause_btn);
        previous_btn = findViewById(R.id.previous_btn);
        next_btn = findViewById(R.id.next_btn);
        song_image = findViewById(R.id.song_img);
        song_name = findViewById(R.id.song_name);
        played_time = findViewById(R.id.played_time);
        total_time = findViewById(R.id.total_time);
        song_seek_bar = findViewById(R.id.song_seek_bar);

        gif_anim = findViewById(R.id.gif_anim);
        Glide.with(PlayerActivity.this).asGif().load(R.drawable.colorful_waves).into(gif_anim);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("songList");
        song_position = intent.getIntExtra("position",0);
        currentSong = intent.getStringExtra("currentSong");
        song_name.setText(currentSong);
        song_name.setSelected(true);
//        onStopCall();
        starting();
        int duration = mediaPlayer.getDuration();
        total_time.setText(formattedTime(duration));
            song_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mediaPlayer != null && fromUser){
                        played_time.setText(formattedTime(seekBar.getProgress()));
//                        mediaPlayer.seekTo(seekBar.getProgress());
//                        setCurrentTime();
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar){
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
            runnable = () -> {
                if(mediaPlayer != null){
                    song_seek_bar.setProgress(mediaPlayer.getCurrentPosition());
                   setCurrentTime();
                }
                handler.postDelayed(runnable,1000);
            };
            handler.postDelayed(runnable,1000);
        }
        /*public void onStopCall(){
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }*/
        public void setCurrentTime(){
            int currentPosition = mediaPlayer.getCurrentPosition();
            played_time.setText(formattedTime(currentPosition));
        }

    public void starting(){
            uri = Uri.parse(songs.get(song_position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            song_seek_bar.setProgress(0);
            song_seek_bar.setMax(mediaPlayer.getDuration());
            play_pause_btn.setImageResource(R.drawable.pause);
        }
    public void next(View view){
        mediaPlayer.stop();
        mediaPlayer.release();
        if(song_position != songs.size()-1){
            song_position = song_position + 1;
        }else{
            song_position = 0;
        }
        starting();
        int duration = mediaPlayer.getDuration();
        total_time.setText(formattedTime(duration));
        currentSong = songs.get(song_position).getName().toString();
        song_name.setText(currentSong);
    }
    public void previous(View view){
        mediaPlayer.stop();
        mediaPlayer.release();
        if(song_position != 0){
            song_position = song_position - 1;
        }else{
            song_position = songs.size()-1;
        }
        starting();
        int duration = mediaPlayer.getDuration();
        total_time.setText(formattedTime(duration));
        currentSong = songs.get(song_position).getName().toString();
        song_name.setText(currentSong);
    }
    public void play_pause(View view){
        if(mediaPlayer.isPlaying()){
            // here are my silly mistake from old app  i do this  [mediaPlayer.stop();] but i right thing is :- [mediaPlayer.pause();]
            mediaPlayer.pause();
            play_pause_btn.setImageResource(R.drawable.play);
            song_seek_bar.setProgress(mediaPlayer.getCurrentPosition());
        }else{
            mediaPlayer.start();
            play_pause_btn.setImageResource(R.drawable.pause);
            song_seek_bar.setProgress(mediaPlayer.getCurrentPosition());
        }
    }
    public String formattedTime(int dura){
        String calTotalTime = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(dura),
                TimeUnit.MILLISECONDS.toSeconds(dura)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dura)));
        return calTotalTime;
    }
}