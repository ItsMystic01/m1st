package Mystical.Mist.SongManager.LyricsAndChords;

import android.annotation.SuppressLint;
import android.content.Context;
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

import Mystical.Mist.Functionalities.IntentsFunctionality;
import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;
import Mystical.Mist.SongManager.Song;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private final Context CONTEXT;
    private final SQLiteManager SQLITE_MANAGER;
    private final String SONG_TYPE;
    Animation translateAnimation;

    public SongRecyclerViewAdapter(Context context, SQLiteManager sqLiteManager, String songType) {
        CONTEXT = context;
        SQLITE_MANAGER = sqLiteManager;
        SONG_TYPE = songType;
    }

    @NonNull
    @Override
    public SongRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

            popupMenu.getMenuInflater().inflate(R.menu.long_press_song_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                TextView songName = view.findViewById(R.id.listViewSongName);

                if(item.getItemId() == R.id.line_up) {
                    SQLITE_MANAGER.addSongToPersonalList("line_up", songName.getText().toString(), SONG_TYPE);
                } else if (item.getItemId() == R.id.favorite) {
                    SQLITE_MANAGER.addSongToPersonalList("favorites", songName.getText().toString(), SONG_TYPE);
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
}
