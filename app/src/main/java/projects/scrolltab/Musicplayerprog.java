package projects.scrolltab;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Musicplayerprog extends AppCompatActivity {
    Button start;
    VideoView mVideoView;
    Button stop;
    Button pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        stop= (Button) findViewById(R.id.stbutton);
        mVideoView = (VideoView) findViewById(R.id.videoViewm);
        start = (Button) findViewById(R.id.plbutton);
        pause= (Button) findViewById(R.id.paubutton);
        mVideoView.setVideoURI(Uri.parse("http://airworldservice.org/talks/centers-of%20-excellence/National-Brain-Research-Centre-SC-Ratnadeep-Banerji%20.mp3"));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mVideoView.isPlaying())
                {
                    mVideoView.resume();
                }
                else


                    mVideoView.start();

            }
        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mVideoView.stopPlayback();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
            }
        });
    }





}
