package Mystical.Mist.Functionalities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.util.ArrayList;

import Mystical.Mist.Activities.ViewSong;
import Mystical.Mist.SongManager.Song;

public class IntentsFunctionality {

    public void viewSongIntent(Context context, ArrayList<Song> songArrayList, int position) {
        Intent intent = new Intent(context, ViewSong.class);
        intent.putExtra("songName", songArrayList.get(position).getSongName());
        intent.putExtra("songAuthor", songArrayList.get(position).getSongAuthor());
        intent.putExtra("songContent", songArrayList.get(position).getSongContent());
        context.startActivity(intent);
    }

    public Bitmap rotateBitmapByOrientation(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                // No rotation needed
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
