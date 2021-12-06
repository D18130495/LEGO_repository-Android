package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.yushun.lego_repo.pojo.Set;
import com.yushun.lego_repo.setsOperate.SetDBManager;
import com.yushun.lego_repo.setsOperate.SetList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView; // List
    private ListView searchListView; // Search List
    private SearchView searchView; // Search
    private String info; // Info from search bar
    private RadioGroup radioGroup;

    ArrayList<Set> setList = new ArrayList<Set>(); // list use to display on the home
    ArrayList<String> searchList = new ArrayList<String>(); // list use to display search list

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetDBManager setDBManager = new SetDBManager(this);
        setDBManager.open();

//        setDBManager.insertTask(new Set("country", "71722", "bad set", "€321", "https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1.5 1.5x, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=2 2x"));
//        setDBManager.insertTask(new Set("country", "71789", "bad set", "€321", "https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1.5 1.5x, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=2 2x"));

        Cursor cursor= setDBManager.getAllSet();

        if (cursor.moveToFirst()) {
            do {
                Set set= new Set();
                set.setSet_name(cursor.getString(1));
                set.setSet_number(cursor.getString(2));
                set.setSet_description(cursor.getString(3));
                set.setSet_price(cursor.getString(4));
                set.setSet_image(cursor.getString(5));
                searchList.add(cursor.getString(2));
                setList.add(set);
            } while (cursor.moveToNext());
        }

        setDBManager.close();

        // the default list
        listView = (ListView)findViewById(R.id.list);

        SetList list = new SetList(this, setList);
        listView.setAdapter(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DisplaySetInfo.class);

                Bundle b = new Bundle();
                b.putString("setName", setList.get(position).getSet_name());
                b.putString("setNumber", setList.get(position).getSet_number());
                b.putString("setDesc", setList.get(position).getSet_description());
                b.putString("setPrice", setList.get(position).getSet_price());
                b.putString("setImage", setList.get(position).getSet_image());
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        // the search list
        searchListView = (ListView)findViewById(R.id.listSearch);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchList);
        searchListView.setAdapter(adapter);

        searchListView.setTextFilterEnabled(true);
        searchListView.setVisibility(View.GONE);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DisplaySetInfo.class);

                Bundle b = new Bundle();
                b.putString("setName", setList.get(position).getSet_name());
                b.putString("setNumber", setList.get(position).getSet_number());
                b.putString("setDesc", setList.get(position).getSet_description());
                b.putString("setPrice", setList.get(position).getSet_price());
                b.putString("setImage", setList.get(position).getSet_image());
                intent.putExtras(b);

                startActivity(intent);
//                setDBManager.open();
//
//                Cursor cursor= setDBManager.getSetByNumber(s);
//
//                Bundle b = new Bundle();
//                b.putString("setName", cursor.getString(1));
//                b.putString("setNumber", cursor.getString(2));
//                b.putString("setDesc", cursor.getString(3));
//                b.putString("setPrice", cursor.getString(4));
//                b.putString("setImage", cursor.getString(5));
//                intent.putExtras(b);
//                startActivity(intent);
            }
        });

        // the search bar view and search event
        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    info = s;
                    listView.setVisibility(View.GONE);
                    searchListView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(s);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    searchListView.setVisibility(View.GONE);
                }
                return true;
            }
        });

        // bottom menu
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) findViewById(i);
                if(rb.getText().equals("Home")) {
                    listView.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                }
                else if(rb.getText().equals("Basket")) {
                    listView.setVisibility(View.GONE);
                    searchListView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                }
                Toast.makeText(getApplicationContext(),rb.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}