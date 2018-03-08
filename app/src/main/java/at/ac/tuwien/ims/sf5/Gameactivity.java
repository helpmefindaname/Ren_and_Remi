package at.ac.tuwien.ims.sf5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import at.ac.tuwien.ims.sf5.activity.DefeatActivity;
import at.ac.tuwien.ims.sf5.activity.MainActivity;
import at.ac.tuwien.ims.sf5.activity.PauseActivity;
import at.ac.tuwien.ims.sf5.activity.WinSplashScreenActivity;
import at.ac.tuwien.ims.sf5.data.RoboAlien;
import at.ac.tuwien.ims.sf5.helper.GameResourceManager;

/**
 * @Author Iris Fiedler
 * the basic activity for starting the game
 */
public class Gameactivity extends Activity {

    private boolean player1Ai, player2Ai;
    private boolean spawnReflector;
    ImageView app_name_image, avatar;
    private ImageButton dmg_up_Button;
    private ImageButton dmg_down_Button;
    private ImageButton rotate_left_Button;
    private ImageButton rotate_right_Button;
    private ImageButton move_left_Button;
    private ImageButton move_right_Button;

    private ImageButton weapon1_button;
    private ImageButton weapon2_button;

    private Button shoot_Button;

    private ImageView[] images;

    private boolean shoot;
    private GameLoop loop;

    private boolean rotateLeft;
    private boolean rotateRight;

    private boolean moveLeft;
    private boolean moveRight;

    private boolean dmgUp;
    private boolean dmgDown;
    private int selectedWeaponId;
    private boolean canChangeWeapon;
    private int level;
    private String avatarName;

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        app_name_image = (ImageView) findViewById(R.id.app_name_image);
        ImageButton pause_Button = (ImageButton) findViewById(R.id.pause_imageButton);
        ImageButton music_Button = (ImageButton) findViewById(R.id.audio_imageButton);
        dmg_up_Button = (ImageButton) findViewById(R.id.blue_plus);
        dmg_down_Button = (ImageButton) findViewById(R.id.yellow_minus);
        rotate_left_Button = (ImageButton) findViewById(R.id.left_arrow_button);
        rotate_right_Button = (ImageButton) findViewById(R.id.right_arrow_button);
        avatar = (ImageView) findViewById(R.id.alien_avatar_image);
        shoot_Button = (Button) findViewById(R.id.shoot_button);
        move_left_Button = (ImageButton) findViewById(R.id.move_left_arrow_button);
        move_right_Button = (ImageButton) findViewById(R.id.move_right_arrow_button);

        weapon1_button = (ImageButton) findViewById(R.id.select_weapon1_button);
        weapon2_button = (ImageButton) findViewById(R.id.select_weapon2_button);

        images = new ImageView[]{
                (ImageView) findViewById(R.id.hearts_image1),
                (ImageView) findViewById(R.id.hearts_image2),
                (ImageView) findViewById(R.id.hearts_image3),
                (ImageView) findViewById(R.id.hearts_image4),
                (ImageView) findViewById(R.id.hearts_image5)
        };

        pause_Button.setOnClickListener((e) -> {

            loop.pause();
            startActivity(new Intent(this, PauseActivity.class));
        });

        music_Button.setOnClickListener((e) -> {
            GameResourceManager.getManager().changeMusicMuted();
        });

