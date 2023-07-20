package Mystical.Mist.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;
import Mystical.Mist.SongManager.Song;
import Mystical.Mist.SongManager.LyricsAndChords.SongRecyclerViewAdapter;
import Mystical.Mist.SongManager.LyricsAndChords.TypeListViewSongListAdapter;

public class SongListActivity extends AppCompatActivity {

    private RecyclerView songsRecView;
    private SQLiteManager sqLiteManager;
    private final ArrayList<Song> songArrayList = new ArrayList<>();
    private TypeListViewSongListAdapter typeListViewSongListAdapter;
    private SongRecyclerViewAdapter songRecyclerViewAdapter;
    private boolean status = true;
    private String TABLE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        sqLiteManager = new SQLiteManager(SongListActivity.this);

        TABLE_NAME = getIntent().getStringExtra("type");
        songsRecView = findViewById(R.id.songsRecView);

        storeDataToArrayList();

        typeListViewSongListAdapter = new TypeListViewSongListAdapter(this, sqLiteManager, TABLE_NAME);
        typeListViewSongListAdapter.setSongs(songArrayList);

        songsRecView.setAdapter(typeListViewSongListAdapter);
        songsRecView.setLayoutManager(new LinearLayoutManager(this));
        status = true;

        songRecyclerViewAdapter = new SongRecyclerViewAdapter(this, sqLiteManager, TABLE_NAME);
        songRecyclerViewAdapter.setSongs(songArrayList);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(view -> {
            if(status) {
                songsRecView.setAdapter(songRecyclerViewAdapter);
                songsRecView.setLayoutManager(new GridLayoutManager(this, 2));
                status = false;
            } else {
                songsRecView.setAdapter(typeListViewSongListAdapter);
                songsRecView.setLayoutManager(new LinearLayoutManager(this));
                status = true;
            }
        });
    }

    public void storeDataToArrayList() {
        Cursor cursor = sqLiteManager.getCursor(TABLE_NAME);

        ImageView noDataImageView = findViewById(R.id.empty_icon);
        TextView noDataTextView = findViewById(R.id.no_data_text);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

        if(cursor.getCount() == 0) {
            noDataImageView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
            return;
        }
        while (cursor.moveToNext()) {
            noDataImageView.setVisibility(View.GONE);
            noDataTextView.setVisibility(View.GONE);
            songArrayList.add(new Song(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getBlob(4), cursor.getInt(5), TABLE_NAME));
        }
    }

}
