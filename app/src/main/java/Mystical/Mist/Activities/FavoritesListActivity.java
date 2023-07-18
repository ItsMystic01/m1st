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
import Mystical.Mist.SongManager.Favorites.FavoritesRecyclerViewAdapter;
import Mystical.Mist.SongManager.Favorites.FavoritesTypeListAdapter;
import Mystical.Mist.SongManager.LyricsAndChords.SongRecyclerViewAdapter;
import Mystical.Mist.SongManager.LyricsAndChords.TypeListViewSongListAdapter;
import Mystical.Mist.SongManager.Song;

public class FavoritesListActivity extends AppCompatActivity {

    private RecyclerView songsRecView;
    private SQLiteManager sqLiteManager;
    private final ArrayList<Song> songArrayList = new ArrayList<>();
    private FavoritesTypeListAdapter favoritesTypeListAdapter;
    private FavoritesRecyclerViewAdapter favoritesRecyclerViewAdapter;
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        songsRecView = findViewById(R.id.songsRecView);

        sqLiteManager = new SQLiteManager(FavoritesListActivity.this);

        storeDataToArrayList();

        favoritesTypeListAdapter = new FavoritesTypeListAdapter(this);
        favoritesTypeListAdapter.setSongs(songArrayList);

        songsRecView.setAdapter(favoritesTypeListAdapter);
        songsRecView.setLayoutManager(new LinearLayoutManager(this));
        status = true;

        favoritesRecyclerViewAdapter = new FavoritesRecyclerViewAdapter(this);
        favoritesRecyclerViewAdapter.setSongs(songArrayList);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(view -> {
            if(status) {
                songsRecView.setAdapter(favoritesRecyclerViewAdapter);
                songsRecView.setLayoutManager(new GridLayoutManager(this, 2));
                status = false;
            } else {
                songsRecView.setAdapter(favoritesTypeListAdapter);
                songsRecView.setLayoutManager(new LinearLayoutManager(this));
                status = true;
            }
        });
    }

    public void storeDataToArrayList() {

        ImageView noDataImageView = findViewById(R.id.empty_icon);
        TextView noDataTextView = findViewById(R.id.no_data_text);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

        Cursor favoritesCursor = sqLiteManager.getCursor("favorites");
        if(favoritesCursor.getCount() == 0) {
            noDataImageView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
            return;
        }

        Cursor mainCursor = sqLiteManager.getCursor("lyrics_and_chords");
        if(mainCursor.getCount() == 0) {
            noDataImageView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
            return;
        }

        noDataImageView.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.GONE);

        while(favoritesCursor.moveToNext()) {
            while (mainCursor.moveToNext()) {
                if (mainCursor.getString(1).equals(favoritesCursor.getString(1))) {
                    songArrayList.add(new Song(mainCursor.getString(1), mainCursor.getString(2),
                            mainCursor.getString(3), mainCursor.getBlob(4), mainCursor.getInt(5)));
                }
            }
        }
    }

}
