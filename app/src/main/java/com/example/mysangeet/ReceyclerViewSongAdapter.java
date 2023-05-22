package com.example.mysangeet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceyclerViewSongAdapter extends RecyclerView.Adapter<ReceyclerViewSongAdapter.ViewHolder> {
    private String[] items;
    private AdapterView.OnItemClickListener onItemClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView showing_song_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showing_song_name = itemView.findViewById(R.id.showing_song_name);
        }
        public TextView getTextView(){
            return showing_song_name;
        }
    }
    public ReceyclerViewSongAdapter(String[] items){
        this.items = items;
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ReceyclerViewSongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_show_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceyclerViewSongAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(items[position]);
    }

    @Override
    public int getItemCount(){
        return items.length;
    }
    private interface OnItemClickListener{
        void onItemClick(int position);
    }
}
