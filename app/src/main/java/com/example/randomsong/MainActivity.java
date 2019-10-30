package com.example.randomsong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button addSong, randomSong;
    TextView nameRandomSong;
    EditText nameAddSong;
    private static final String SHARED_PREFS_NAME = "MY_SHARED_PREF";
    ArrayList<String> listSongs = new ArrayList<String>();
    static ArrayList<Integer> randomNum = new ArrayList<Integer>();
    ArrayAdapter<String> adapter;
    public static final String KEY_MSG1 = "com.example.dina.msg1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSong = findViewById(R.id.buttonAdd);
        randomSong = findViewById(R.id.buttonRandom);
        nameAddSong = findViewById(R.id.editTextAddSong);
        nameRandomSong = findViewById(R.id.textViewRandomSong);
        listSongs = getArray();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSongs);


    }

    private ArrayList<String> getArray() {
        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);

        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<String>(set);
    }
    public boolean saveArray() {
        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(listSongs);
        mEdit1.putStringSet("list", set);
        return mEdit1.commit();
    }

    public void randomSong(View view) {
        if(listSongs.size() != 0) {
            if(listSongs.size() != randomNum.size()) {
                int random = (int) (Math.random() * listSongs.size());
                while (checkRandom(random)) {
                    random = (int) (Math.random() * listSongs.size());
                }
                nameRandomSong.setText(listSongs.get(random));
            }else {
                Toast toast = Toast.makeText(this, "Песни закончились", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(this, "Добавьте песню", Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    public void addSong(View view) {

        listSongs.add(nameAddSong.getText().toString());
        adapter.notifyDataSetChanged();
        nameAddSong.getText().clear();
    }

    public static boolean checkRandom(int random){

        if(randomNum.contains(random)){
                return true;
            }
            else{
                randomNum.add(random);
                return false;
            }
    }
    public void onStop() {
        saveArray();
        super.onStop();
    }

    public void showList(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra(KEY_MSG1, listSongs);
        startActivity(i);
    }
}
