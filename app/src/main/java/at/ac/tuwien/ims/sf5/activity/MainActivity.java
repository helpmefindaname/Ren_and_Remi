package at.ac.tuwien.ims.sf5.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import at.ac.tuwien.ims.sf5.Gameactivity;
import at.ac.tuwien.ims.sf5.R;

/**
 * @Author Iris Fiedler
 * the mainActivity, at the moment it simulates an loading screen.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread welcomeThread = new Thread(this::simulateLoading);
        welcomeThread.start();
    }


    private void simulateLoading() {
        try {
            Thread.sleep(2000);

        } catch (InterruptedException ignored) {

        } finally {
            Intent i = new Intent(this, Gameactivity.class);
            startActivity(i);
            finish();
        }
    }
}
