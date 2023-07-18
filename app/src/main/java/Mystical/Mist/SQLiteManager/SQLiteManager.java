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
import java.util.ArrayList;
import java.util.HashSet;

public class SQLiteManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SongLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private final String LYRICS_AND_CHORDS = "lyrics_and_chords";
    private final String LYRICS = "lyrics";
    private final String CHORDS = "chords";
    private final String LINE_UP = "line_up";
    private final String FAVORITES = "favorites";

    private final Context CONTEXT;
    private SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.CONTEXT = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createDatabaseTables(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + LYRICS_AND_CHORDS);
        database.execSQL("DROP TABLE IF EXISTS " + LYRICS);
        database.execSQL("DROP TABLE IF EXISTS " + CHORDS);
        database.execSQL("DROP TABLE IF EXISTS " + LINE_UP);
        database.execSQL("DROP TABLE IF EXISTS " + FAVORITES);
        onCreate(database);
    }

    public void addSongToSongList(String tableName, String title, String songAuthor, String songLyricsAndChords, byte[] songImageCover, int orientation) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("song_title", title);
        contentValues.put("song_author", songAuthor);
        contentValues.put("song_content", songLyricsAndChords);
        contentValues.put("song_image_cover", songImageCover);
        contentValues.put("song_image_orientation", orientation);
        long result = sqLiteDatabase.insert(tableName, null, contentValues);
        if(result == -1) { Toast.makeText(CONTEXT, "Failed to add song", Toast.LENGTH_LONG).show(); return; }
        Toast.makeText(CONTEXT, "Successfully added!", Toast.LENGTH_LONG).show();
    }

    public void addSongToPersonalList(String tableName, String title, String type) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("song_title", title);
        contentValues.put("song_type", type);
        long result = sqLiteDatabase.insert(tableName, null, contentValues);
        if(result == -1) { Toast.makeText(CONTEXT, "Failed to add song to the current " + tableName, Toast.LENGTH_LONG).show(); return; }
        Toast.makeText(CONTEXT, "Song has been added to the current " + tableName, Toast.LENGTH_LONG).show();
    }

    public Cursor getCursor(String tableName) {
        String query = "SELECT * FROM " + tableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    public void createDatabaseTables(SQLiteDatabase database) {
        HashSet<String> queries = new HashSet<>();

        queries.add("CREATE TABLE " + LYRICS_AND_CHORDS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, song_title TEXT, " +
                "song_author TEXT, song_content TEXT, song_image_cover BLOB, song_image_orientation INTEGER);");
        queries.add("CREATE TABLE " + LYRICS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, song_title TEXT, " +
                "song_author TEXT, song_content TEXT, song_image_cover BLOB, song_image_orientation INTEGER);");
        queries.add("CREATE TABLE " + CHORDS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, song_title TEXT, " +
                "song_author TEXT, song_content TEXT, song_image_cover BLOB, song_image_orientation INTEGER);");
        queries.add("CREATE TABLE " + LINE_UP + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, song_title TEXT, song_type TEXT);");
        queries.add("CREATE TABLE " + FAVORITES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, song_title TEXT, song_type TEXT);");

        for (String query : queries) {
            database.execSQL(query);
        }
    }

    public void deleteItem(String tableName, String row_id) {
        long result = sqLiteDatabase.delete(tableName, "_id=?", new String[]{(row_id)});
        if(result == -1) { Toast.makeText(CONTEXT, "Failed to execute deletion.", Toast.LENGTH_LONG).show(); return; }
        Toast.makeText(CONTEXT, "Successfully deleted.", Toast.LENGTH_LONG).show();
    }

}
