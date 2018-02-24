package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import at.ac.tuwien.ims.sf5.R;

public class WinSplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up_animation);
        TextView textView = (TextView) findViewById(R.id.textView2);

        textView.startAnimation(scaleUp);
        Handler handler = new Handler();

        Bundle data = getIntent().getExtras();

        if (data == null) {
            data = new Bundle();
        }

        int score = data.getInt("score", -1);

        handler.postDelayed(() -> {
            Intent i = new Intent(this, WinActivity.class);
            i.putExtra("score", score);
            startActivity(i);
            finish();
        }, 2000);
    }
}
