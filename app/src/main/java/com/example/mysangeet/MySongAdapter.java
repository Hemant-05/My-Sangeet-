package com.example.mysangeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MySongAdapter extends ArrayAdapter<String> {
    Context context;
    String[] items;
    LayoutInflater layoutInflater ;

    public MySongAdapter(@NonNull Context context, int resource, @NonNull String[] items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.song_show_items,null);

        TextView showing_song_name = view.findViewById(R.id.showing_song_name);
        showing_song_name.setText(items[position]);
        return view;
    }

    @Override
    public int getCount() {
        return items.length;
    }
}
