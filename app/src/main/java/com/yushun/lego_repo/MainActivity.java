package com.yushun.lego_repo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushun.lego_repo.basketOperate.BasketDBManager;
import com.yushun.lego_repo.basketOperate.BasketList;
import com.yushun.lego_repo.orderOperate.OrderDBManager;
import com.yushun.lego_repo.orderOperate.OrderList;
import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Order;
import com.yushun.lego_repo.pojo.Set;
import com.yushun.lego_repo.setsOperate.SetDBManager;
import com.yushun.lego_repo.setsOperate.SetList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView listView; // List
    private ListView searchListView; // Search List
    private ListView basketListView; // Basket List
    private ListView orderListView; // Order List
    private SearchView searchView; // Search
    private RadioGroup radioGroup; // bottom menus
    private TextView basketTotalView; // total
    private ImageView purchaseView; // purchase

    private int count = 0;

    ArrayList<Set> setList = new ArrayList<Set>(); // list use to display on the home
    ArrayList<String> searchList = new ArrayList<String>(); // list use to display search list
    ArrayList<Basket> basketList = new ArrayList<Basket>(); // list use to display basket list
    ArrayList<Order> orderNumberList = new ArrayList<Order>(); // list use to display order list

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basketListView = (ListView)findViewById(R.id.listBasket);
        basketListView.setVisibility(View.GONE);
        basketTotalView = (TextView) findViewById(R.id.displayBasketTotal);
        basketTotalView.setVisibility(View.GONE);
        purchaseView = (ImageView) findViewById(R.id.purchaseButton);
        purchaseView.setVisibility(View.GONE);
        orderListView = (ListView) findViewById(R.id.orderList);

        SetDBManager setDBManager = new SetDBManager(this);
        setDBManager.open();

