package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import at.ac.tuwien.ims.sf5.R;

/**
 * @Author Iris Fiedler
 */

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView app_name_image;
    private Button new_game_button;
    private Button menu_button;
    private ConstraintLayout new_game_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        new_game_button = (Button) findViewById(R.id.new_game_button);
        menu_button = (Button) findViewById(R.id.menu_button);
        new_game_layout = (ConstraintLayout) findViewById(R.id.new_game_layout);

        new_game_button.setOnClickListener(this);
        menu_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.new_game_button:
                intent = new Intent(this, ChooseLvlActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_button:
                intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}
