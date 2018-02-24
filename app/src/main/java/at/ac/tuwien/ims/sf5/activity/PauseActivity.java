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
public class PauseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        Button continue_button = (Button) findViewById(R.id.continue_button);
        Button quit_button = (Button) findViewById(R.id.quit_button);

        continue_button.setOnClickListener(this);
        quit_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.continue_button:
                finish();
                break;
            case R.id.quit_button:
                intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }

    }
}
