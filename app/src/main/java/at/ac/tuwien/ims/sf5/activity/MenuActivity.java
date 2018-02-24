package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.GameResourceManager;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        GameResourceManager.getManager().loadResources(getResources(), this);
        locale = GameResourceManager.getManager().getLocale();

        Button highscore_button = (Button) findViewById(R.id.highscore_button);
        Button play_button = (Button) findViewById(R.id.play_button);
        Button introduction_button = (Button) findViewById(R.id.introduction_button);
        Button settings_button = (Button) findViewById(R.id.settings_button);
        Button quit_button = (Button) findViewById(R.id.quit_button);

        highscore_button.setOnClickListener(this);
        play_button.setOnClickListener(this);
        introduction_button.setOnClickListener(this);
        settings_button.setOnClickListener(this);
        quit_button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameResourceManager.getManager().loadResources(getResources(), this);
        if (!locale.equals(GameResourceManager.getManager().getLocale())) {
            recreate();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        GameResourceManager.getManager().unloadResources();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                runActivity(ChooseLvlActivity.class);
                break;
            case R.id.introduction_button:
                runActivity(GuideActivity.class);
                break;
            case R.id.highscore_button:
                runActivity(HighscoreActivity.class);
                break;
            case R.id.settings_button:
                runActivity(OptionsActivity.class);
                break;
            //TODO Soll das Spiel beenden und schließen (zurzeit bleibt es noch offen)
            case R.id.quit_button:
                finish();

        }
    }

    private void runActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }
}
