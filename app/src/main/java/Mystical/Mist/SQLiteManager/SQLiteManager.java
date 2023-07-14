package Mystical.Mist.SQLiteManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class SQLiteManager extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "SongLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "song_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_AUTHOR = "song_author";
    private static final String COLUMN_LYRICS_AND_CHORDS = "lyrics_and_chords";
    private static final String COLUMN_IMAGE_COVER = "song_image_cover";
    private static final String COLUMN_IMAGE_ORIENTATION = "song_image_orientation";


    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_LYRICS_AND_CHORDS + " TEXT, " +
                        COLUMN_IMAGE_COVER + " BLOB, " +
                        COLUMN_IMAGE_ORIENTATION + " INTEGER);";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public void addSong(String title, String songAuthor, String songLyricsAndChords, byte[] songImageCover, int orientation) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_AUTHOR, songAuthor);
        contentValues.put(COLUMN_LYRICS_AND_CHORDS, songLyricsAndChords);
        contentValues.put(COLUMN_IMAGE_COVER, songImageCover);
        contentValues.put(COLUMN_IMAGE_ORIENTATION, orientation);
        long result = database.insert(TABLE_NAME, null, contentValues);
        if(result == -1) { Toast.makeText(context, "Failed to add song", Toast.LENGTH_LONG).show(); return; }
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
    }

    public Cursor getCursor() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
}
