package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import at.ac.tuwien.ims.sf5.R;
/**
 * @Author Iris Fiedler
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button control_name_button;
    private Button app_name_button;
    private Button weapons_button;
    private Button game_specials_button;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        control_name_button=(Button)findViewById(R.id.control_name_button);
        app_name_button=(Button)findViewById(R.id.app_name_button);
        weapons_button=(Button)findViewById(R.id.weapons_button);
        game_specials_button=(Button)findViewById(R.id.game_specials_button);
        back_button = (Button) findViewById(R.id.back_button);

        control_name_button.setOnClickListener(this);
        app_name_button.setOnClickListener(this);
        weapons_button.setOnClickListener(this);
        game_specials_button.setOnClickListener(this);
        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_name_button:
                runActivity(ControlGuideActivity.class);
                break;
            case R.id.app_name_button:
                runActivity(GameGuideActivity.class);
                break;
            case R.id.weapons_button:
                runActivity(WeaponGuideActivity.class);
                break;
            case R.id.game_specials_button:
                runActivity(SpecialsGuideActivity.class);
                break;
            case R.id.back_button:
                finish();

        }
    }

    private void runActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }

}
