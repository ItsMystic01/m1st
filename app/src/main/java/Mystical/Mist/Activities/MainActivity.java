package Mystical.Mist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;

import Mystical.Mist.R;
import Mystical.Mist.SongManager.Song;
import Mystical.Mist.SongManager.SongRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView songsRecView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        View additionalImage = findViewById(R.id.additional_image);
        additionalImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSongActivity.class);
                startActivity(intent);
            }
        });
//        songsRecView = findViewById(R.id.songsRecView);
//
//        ArrayList<Song> songArrayList = new ArrayList<>();
//        songArrayList.add(new Song("DOT", "DOT"));
//        songArrayList.add(new Song("TOD", "TOD"));
//        songArrayList.add(new Song("TDO", "TDO"));
//        songArrayList.add(new Song("ODT", "ODT"));
//        songArrayList.add(new Song("TED", "TED"));
//
//        SongRecyclerViewAdapter songRecyclerViewAdapter = new SongRecyclerViewAdapter(this);
//        songRecyclerViewAdapter.setSongs(songArrayList);
//
//        songsRecView.setAdapter(songRecyclerViewAdapter);
//        songsRecView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}