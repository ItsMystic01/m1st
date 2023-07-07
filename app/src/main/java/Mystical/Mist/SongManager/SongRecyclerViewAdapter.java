package Mystical.Mist.SongManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Mystical.Mist.R;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Song> songArrayList = new ArrayList<>();
    private Context context;


    public SongRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songTxtName.setText(songArrayList.get(position).getSongName());
        holder.songAuthorName.setText(songArrayList.get(position).getSongAuthor());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() { return songArrayList.size(); }

    @SuppressLint("NotifyDataSetChanged")
    public void setSongs(ArrayList<Song> songArrayList) { this.songArrayList = songArrayList; notifyDataSetChanged(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView songTxtName;
        private TextView songAuthorName;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTxtName = itemView.findViewById(R.id.songTxtName);
            songAuthorName = itemView.findViewById(R.id.songAuthorName);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