        dmg_up_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    dmgUp = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        dmg_down_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    dmgDown = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        rotate_left_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    rotateLeft = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        rotate_right_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    rotateRight = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        move_left_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    moveLeft = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        move_right_Button.setOnTouchListener((v, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_CANCEL:
                    moveRight = e.getAction() == MotionEvent.ACTION_DOWN;
                    return true;
            }
            return false;
        });

        shoot_Button.setOnClickListener((e) -> {
            this.canChangeWeapon = false;
            shoot = true;
        });

        weapon1_button.setOnClickListener((e) -> {
            if (!canChangeWeapon) {
                return;
            }
            setWeaponSelected(0);
        });
        weapon2_button.setOnClickListener((e) -> {
            if (!canChangeWeapon) {
                return;
            }
            setWeaponSelected(1);
        });

        Bundle data = getIntent().getExtras();

        if (data == null) {
            data = new Bundle();
        }

        player1Ai = data.getBoolean("player1Ai", false);
        player2Ai = data.getBoolean("player2Ai", false);
        spawnReflector = data.getBoolean("spawnReflector", true);
        level = data.getInt("level", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameResourceManager.getManager().setListener((mp) -> {
            GameResourceManager.getManager().resumeSound();
            if (!mp.isPlaying()) {
                GameResourceManager.getManager().playMusic("background_music", true);
            }
        });
        GameResourceManager.getManager().loadResources(getResources(), this);
        GameResourceManager.getManager().resumeSound();
        this.setWeaponSelected(selectedWeaponId);
        this.setupAvatar(avatarName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameResourceManager.getManager().pauseSound();
        GameResourceManager.getManager().unloadResources();
    }

    public void nextActivity(GameResult gameResult, int score) {

        if (loop != null) {
            loop.stopLoop();
            loop = null;
        }

        Class<?> classToGo = MainActivity.class;

        switch (gameResult) {
            case win:
                classToGo = WinSplashScreenActivity.class;
                break;
            case draw:
            case loose:
                classToGo = DefeatActivity.class;
                break;
        }

        Log.i("gameactivity", "Ended with: " + gameResult.name());

        Intent i = new Intent(this, classToGo);
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("result", gameResult.name());
        i.putExtras(bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public ContentResolver getContentResolver() {
        return super.getContentResolver();
    }

    public boolean isPlayer1Ai() {
        return player1Ai;
    }

    public boolean isPlayer2Ai() {
        return false;
    }

    public boolean isSpawnReflector() {
        return spawnReflector;
    }

    public void setUpPlayerUi(RoboAlien player) {
        this.selectedWeaponId = player.getWeapon();
        shoot = false;
        rotateLeft = false;
        rotateRight = false;
        dmgUp = false;
        dmgDown = false;
        moveLeft = false;
        moveRight = false;

        this.runOnUiThread(() -> {
            setupWeapons(player, true);

            setupAvatar(player.getName());
            setupHp(player.getHp() / 2);

            shoot_Button.setVisibility(View.VISIBLE);
            rotate_left_Button.setVisibility(View.VISIBLE);
            rotate_right_Button.setVisibility(View.VISIBLE);

            dmg_up_Button.setVisibility(View.VISIBLE);
            dmg_down_Button.setVisibility(View.VISIBLE);

            move_left_Button.setVisibility(View.VISIBLE);
            move_right_Button.setVisibility(View.VISIBLE);
        });
    }

    private void setupHp(int hp) {
        for (int i = 0; i < 5; i++) {
            images[i].setVisibility(i < hp ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setUpAiUi(RoboAlien player) {

        this.runOnUiThread(() -> {
            setupWeapons(player, false);
            setupHp(player.getHp() / 2);
            setupAvatar(player.getName());
            shoot_Button.setVisibility(View.INVISIBLE);
            rotate_left_Button.setVisibility(View.INVISIBLE);
            rotate_right_Button.setVisibility(View.INVISIBLE);

            dmg_up_Button.setVisibility(View.INVISIBLE);
            dmg_down_Button.setVisibility(View.INVISIBLE);

            move_left_Button.setVisibility(View.INVISIBLE);
            move_right_Button.setVisibility(View.INVISIBLE);
            shoot = false;
        });
    }

    private void setupWeapons(RoboAlien player, boolean canChange) {
        this.canChangeWeapon = canChange;
        int weapon = player.getWeapon();
        while (weapon != -1 && !player.hasAmo(weapon)) {
            weapon--;
        }
        player.setWeapon(weapon);

        weapon1_button.setActivated(player.hasAmo(0));
        weapon2_button.setActivated(player.hasAmo(1));
        setWeaponSelected(weapon);
    }

    private void setWeaponSelected(int weapon) {
        this.selectedWeaponId = weapon;

        weapon1_button.setImageBitmap(GameResourceManager.getManager().getBitmap("weapon_1" + ((weapon == 0) ? "_selected" : "")));
        weapon2_button.setImageBitmap(GameResourceManager.getManager().getBitmap("weapon_2" + ((weapon == 0) ? "_selected" : "")));
        weapon2_button.setImageResource(
                this.getResources()
                        .getIdentifier(
                                "tripple_weapon" + ((weapon == 1) ? "_selected" : ""),
                                "drawable",
                                this.getPackageName()
                        )
        );
    }

    private void setupAvatar(String name) {
        this.avatarName = name;
        avatar.setImageBitmap(GameResourceManager.getManager().getBitmap("profile_avatar_" + name));
    }

    public boolean isShoot() {
        return shoot;
    }

    public GameLoop getLoop() {
        return loop;
    }

    public GameLoop createLoop() {
        return loop = new GameLoop(this);
    }

    public boolean isRotateLeft() {
        return rotateLeft;
    }

    public boolean isRotateRight() {
        return rotateRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isDmgUp() {
        return dmgUp;
    }

    public boolean isDmgDown() {
        return dmgDown;
    }

    public int getSelectedWeaponId() {
        return selectedWeaponId;
    }

    public int getLevel() {
        return level;
    }
}
