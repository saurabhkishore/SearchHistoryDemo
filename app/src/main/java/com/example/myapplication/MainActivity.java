package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * https://medium.com/@droidbyme/autocomplete-textview-in-android-a1bf5fc112f6
 * https://proxus.dk/Blog/Post/2014/12/19/android-autocomplete-edittext-with-history
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private Set<String> history;

    private String[] fruits = {"Apple", "Appy", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
    private AutoCompleteTextView autoTextView;
    private ArrayList<Fruit> fruitArrayList;
    private FruitAdapter fruitAdapter;


    private void init(){
        fruitArrayList = new ArrayList<>();
        fruitArrayList.add(new Fruit("apple", "3"));
        fruitArrayList.add(new Fruit("apple2", "1"));
        fruitArrayList.add(new Fruit("apple3", "1"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        autoTextView = findViewById(R.id.textInput);
        fruitAdapter = new FruitAdapter(this, R.layout.recent_row, fruitArrayList);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this, android.R.layout.select_dialog_item, fruits);

        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(fruitAdapter);

        autoTextView.setOnItemClickListener(this);
//        autoTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                return false;
//            }
//        });

        autoTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER){
                    Toast.makeText(MainActivity.this, "" + i, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "" + i, Toast.LENGTH_SHORT);
    }

    public static class Fruit{
        public int image;
        public String name;
        public String desc;

        public Fruit(String name, String desc){
            this.image = R.drawable.ic_launcher_background;
            this.name = name;
            this.desc = desc;
        }
    }
}
