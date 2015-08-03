package com.sahilmutneja.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button mPlayButton;
    private Button mPauseButton;
    private Button mFastForwardButton;
    private Button mRewindButton;
    private Button mCompletedButton;

    private SeekBar seekBar;

    private MediaPlayer mediaPlayer;

    private MusicHandler musicHandler = new MusicHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.sath);
        seekBar = (SeekBar) findViewById(R.id.seekbar);

        seekBar.setMax(mediaPlayer.getDuration());

        mPlayButton = (Button) findViewById(R.id.play);
        mPauseButton = (Button) findViewById(R.id.pause);

        mFastForwardButton = (Button) findViewById(R.id.fast_forward);
        mRewindButton = (Button) findViewById(R.id.rewind);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Play is clicked", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                musicHandler.sendEmptyMessage(MESSAGE_WAKE_UP_AND_SEEK);

            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Pause is clicked", Toast.LENGTH_SHORT).show();
                musicHandler.removeMessages(MESSAGE_WAKE_UP_AND_SEEK);
                mediaPlayer.pause();

            }
        });

        mFastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Fast Forward button is clicked", Toast.LENGTH_SHORT).show();
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 2000);

            }
        });

        mRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Rewind Button is clicked", Toast.LENGTH_SHORT).show();
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 2000);
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "Song is Completed/Completion Listener", Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mediaPlayer != null && fromUser) {
//                    int newPosition = (int)(((float)progress/100) * mediaPlayer.getDuration());
//                    mediaPlayer.seekTo(newPosition);
                    mediaPlayer.seekTo(progress);
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int MESSAGE_WAKE_UP_AND_SEEK = 10;

    class MusicHandler extends android.os.Handler {
        //lets u schedule stuff on thread, handles messages on a thread
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_WAKE_UP_AND_SEEK) {
                if(mediaPlayer!=null){
                    if(mediaPlayer.isPlaying()){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        sendEmptyMessageDelayed(MESSAGE_WAKE_UP_AND_SEEK, 200);
                    }
                }
            }
        }

    }
}
