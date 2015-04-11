package com.example.andrescamilo.app;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;


public class Lobby extends ActionBarActivity {
    TextView introT;
    Button add;
    Button next;
    Button prev;
    Button stop;
    ListView listItems;
    MediaPlayer mp=new MediaPlayer();
    int currentS;
    CheckBox shuf;
    boolean swShuf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("myValue");
        introT = (TextView) findViewById(R.id.introT);
        introT.setText(extra);
        listItems = (ListView) findViewById(R.id.listItems);
        swShuf=false;

        shuf=(CheckBox)findViewById(R.id.shufCheck);
        shuf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swShuf=isChecked;
            }
        });

        final ArrayList<File> mySongs= findSongs(Environment.getExternalStorageDirectory());

        final ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<mySongs.size();i++){
            list.add(mySongs.get(i).getName().toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);

        listItems.setAdapter(adapter);
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Title => "+list.get(position), Toast.LENGTH_SHORT).show();
                currentS=position;
                if(!mp.isPlaying()) {
                    mp = MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(position).toString()));
                    mp.start();
                }else{
                    mp.stop();
                    mp.release();
                    mp= MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(position).toString()));
                    mp.start();
                }
            }
        });

        add=(Button)findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                }else{
                    mp.start();
                }
            }
        });
        next=(Button)findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!swShuf) {
                    currentS++;
                }else{
                    Random randomGenerator = new Random();
                    currentS=randomGenerator.nextInt(list.size());
                }
                if(!mp.isPlaying()) {
                    mp = MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(currentS).toString()));
                    mp.start();
                }else{
                    mp.stop();
                    mp.release();
                    mp= MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(currentS).toString()));
                    mp.start();
                }
            }
        });
        prev=(Button)findViewById(R.id.prevButton);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentS!=0) {
                    if(!swShuf) {
                        currentS--;
                    }else{
                        Random randomGenerator = new Random();
                        currentS=randomGenerator.nextInt(list.size());
                    }
                }
                if(!mp.isPlaying()) {
                    mp = MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(currentS).toString()));
                    mp.start();
                }else{
                    mp.stop();
                    mp.release();
                    mp= MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(currentS).toString()));
                    mp.start();
                }
            }
        });
        stop=(Button)findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp1) {
                currentS++;
                mp1.stop();
                mp1.release();
                mp1=MediaPlayer.create(getApplicationContext(), Uri.parse(mySongs.get(currentS).toString()));
                mp1.start();
            }
        });
    }

    public ArrayList<File> findSongs(File root){
        ArrayList<File> al=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File x:files){
            if(x.isDirectory() && !x.isHidden()){
                al.addAll(findSongs(x));
            }else{
                if(x.getName().endsWith(".mp3")){
                    al.add(x);
                }
            }
        }
        return al;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
