package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import at.ac.tuwien.ims.sf5.R;

public class SplashScreenWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_welcome);

        Animation handShake = AnimationUtils.loadAnimation(this, R.anim.shake_hand_animation);
        ImageView handView = (ImageView) findViewById(R.id.handView);

        handView.startAnimation(handShake);
        Handler handler = new Handler();

        handler.postDelayed(() -> {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }, 3000);
    }
}
