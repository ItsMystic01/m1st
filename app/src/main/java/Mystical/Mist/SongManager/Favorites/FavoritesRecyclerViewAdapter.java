package Mystical.Mist.SongManager.Favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Mystical.Mist.Activities.MainActivity;
import Mystical.Mist.Functionalities.IntentsFunctionality;
import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;
import Mystical.Mist.SongManager.Song;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private final Context CONTEXT;

    private final SQLiteManager SQLITE_MANAGER;
    Animation translateAnimation;

    public FavoritesRecyclerViewAdapter(Context context, SQLiteManager sqLiteManager) {
        CONTEXT = context;
        SQLITE_MANAGER = sqLiteManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntentsFunctionality intentsFunctionality = new IntentsFunctionality();

        if(songArrayList.get(position).getSongImageCover() != null) {
            byte[] imageBytes = songArrayList.get(position).getSongImageCover();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Bitmap rotatedBitmap = intentsFunctionality.rotateBitmapByOrientation(bitmap, songArrayList.get(position).getSongImageOrientation());
            holder.songImageView.setImageBitmap(rotatedBitmap);
        }
        holder.songTxtName.setText(songArrayList.get(position).getSongName());
        holder.songAuthorName.setText(songArrayList.get(position).getSongAuthor());
        holder.parent.setOnClickListener(view -> {
            intentsFunctionality.viewSongIntent(CONTEXT, songArrayList, position);
        });
        holder.parent.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(CONTEXT.getApplicationContext(), view);

            popupMenu.getMenuInflater().inflate(R.menu.long_press_song_to_delete, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.remove_song) {
                    deleteSong(songArrayList.get(position).getSongName(), songArrayList.get(position).getSongType(), position);
                }
                return true;
            });

            popupMenu.show();

            return true;
        });
    }

    @Override
    public int getItemCount() { return songArrayList.size(); }

    @SuppressLint("NotifyDataSetChanged")
    public void setSongs(ArrayList<Song> songArrayList) { this.songArrayList = songArrayList; notifyDataSetChanged(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView songImageView;
        private final TextView songTxtName;
        private final TextView songAuthorName;
        private final CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songImageView = itemView.findViewById(R.id.songImage);
            songTxtName = itemView.findViewById(R.id.songTxtName);
            songAuthorName = itemView.findViewById(R.id.songAuthorName);
            parent = itemView.findViewById(R.id.parent);

            translateAnimation = AnimationUtils.loadAnimation(CONTEXT, R.anim.translate_animation);
            parent.setAnimation(translateAnimation);
        }
    }

    public void deleteSong(String songName, String songType, int position) {
        // Delete the song at the specified position from the database
        SQLITE_MANAGER.deleteItem("favorites", songName, songType);

        // Remove the song from the dataset
        songArrayList.remove(position);

        // Notify the adapter that the dataset has changed
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, songArrayList.size());
        this.setSongs(songArrayList);

        if (songArrayList.isEmpty()) {
            // If it's empty, navigate back to MainActivity
            Intent intent = new Intent(CONTEXT, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            CONTEXT.startActivity(intent);
        }
    }
}
