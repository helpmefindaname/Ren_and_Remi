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
public class SpecialsGuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout specials_guide_layout;
    private ImageView app_name_image;
    private TextView specials_guide_text;
    private TextView specials_description;
    private ImageView reflector_image;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specials_guide);

        specials_guide_layout = (ConstraintLayout) findViewById(R.id.specials_guide_layout);
        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        specials_guide_text = (TextView) findViewById(R.id.specials_guide_text);
        specials_description = (TextView) findViewById(R.id.specials_description);
        reflector_image=(ImageView) findViewById(R.id.reflector_image);
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
