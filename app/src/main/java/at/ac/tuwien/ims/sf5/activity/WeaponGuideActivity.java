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
public class WeaponGuideActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout weapon_guide_layout;
    private ImageView app_name_image;
    private TextView weapon_guide_text;
    private TextView weapons_description;
    private ImageView single_weapon_symbole_image;
    private ImageView tripple_weapon_symbole_image;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_guide);

        weapon_guide_layout = (ConstraintLayout) findViewById(R.id.weapon_guide_layout);
        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        weapon_guide_text = (TextView) findViewById(R.id.weapon_guide_text);
        weapons_description = (TextView) findViewById(R.id.weapons_description);
        single_weapon_symbole_image = (ImageView) findViewById(R.id.single_weapon_symbole_image);
        tripple_weapon_symbole_image = (ImageView) findViewById(R.id.tripple_weapon_symbole_image);
        back_button = (Button) findViewById(R.id.back_button);


        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {;
        if (view.getId() == back_button.getId()) {
            finish();
        }
    }
}
