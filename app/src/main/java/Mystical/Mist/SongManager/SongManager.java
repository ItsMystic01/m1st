package Mystical.Mist.SongManager;

import android.content.Context;
import android.content.Intent;
import android.widget.Adapter;

import java.util.ArrayList;

import Mystical.Mist.Activities.MainActivity;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class SongManager {

    public static void deleteSong(Context context, SQLiteManager sqLiteManager, String tableName,
                                  int position, ArrayList<Song> songArrayList) {

        sqLiteManager.deleteItem(tableName, songArrayList.get(position).getSongName(), songArrayList.get(position).getSongType());
        songArrayList.remove(position);

        if (songArrayList.isEmpty()) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}
