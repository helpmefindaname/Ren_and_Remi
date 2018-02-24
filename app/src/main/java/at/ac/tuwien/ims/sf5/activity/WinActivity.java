package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.HighScoreManager;

/**
 * @Author Iris Fiedler
 */
public class WinActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout win_layout;
    private ImageView app_name_image;
    private ImageView winner_alien_image;
    private TextView win_text;
    private Button next_button;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        win_layout = (ConstraintLayout) findViewById(R.id.win_layout);
        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        winner_alien_image = (ImageView) findViewById(R.id.winner_alien_image);
        win_text = (TextView) findViewById(R.id.win_text);
        next_button = (Button) findViewById(R.id.next_button);

        next_button.setOnClickListener(this);

        Bundle data = getIntent().getExtras();

        if (data == null) {
            data = new Bundle();
        }

        score = data.getInt("score", -1);
    }

    @Override
    public void onClick(View view) {

        Bundle bundle = new Bundle();
        bundle.putInt("score", score);

        if (view.getId() == next_button.getId()) {

            if (HighScoreManager.getManager().isNewHighScore(this, score)) {
                runActivity(NewHighscoreActivity.class, bundle);
            } else {
                runActivity(NewGameActivity.class, bundle);
            }
        }
    }

    private void runActivity(Class<?> c, Bundle bundle) {
        Intent intent;
        intent = new Intent(this, c);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
