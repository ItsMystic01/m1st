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
import java.util.HashMap;
import java.util.Objects;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;
import Mystical.Mist.SongManager.LineUp.LineUpRecyclerViewAdapter;
import Mystical.Mist.SongManager.LineUp.LineUpTypeListAdapter;
import Mystical.Mist.SongManager.Song;

public class LineUpListActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView noDataTextView;
    ImageView noDataImageView;
    private RecyclerView songsRecView;
    private SQLiteManager sqLiteManager;
    private final ArrayList<Song> songArrayList = new ArrayList<>();
    private LineUpTypeListAdapter lineUpTypeListAdapter;
    private LineUpRecyclerViewAdapter lineUpRecyclerViewAdapter;
    private final String[] songTypes = new String[]{"lyrics", "chords", "lyrics_and_chords"};
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        songsRecView = findViewById(R.id.songsRecView);

        sqLiteManager = new SQLiteManager(LineUpListActivity.this);

        noDataImageView = findViewById(R.id.empty_icon);
        noDataTextView = findViewById(R.id.no_data_text);
        floatingActionButton = findViewById(R.id.floating_action_button);

        storeDataToArrayList();
        if(songArrayList.size() == 0) {
            noDataImageView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
            return;
        }

        noDataImageView.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);

        lineUpTypeListAdapter = new LineUpTypeListAdapter(this, sqLiteManager);
        lineUpTypeListAdapter.setSongs(songArrayList);

        songsRecView.setAdapter(lineUpTypeListAdapter);
        songsRecView.setLayoutManager(new LinearLayoutManager(this));
        status = true;

        lineUpRecyclerViewAdapter = new LineUpRecyclerViewAdapter(this, sqLiteManager);
        lineUpRecyclerViewAdapter.setSongs(songArrayList);

        floatingActionButton.setOnClickListener(view -> {
            if(status) {
                songsRecView.setAdapter(lineUpRecyclerViewAdapter);
                songsRecView.setLayoutManager(new GridLayoutManager(this, 2));
                status = false;
            } else {
                songsRecView.setAdapter(lineUpTypeListAdapter);
                songsRecView.setLayoutManager(new LinearLayoutManager(this));
                status = true;
            }
        });
    }

    public void storeDataToArrayList() {

        Cursor lineUpCursor = sqLiteManager.getCursor("line_up");

        HashMap<String, String> songList = new HashMap<>();

        while(lineUpCursor.moveToNext()) {
            songList.put(lineUpCursor.getString(1), lineUpCursor.getString(2));
        }

        for(String songType : songTypes) {
            addSongToArrayList(sqLiteManager.getCursor(songType), songList, songType);
        }
    }

    public void addSongToArrayList(Cursor songCursor, HashMap<String, String> songList, String songType) {
        while(songCursor.moveToNext()) {
            for(String song : songList.keySet()) {
                if(song.equals(songCursor.getString(1)) && Objects.equals(songList.get(song), songType)) {
                    songArrayList.add(new Song(songCursor.getString(1), songCursor.getString(2),
                            songCursor.getString(3), songCursor.getBlob(4), songCursor.getInt(5), songType));
                }
            }
        }
    }
}
