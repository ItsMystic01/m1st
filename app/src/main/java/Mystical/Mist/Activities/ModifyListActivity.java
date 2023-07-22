package Mystical.Mist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import Mystical.Mist.R;

public class ModifyListActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.background_image).setOnClickListener(view -> {
            Intent intent = new Intent(ModifyListActivity.this, SongListActivity.class);
            intent.putExtra("type", "lyrics_and_chords");
            startActivity(intent);
        });

        FloatingActionButton button = findViewById(R.id.addSongButtonInMain);
        button.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigational_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_option_lyrics) {
                Intent intent = new Intent(ModifyListActivity.this, SongListActivity.class);
                intent.putExtra("type", "lyrics");
                startActivity(intent);
            } else if (itemId == R.id.nav_option_chords) {
                Intent intent = new Intent(ModifyListActivity.this, SongListActivity.class);
                intent.putExtra("type", "chords");
                startActivity(intent);
            } else if (itemId == R.id.nav_option_lyrics_and_chords) {
                Intent intent = new Intent(ModifyListActivity.this, SongListActivity.class);
                intent.putExtra("type", "lyrics_and_chords");
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_option_add_song) {
                Intent intent = new Intent(ModifyListActivity.this, AddSongActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_option_modify_song) {
                Toast.makeText(ModifyListActivity.this, "Modify Song - Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_option_line_up) {
                Intent intent = new Intent(ModifyListActivity.this, LineUpListActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_option_favorites) {
                Intent intent = new Intent(ModifyListActivity.this, FavoritesListActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}