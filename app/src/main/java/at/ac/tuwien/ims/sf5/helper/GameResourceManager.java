package at.ac.tuwien.ims.sf5.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import at.ac.tuwien.ims.sf5.R;

/**
 * @Author Benedikt Fuchs
 *
 * singleton
 * handles several resources line images, sounds and shared preferences.
 */

public class GameResourceManager {

    private static final int SOUND_FILES = 3;
    private static GameResourceManager manager;
    private boolean musicMuted;
    private SharedPreferences preferences;
    private boolean skip;
    private String locale;

    /**
     * gets or creates a singleton resourcemanager
     * @return the resourcemanager
     */
    public static GameResourceManager getManager() {
        if (manager == null) {
            manager = new GameResourceManager();
        }
        return manager;
    }

    private boolean isLoaded;

    private SoundPool soundPool;

    private Map<String, Bitmap> bitmaps;
    private Map<String, Integer> sounds;
    private Map<String, MediaPlayer> music;
    private MediaPlayer.OnCompletionListener listener;

    private GameResourceManager() {
        isLoaded = false;
        bitmaps = new HashMap<>();
        sounds = new HashMap<>();
        music = new HashMap<>();
    }

    /**
     * loads all resources for specific resources and context objects
     * @param resources the resources object
     * @param context the context object
     */
    public void loadResources(Resources resources, Context context) {
        if (isLoaded) {
            return;
        }
        isLoaded = true;

        preferences = context.getSharedPreferences("pref", 0);

        musicMuted = preferences.getBoolean("musicMuted", false);
        skip = preferences.getBoolean("skip", false);
        locale = preferences.getString("locale", "de");

        loadBitmap(resources, "reflector", R.drawable.laser);
        loadBitmap(resources, "explosions", R.drawable.explosions_tilemap);
        loadBitmap(resources, "alien_ren", R.drawable.alien_gruen);
        loadBitmap(resources, "alien_remi", R.drawable.alien_rot);
        loadBitmap(resources, "weapon_ren", R.drawable.waffe_gruen);
        loadBitmap(resources, "weapon_remi", R.drawable.waffe_rot);
        loadBitmap(resources, "weapon_1", R.drawable.single_weapon);
        loadBitmap(resources, "weapon_1_selected", R.drawable.single_weapon_selected);
        loadBitmap(resources, "weapon_2", R.drawable.tripple_weapon);
        loadBitmap(resources, "weapon_2_selected", R.drawable.tripple_weapon_selected);
        loadBitmap(resources, "profile_avatar_ren", R.drawable.profile_avatar_ren);
        loadBitmap(resources, "profile_avatar_remi", R.drawable.profile_avatar_remi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = createNewSoundpool();
        } else {
            soundPool = createOldSoundpool();
        }

        loadSound(context, "shoot", R.raw.shoot);
        loadSound(context, "reflect", R.raw.reflect);
        loadSound(context, "explosion", R.raw.explosion);

        loadMusic(context, "background_music", R.raw.background_music);
    }

    private void loadMusic(Context context, String name, int id) {
        music.put(name, MediaPlayer.create(context, id));
        if (listener != null) {
            music.get(name).setOnCompletionListener(listener);
        }
    }

    private void loadSound(Context context, String name, int id) {
        sounds.put(name, soundPool.load(context, id, 1));
    }

    /**
     * unloads all reasources, this includes saving shared references before unloading.
     */
    public void unloadResources() {
        if (!isLoaded) {
            return;
        }
        isLoaded = false;

        for (Bitmap bitmap : bitmaps.values()) {
            bitmap.recycle();
        }
        bitmaps.clear();

        soundPool.release();

        for (MediaPlayer player : music.values()) {
            player.release();
        }

        sounds.clear();
        music.clear();
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean("skip", skip);
        e.putBoolean("musicMuted", musicMuted);
        e.putString("locale", locale);
        e.apply();
        preferences = null;

        listener = null;
    }

    private void loadBitmap(Resources resources, String tag, int id) {
        Log.i("resourceManager", "try load resource " + tag);
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id);
        bitmaps.put(tag, bitmap);
    }

    /**
     * returns a image which is loaded with a specific tag
     * @param tag the tag of the image
     * @return the image to the corresponding tab
     */
    public Bitmap getBitmap(String tag) {
        return bitmaps.get(tag);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createNewSoundpool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(SOUND_FILES)
                .build();
    }

    @SuppressWarnings("deprecation")
    // SupressWarnings should not be used, still in this case it is needed
    private SoundPool createOldSoundpool() {
        return new SoundPool(SOUND_FILES, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * plays the music by a corresponding tag
     * @param tag the tag of the music
     * @param loop if the play should be looped or not.
     */
    public void playMusic(String tag, boolean loop) {
        MediaPlayer m = music.get(tag);
        m.setLooping(loop);
        m.seekTo(0);
        if (musicMuted) return;
        m.start();
    }

    /**
     * plays a sound to a respective tag
     * @param tag the tag of the sound
     */
    public void playSound(String tag) {
        soundPool.play(sounds.get(tag), 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * resumes all sounds and music that have been paused.
     */
    public void resumeSound() {
        if (!musicMuted) {
            for (MediaPlayer player : music.values()) {
                player.start();
            }
        }
        soundPool.autoResume();
    }

    /**
     * pauses all sounds and musics
     */
    public void pauseSound() {
        for (MediaPlayer player : music.values()) {
            player.pause();
        }
        soundPool.autoResume();
    }

    /**
     * set a on music load completion listener
     * @param listener the callback to set
     */
    public void setListener(MediaPlayer.OnCompletionListener listener) {
        this.listener = listener;
    }

    /**
     * returns if the music is muted
     * @return true if the music is muted
     */
    public boolean isMusicMuted() {
        return musicMuted;
    }

    /**
     * mutes or unmutes the music dependent on its previous state
     */
    public void changeMusicMuted() {
        musicMuted = !musicMuted;
        if (musicMuted) {
            for (MediaPlayer player : music.values()) {
                player.pause();
            }
        } else {
            for (MediaPlayer player : music.values()) {
                player.start();
            }
        }
    }

    /**
     * sets the skip attribute for saving
     * @param skip whether the video should be skipped or not.
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public boolean isSkip() {
        return skip;
    }

    /**
     * sets the locale attribute for saving
     * @param locale the current language.
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }
}
