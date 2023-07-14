package Mystical.Mist.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;
import Mystical.Mist.SongManager.Song;
import Mystical.Mist.SongManager.SongRecyclerViewAdapter;
import Mystical.Mist.SongManager.TypeListViewSongListAdapter;

public class SongListActivity extends AppCompatActivity {

    private RecyclerView songsRecView;
    private SQLiteManager sqLiteManager;
    private final ArrayList<Song> songArrayList = new ArrayList<>();
    private TypeListViewSongListAdapter typeListViewSongListAdapter;
    private SongRecyclerViewAdapter songRecyclerViewAdapter;
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        songsRecView = findViewById(R.id.songsRecView);

        sqLiteManager = new SQLiteManager(SongListActivity.this);

        storeDataToArrayList();

        typeListViewSongListAdapter = new TypeListViewSongListAdapter(this);
        typeListViewSongListAdapter.setSongs(songArrayList);

        songsRecView.setAdapter(typeListViewSongListAdapter);
        songsRecView.setLayoutManager(new LinearLayoutManager(this));
        status = true;

        songRecyclerViewAdapter = new SongRecyclerViewAdapter(this);
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
        Cursor cursor = sqLiteManager.getCursor();
        if(cursor.getCount() == 0) {
            return;
        }
        while (cursor.moveToNext()) {
            songArrayList.add(new Song(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getBlob(4), cursor.getInt(5)));
        }
    }

}
