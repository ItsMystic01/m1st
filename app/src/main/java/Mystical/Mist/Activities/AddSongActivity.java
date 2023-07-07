package Mystical.Mist.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Mystical.Mist.R;
import Mystical.Mist.SongManager.Song;
import Mystical.Mist.SongManager.SongRecyclerViewAdapter;

public class AddSongActivity extends AppCompatActivity {

    private RecyclerView songsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsRecView = findViewById(R.id.songsRecView);

        ArrayList<Song> songArrayList = new ArrayList<>();
        songArrayList.add(new Song("DOT", "DOT"));
        songArrayList.add(new Song("TOD", "TOD"));
        songArrayList.add(new Song("TDO", "TDO"));
        songArrayList.add(new Song("ODT", "ODT"));
        songArrayList.add(new Song("TED", "TED"));

        SongRecyclerViewAdapter songRecyclerViewAdapter = new SongRecyclerViewAdapter(this);
        songRecyclerViewAdapter.setSongs(songArrayList);

        songsRecView.setAdapter(songRecyclerViewAdapter);
        songsRecView.setLayoutManager(new GridLayoutManager(this, 2));
    }

}
