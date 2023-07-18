package Mystical.Mist.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class ViewSong extends AppCompatActivity {

    private TextView viewSongName, viewSongAuthor, viewSongContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_song);

        viewSongName = findViewById(R.id.songTitle);
        viewSongAuthor = findViewById(R.id.songAuthor);
        viewSongContent = findViewById(R.id.songContent);

        getAndSetIntentData();

        viewSongContent.setMovementMethod(new ScrollingMovementMethod());
    }

    public void getAndSetIntentData() {
        if(getIntent().hasExtra("songName") && getIntent().hasExtra("songAuthor")
                && getIntent().hasExtra("songContent")) {
            String name = getIntent().getStringExtra("songName");
            String author = getIntent().getStringExtra("songAuthor");
            String content = getIntent().getStringExtra("songContent");

            viewSongName.setText(name);
            viewSongAuthor.setText(author);
            viewSongContent.setText(content);
        }
    }
}