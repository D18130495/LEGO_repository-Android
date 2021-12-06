package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView; // List
    private SearchView searchView; // search
    private String info; // info from search bar

    ArrayList<Set> setList = new ArrayList<Set>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetDBManager setDBManager = new SetDBManager(this);
        setDBManager.open();

//        setDBManager.insertTask(new Set("country", "71722", "bad set", "€321", "https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1.5 1.5x, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=2 2x"));

        Cursor cursor= setDBManager.getAllTask();

        if (cursor.moveToFirst()) {
            do {
                Set set= new Set();
                set.setSet_name(cursor.getString(1));
                set.setSet_number(cursor.getString(2));
                set.setSet_image(cursor.getString(5));
                setList.add(set);
            } while (cursor.moveToNext());
        }

        setDBManager.close();

        listView = (ListView)findViewById(R.id.list);

        SetList list = new SetList(this, setList);
        listView.setAdapter(list);
        listView.setTextFilterEnabled(true);

        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                if(info.equals("1")) {
                    Toast.makeText(getApplicationContext(),"1" ,Toast.LENGTH_SHORT).show();
                }else if (info.equals("2")) {
                    Toast.makeText(getApplicationContext(),"2" ,Toast.LENGTH_SHORT).show();
                }else if (info.equals("3")) {
                    Toast.makeText(getApplicationContext(),"3" ,Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    info = s;
                    listView.setVisibility(View.GONE);
                    listView.setFilterText(s);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    listView.clearTextFilter();
                }
                return false;
            }
        });
    }
}