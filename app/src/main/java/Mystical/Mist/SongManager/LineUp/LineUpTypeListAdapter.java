package Mystical.Mist.SongManager.LineUp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class LineUpTypeListAdapter extends RecyclerView.Adapter<LineUpTypeListAdapter.ViewHolder>{

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private final Context CONTEXT;

    Animation translateAnimation;

    public LineUpTypeListAdapter(Context context) {
        CONTEXT = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntentsFunctionality intentsFunctionality = new IntentsFunctionality();

        holder.listViewSongName.setText(songArrayList.get(position).getSongName());
        holder.listViewSongAuthor.setText(songArrayList.get(position).getSongAuthor());
        holder.listViewParent.setOnClickListener(view -> {
            intentsFunctionality.viewSongIntent(CONTEXT, songArrayList, position);
        });
    }

    @Override
    public int getItemCount() { return songArrayList.size(); }

    @SuppressLint("NotifyDataSetChanged")
    public void setSongs(ArrayList<Song> songArrayList) { this.songArrayList = songArrayList; notifyDataSetChanged(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView listViewSongName;
        private final TextView listViewSongAuthor;
        private final CardView listViewParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listViewSongName = itemView.findViewById(R.id.listViewSongName);
            listViewSongAuthor = itemView.findViewById(R.id.listViewSongAuthor);
            listViewParent = itemView.findViewById(R.id.listViewParent);

            translateAnimation = AnimationUtils.loadAnimation(CONTEXT, R.anim.translate_animation);
            listViewParent.setAnimation(translateAnimation);
        }
    }
}
