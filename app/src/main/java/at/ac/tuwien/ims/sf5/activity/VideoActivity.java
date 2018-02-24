package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import java.util.Locale;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.GameResourceManager;

/**
 * @Author Benedikt Fuchs
 */
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {

    VideoView view;
    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set to fullscreen:
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        GameResourceManager.getManager().loadResources(getResources(), this);
        Locale myLocale = new Locale(GameResourceManager.getManager().getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        super.onCreate(savedInstanceState);

        // Set layout:
        setContentView(R.layout.activity_video);

        // Initialize VideoView:
        view = (VideoView) findViewById(R.id.videoView);
        view.setOnCompletionListener(this);

        // Initialize "skip" button:
        skipButton = (Button) findViewById(R.id.skipVideoButton);

        // Set Video URI:
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + ("de".equals(GameResourceManager.getManager().getLocale()) ? R.raw.video : R.raw.video_en));
        view.setVideoURI(video);

        if (!GameResourceManager.getManager().isSkip()) {
            skipButton.setVisibility(View.INVISIBLE);
        } else {
            skipButton.setOnClickListener(this);
        }

        // Start the video:
        view.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // Is called when the video has finished
        skipVideo();
    }

    @Override
    public void onClick(View v) {
        // Is called when the skip button is clicked
        skipVideo();
    }

    private void skipVideo() {
        GameResourceManager.getManager().setSkip(true);
        GameResourceManager.getManager().unloadResources();
        activateSplashscreen();
        //startActivity(new Intent(this, MenuActivity.class));
        //finish();
    }

    private void activateSplashscreen() {
        startActivity(new Intent(this, SplashScreenWelcomeActivity.class));
        finish();
    }

}