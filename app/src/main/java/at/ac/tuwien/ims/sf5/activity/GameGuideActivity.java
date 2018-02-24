package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import at.ac.tuwien.ims.sf5.R;
/**
 * @Author Iris Fiedler
 */
public class GameGuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout game_guide_layout;
    private ImageView app_name_image;
    private TextView game_guide_text;
    private TextView about_description;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guide);

        game_guide_layout = (ConstraintLayout) findViewById(R.id.game_guide_layout);
        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        game_guide_text = (TextView) findViewById(R.id.game_guide_text);
        about_description = (TextView) findViewById(R.id.about_description);
        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == back_button.getId()) {
            finish();
        }
    }
}
