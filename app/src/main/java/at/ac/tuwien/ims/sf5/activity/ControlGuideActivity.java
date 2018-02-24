package at.ac.tuwien.ims.sf5.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import at.ac.tuwien.ims.sf5.R;

/**
 * @Author Iris Fiedler
 */
public class ControlGuideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_guide);

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
