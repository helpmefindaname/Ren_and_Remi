package at.ac.tuwien.ims.sf5.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.Switch;

import java.util.Locale;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.GameResourceManager;

public class OptionsActivity extends AppCompatActivity {

    Switch sw;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sw = (Switch) findViewById(R.id.toggleButton_modus);
        GameResourceManager.getManager().loadResources(this.getResources(), this);
        sw.setChecked(!GameResourceManager.getManager().isMusicMuted());
        sw.setOnCheckedChangeListener((a, b) -> {
            GameResourceManager.getManager().changeMusicMuted();
        });
        back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener((a) -> {
            this.finish();
        });

        Button germanButton = (Button) findViewById(R.id.german_button);
        Button englishButton = (Button) findViewById(R.id.english_button);

        germanButton.setOnClickListener((e) -> setLocale("de"));
        englishButton.setOnClickListener((e) -> setLocale("en"));
    }

    public void onDestroy() {
        GameResourceManager.getManager().unloadResources();
        super.onDestroy();
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        GameResourceManager.getManager().setLocale(lang);
        recreate();
    }
}
