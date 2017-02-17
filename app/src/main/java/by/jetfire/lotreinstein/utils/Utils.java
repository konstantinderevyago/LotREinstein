package by.jetfire.lotreinstein.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import by.jetfire.lotreinstein.R;
import by.jetfire.lotreinstein.entity.TaskItem;

/**
 * Created by Konstantin on 07.02.2017.
 */

public class Utils {

    private static MediaPlayer mediaPlayer;

    public static List<TaskItem> getTaskItems(Context context) {
        List<TaskItem> taskItems = new ArrayList<>();

        taskItems.add(new TaskItem(1, context.getString(R.string.legolas),
                context.getString(R.string.yellow), context.getString(R.string.wine),
                context.getString(R.string.no), context.getString(R.string.dagger)));
        taskItems.add(new TaskItem(2, context.getString(R.string.aragorn),
                context.getString(R.string.blue), context.getString(R.string.gin),
                context.getString(R.string.peric), context.getString(R.string.suspension)));
        taskItems.add(new TaskItem(3, context.getString(R.string.gimly),
                context.getString(R.string.red), context.getString(R.string.ale),
                context.getString(R.string.latania), context.getString(R.string.curl)));
        taskItems.add(new TaskItem(4, context.getString(R.string.bilbo),
                context.getString(R.string.green), context.getString(R.string.beer),
                context.getString(R.string.verginia), context.getString(R.string.ring)));
        taskItems.add(new TaskItem(5, context.getString(R.string.gendalf),
                context.getString(R.string.white), context.getString(R.string.cognac),
                context.getString(R.string.maryland), context.getString(R.string.book)));

        return taskItems;
    }

    public static void playMusic(Context context, int mediaRes) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer = MediaPlayer.create(context, mediaRes);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
