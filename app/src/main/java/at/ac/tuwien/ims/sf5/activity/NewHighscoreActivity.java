package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.HighScoreManager;

public class NewHighscoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout new_highscore_layout;
    private ImageView app_name_image;
    private TextView new_highscore_text;
    private EditText editText;
    private Button next_button;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_highscore);

        new_highscore_layout = (ConstraintLayout) findViewById(R.id.new_highscore_layout);
        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        new_highscore_text = (TextView) findViewById(R.id.new_highscore_text);
        editText = (EditText) findViewById(R.id.editText);
        next_button = (Button) findViewById(R.id.next_button);

        next_button.setOnClickListener(this);

        score = getIntent().getIntExtra("score", 0);
    }

    /*
    TODO Providing keyboard
     */

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == next_button.getId()) {
            intent = new Intent(this, NewGameActivity.class);
            HighScoreManager.getManager().putScore(this, editText.getText().toString(), score);
            startActivity(intent);
            finish();
        }
    }
}
