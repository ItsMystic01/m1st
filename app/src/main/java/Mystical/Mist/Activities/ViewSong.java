package Mystical.Mist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class ViewSong extends AppCompatActivity {

    private TextView viewSongName, viewSongAuthor, viewSongLyricsAndChords;
    private String name, author, lyricsAndChords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_song);

        viewSongName = findViewById(R.id.songTitle);
        viewSongAuthor = findViewById(R.id.songAuthor);
        viewSongLyricsAndChords = findViewById(R.id.songLyricsAndChords);

        getAndSetIntentData();
    }

    public void getAndSetIntentData() {
        if(getIntent().hasExtra("songName") && getIntent().hasExtra("songAuthor")
                && getIntent().hasExtra("songLyricsAndChords")) {
            name = getIntent().getStringExtra("songName");
            author = getIntent().getStringExtra("songAuthor");
            lyricsAndChords = getIntent().getStringExtra("songLyricsAndChords");

            viewSongName.setText(name);
            viewSongAuthor.setText(author);
            viewSongLyricsAndChords.setText(lyricsAndChords);
        }
    }
}