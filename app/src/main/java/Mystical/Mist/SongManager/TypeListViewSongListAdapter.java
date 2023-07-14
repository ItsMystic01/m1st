package Mystical.Mist.SongManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Mystical.Mist.Functionalities.IntentsFunctionality;
import Mystical.Mist.R;

public class TypeListViewSongListAdapter extends RecyclerView.Adapter<TypeListViewSongListAdapter.ViewHolder>{

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private Context context;

    public TypeListViewSongListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TypeListViewSongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntentsFunctionality intentsFunctionality = new IntentsFunctionality();

        holder.listViewSongName.setText(songArrayList.get(position).getSongName());
        holder.listViewSongAuthor.setText(songArrayList.get(position).getSongAuthor());
        holder.listViewParent.setOnClickListener(view -> {
            intentsFunctionality.viewSongIntent(context, songArrayList, position);
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
        }
    }
}
