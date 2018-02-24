package at.ac.tuwien.ims.sf5.activity;

import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.ac.tuwien.ims.sf5.R;
import at.ac.tuwien.ims.sf5.helper.HighScoreManager;
import at.ac.tuwien.ims.sf5.sqlite.ScoreContract;

public class HighscoreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back_button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.highscore_long_text);

        textView.setText(getText());
    }

    private String getText() {

        CursorLoader loader = HighScoreManager.getManager().loadData(this);
        loader.startLoading();
        Cursor c = loader.loadInBackground();

        StringBuilder text = new StringBuilder();
        c.moveToFirst();

        for (int i = 0; i < c.getCount() && i < 5; i++) {

            int score = c.getInt(c.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE));
            String name = c.getString(c.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME));
            text.append("\n").append(score).append(padLeft(name, 12));
            c.moveToNext();
        }

        return text.toString();
    }

    private String padLeft(String text, int count) {
        return text.length() >= count ? text : padLeft(" " + text, count);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == back_button.getId()) {
            finish();
        }

    }
}
