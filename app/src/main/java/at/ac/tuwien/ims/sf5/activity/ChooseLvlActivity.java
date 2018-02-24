package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import at.ac.tuwien.ims.sf5.Gameactivity;
import at.ac.tuwien.ims.sf5.R;

/**
 * @Author Iris Fiedler
 */
public class ChooseLvlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button lvl_1_button, lvl_2_button;
    private Button back_button, start_game_button;

    private int level;
    private boolean allowLaser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_lvl);
        allowLaser = false;

        ImageView app_name_image = (ImageView) findViewById(R.id.app_name_image);
        lvl_1_button = (Button) findViewById(R.id.lvl_1_button);
        lvl_2_button = (Button) findViewById(R.id.lvl_2_button);
        back_button = (Button) findViewById(R.id.back_button);
        start_game_button = (Button) findViewById(R.id.start_game_button);
        ConstraintLayout choose_lvl_layout = (ConstraintLayout) findViewById(R.id.choose_lvl_layout);

        lvl_1_button.setOnClickListener(this);
        lvl_2_button.setOnClickListener(this);
        back_button.setOnClickListener(this);
        start_game_button.setOnClickListener(this);

        // chosen lvl by default = lvl_1
        lvl_1_button.setBackground(getDrawable(R.drawable.simple_orange_button));
        level = 1;
    }

    /*
           If lvl_1_button or lvl_2_button was clicked on, it's background changes from green
           (simple_button) to orange (simple_orange_button).

           It's not possible to choose both lvl-buttons, only one can have the orange background.
           So in case another lvl-button was clicked, the old one get's back it's green background,
           and the new one becomes orange.
    */
    @Override
    public void onClick(View view) {
        Intent intent;

        // if back_button was clicked, return to menu_activity
        if (view.getId() == back_button.getId()) {
            finish();
        }
        // if lvl_1_button or lvl_2_button was clicked, background changes to orange.
        // if other one was clicked on, old clicked one changes back to green, new one
        // becomes orange
        // chooses lvl in program
        if (view.getId() == lvl_1_button.getId() || view.getId() == lvl_2_button.getId()) {
            switch (view.getId()) {
                case R.id.lvl_1_button:
                    lvl_2_button.setBackground(getDrawable(R.drawable.simple_button));
                    lvl_1_button.setBackground(getDrawable(R.drawable.simple_orange_button));
                    allowLaser = false;
                    level = 1;
                    break;
                case R.id.lvl_2_button:
                    lvl_1_button.setBackground(getDrawable(R.drawable.simple_button));
                    lvl_2_button.setBackground(getDrawable(R.drawable.simple_orange_button));
                    allowLaser = true;
                    level = 2;
                    break;
            }
        }

        if (view.getId() == start_game_button.getId()) {
            intent = new Intent(this, Gameactivity.class);
            intent.putExtra("spawnReflector", allowLaser);
            intent.putExtra("player1Ai", false);
            intent.putExtra("player2Ai", true);
            intent.putExtra("level", level);
            startActivity(intent);
            finish();
        }

    }
}
