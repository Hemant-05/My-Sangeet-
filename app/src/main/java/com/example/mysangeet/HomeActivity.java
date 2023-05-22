package com.example.mysangeet;

import static android.Manifest.permission.READ_MEDIA_AUDIO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    ListView song_list_view;
//    SearchView search_song;
    ArrayList<File> mySongs;
    MySongAdapter adapter;
//    ArrayAdapter<String> adapter;
//    RecyclerView song_recycler_view;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        song_list_view = findViewById(R.id.song_list_view);
//        search_song = findViewById(R.id.search_song);

//        song_recycler_view = findViewById(R.id.song_recycler_view);
        Dexter.withContext(HomeActivity.this)
                .withPermission(READ_MEDIA_AUDIO)
                .withListener(new PermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        Toast.makeText(HomeActivity.this, "Granted....", Toast.LENGTH_SHORT).show();
                        mySongs = fetchSong(Environment.getExternalStorageDirectory());
                        String [] items = new String[mySongs.size()];
                        for (int i = 0; i < mySongs.size(); i++) {
                            items[i] = mySongs.get(i).getName().replace(".mp3"," ");
                        }
//                          adapter= new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1,items);
                          adapter = new MySongAdapter(HomeActivity.this,R.layout.song_show_items,items);
//                        ReceyclerViewSongAdapter adapter = new ReceyclerViewSongAdapter(items);
//                        song_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                          song_list_view.setAdapter(adapter);

                          song_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
                                String currentSong = song_list_view.getItemAtPosition(position).toString();
                                intent.putExtra("songList",mySongs);
                                intent.putExtra("currentSong",currentSong);
                                intent.putExtra("position",position);
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
        /*search_song.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
//                ArrayList<File> filteredList = filter(mySongs,newText);
                adapter.getFilter().filter(newText);
                return true;
            }
            private ArrayList<File> customFilter(ArrayList<File> songsList, String query){
                ArrayList<File> queryList = new ArrayList<>();
                query = query.toLowerCase();
                for (File myquery : songsList) {
                   String str =  myquery.toString().toLowerCase();
                   if(songsList.contains(str)){
                       queryList.add(myquery);
                   }
                }
                return queryList;
            }
        });*/
    }
    public ArrayList<File> fetchSong(File file){
        ArrayList songList = new ArrayList();
        File[] songs = file.listFiles();
        if(songs != null){
            for(File myFile : songs){
                if(!myFile.isHidden() && myFile.isDirectory()){
                    songList.addAll(fetchSong(myFile));
                }else{
                    if(myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith(".")){
                        songList.add(myFile);
                    }
                }
            }
        }
        return songList;
    }
}