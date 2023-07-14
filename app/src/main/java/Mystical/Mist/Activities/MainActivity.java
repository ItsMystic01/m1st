package Mystical.Mist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.background_image).setOnClickListener(view -> {
            try (SQLiteManager sqLiteManager = new SQLiteManager(this)) {
                if (sqLiteManager.getCursor().getCount() == 0) {
                    Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            Intent intent = new Intent(MainActivity.this, SongListActivity.class);
            startActivity(intent);
        });

        FloatingActionButton button = findViewById(R.id.addSongButtonInMain);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddSongActivity.class);
            startActivity(intent);
        });
    }
}