//        setDBManager.insertSet(new Set("Creator 3in1 Pirate Ship Toy, Pirates’ Inn, Island", "31109", "This awesome, detailed set features a pirate ship with moving sails, cannons and a cabin with opening roof and sides, 3 minifigures, buildable figures including a shark and a parrot, plus lots of other brick-built details and cool accessories to jump-start play.", "€99.99", "https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1.5 1.5x, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=2 2x"));
//        setDBManager.insertSet(new Set("Star Wars Millennium Falcon Starship Building Set", "75257", "bad set", "€169.99", "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,\n" + "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5 1.5x,\n" + "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2 2x"));

        Cursor cursor = setDBManager.getAllSet();

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

        //---------------------------------------------Home page--------------------------------------------------
        // the default list
        listView = (ListView)findViewById(R.id.list);

        SetList list = new SetList(this, setList);
        listView.setAdapter(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            }
        });

        // the search bar view and search event
        searchView = findViewById(R.id.search);
        searchView.setFocusable(true);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    listView.setVisibility(View.GONE);
                    searchListView.setVisibility(View.VISIBLE);
                }else {
                    listView.setVisibility(View.VISIBLE);
                    searchListView.setVisibility(View.GONE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    adapter.getFilter().filter(s); // set the search bar value with input
                }else{
                    searchListView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(""); // reset the search bar value
                    searchView.clearFocus();
                }
                return false;
            }
        });
        //---------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------

        //---------------------------------------------bottom menu-------------------------------------------
        // bottom menu
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) findViewById(i);
                if(rb.getText().equals("Home")) {
                    listView.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    basketListView.setVisibility(View.GONE);
                    basketTotalView.setVisibility(View.GONE);
                    purchaseView.setVisibility(View.GONE);
                    orderListView.setVisibility(View.GONE);
                } else if(rb.getText().equals("Basket")) {
                    listView.setVisibility(View.GONE);
                    searchListView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    displayBasket();
                    displayBasketTotal();
                    basketListView.setVisibility(View.VISIBLE);
                    basketTotalView.setVisibility(View.VISIBLE);
                    purchaseView.setVisibility(View.VISIBLE);
                    orderListView.setVisibility(View.GONE);
                } else if(rb.getText().equals("Order")) {
                    listView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    basketListView.setVisibility(View.GONE);
                    basketTotalView.setVisibility(View.GONE);
                    purchaseView.setVisibility(View.GONE);
                    orderListView.setVisibility(View.GONE);
                    displayOrder();
                    orderListView.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(getApplicationContext(),rb.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(count > 0) {
            displayBasket(); // reload displayBasket
            displayBasketTotal(); //reload displayBasketTotal
        }

        count++; // when the app init, it will run this function once, to avoid call this function from start
    }

    public void displayBasket() {
        basketList.clear(); // clear the list to avoid display product many times

        BasketDBManager basketDBManager = new BasketDBManager(this);
        basketDBManager.open();

        Cursor basketCursor = basketDBManager.getAllBasketItem();

        if (basketCursor.moveToFirst()) {
            do {
                Basket basket = new Basket();
                basket.setSet_name(basketCursor.getString(1));
                basket.setSet_number(basketCursor.getString(2));
                basket.setSet_price(basketCursor.getString(3));
                basket.setSet_image(basketCursor.getString(4));
                basket.setSet_quantity(basketCursor.getString(5));
                basketList.add(basket);
            } while (basketCursor.moveToNext());
        }

        basketDBManager.close();

        basketListView = (ListView)findViewById(R.id.listBasket);

        BasketList basketViewList = new BasketList(this, basketList);
        basketListView.setAdapter(basketViewList);

        basketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DisplayBasketInfo.class);

                Bundle b = new Bundle();
                b.putString("setName", basketList.get(position).getSet_name());
                b.putString("setNumber", basketList.get(position).getSet_number());
                b.putString("setPrice", basketList.get(position).getSet_price());
                b.putString("setQuantity", basketList.get(position).getSet_quantity());
                b.putString("setImage", basketList.get(position).getSet_image());
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }

    public void displayBasketTotal() {
        int quantity = 0;
        float price = 0, itemPrice = 0, sum = 0;
        basketTotalView = (TextView) findViewById(R.id.displayBasketTotal);

        BasketDBManager basketDBManager = new BasketDBManager(this);
        basketDBManager.open();

        Cursor cursor= basketDBManager.getAllBasketItem();

        // Calculate the total price
        if (cursor.moveToFirst()) {
            do {
                price = Float.parseFloat(cursor.getString(3).substring(1));
                quantity = Integer.parseInt(cursor.getString(5));
                itemPrice = price * quantity;
                sum = sum + itemPrice;
            } while (cursor.moveToNext());
        }

        // format the price
        DecimalFormat decimalFormat = new DecimalFormat("00.00");

        // set the total
        basketTotalView.setText("Total: " + "€" + decimalFormat.format(sum));

        basketDBManager.close();
    }

    //------------------------------------------ basket page purchase sets button ---------------------------------------------
    public void purchase(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Purchase")
                .setMessage("Are you sure you want to buy these sets?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Purchase successful", Toast.LENGTH_SHORT).show();

                        int randomNumber;
                        String orderNumber, orderDate;
                        ArrayList<Order> orderList = new ArrayList<Order>();
                        basketTotalView = (TextView) findViewById(R.id.displayBasketTotal);

                        // random number for order number
                        randomNumber = new Random().nextInt(50000);
                        orderNumber = String.valueOf(10000 + randomNumber);

                        // data format for current date
                        SimpleDateFormat sdf = new SimpleDateFormat();
                        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
                        Date date = new Date();
                        orderDate = sdf.format(date);

                        // handle the basket database, after purchase delete all the data from the basket
                        BasketDBManager basketDBManager = new BasketDBManager(getApplicationContext());
                        basketDBManager.open();

                        Cursor cursor= basketDBManager.getAllBasketItem();

                        if (cursor.moveToFirst()) {
                            do {
                                Order order = new Order(orderNumber, orderDate, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5));
                                orderList.add(order);
                            } while (cursor.moveToNext());
                        }

                        // delete the basket data and reload basket and total
                        basketDBManager.deleteBasket();
                        displayBasket();
                        displayBasketTotal();

                        basketDBManager.close();

                        //--------------------------------------add to the order--------------------------------------
                        OrderDBManager orderDBManager = new OrderDBManager(getApplicationContext());
                        orderDBManager.open();

                        for(Order order : orderList) {
                            orderDBManager.insertOrder(order);
                        }

                        orderDBManager.close();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Purchase canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void displayOrder() {
        orderNumberList.clear(); // clear the list to avoid display product many times

        OrderDBManager orderDBManager = new OrderDBManager(this);
        orderDBManager.open();

        Cursor orderCursor = orderDBManager.getAllOrderNumber();

        if (orderCursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrder_number(orderCursor.getString(0));
                order.setOrder_date(orderCursor.getString(1));
                orderNumberList.add(order);
            } while (orderCursor.moveToNext());
        }

        orderCursor.close();

        orderListView = (ListView) findViewById(R.id.orderList);

        OrderList orderList = new OrderList(this, orderNumberList);
        orderListView.setAdapter(orderList);

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DisplayOrderInfo.class);

                Bundle b = new Bundle();
                b.putString("orderNumber", orderNumberList.get(position).getOrder_number());

                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }
}