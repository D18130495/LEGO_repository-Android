package com.yushun.lego_repo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.yushun.lego_repo.utils.MapAvailable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private ListView listView; // List
    private ListView searchListView; // Search List
    private ListView basketListView; // Basket List
    private ListView orderListView; // Order List
    private SearchView searchView; // Search
    private RadioGroup radioGroup; // bottom menus
    private TextView basketTotalView; // total
    private TextView basketEmptyView; // display basket if empty
    private ImageView purchaseView; // purchase

    private TextView locationView; // the current location
    private TextView localityView; // the current location place
    private Button displayMapView; // use to display map
    private LocationManager locationManager;
    private long minTime = 500;
    private float minDistance = 1;
    private static final int MY_PERMISSION_GPS = 1;
    private Location location;

    private int count = 0;
    private float total = 0;
    private RadioButton rb;

    ArrayList<Set> setList = new ArrayList<Set>(); // list use to display on the home
    ArrayList<Integer> imageList = new ArrayList<Integer>(); // list use to store set image
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
        basketEmptyView = (TextView) findViewById(R.id.displayBasketEmpty);
        basketEmptyView.setVisibility(View.GONE);
        purchaseView = (ImageView) findViewById(R.id.purchaseButton);
        purchaseView.setVisibility(View.GONE);
        orderListView = (ListView) findViewById(R.id.orderList);

        locationView = (TextView) findViewById(R.id.location);
        locationView.setVisibility(View.GONE);
        localityView = (TextView) findViewById(R.id.locality);
        localityView.setVisibility(View.GONE);
        displayMapView = (Button) findViewById(R.id.displayMap);
        displayMapView.setVisibility(View.GONE);
        setUpLocation();

        SetDBManager setDBManager = new SetDBManager(this);
        setDBManager.open();

//        setDBManager.insertSet(new Set("Creator 3in1 Pirate Ship Toy, Pirates’ Inn, Island", "31109", "This awesome, detailed set features a pirate ship with moving sails, cannons and a cabin with opening roof and sides, 3 minifigures, buildable figures including a shark and a parrot, plus lots of other brick-built details and cool accessories to jump-start play.", "€99.99", "https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=1.5 1.5x, https://www.lego.com/cdn/cs/set/assets/blt28eab11b67d4156b/31109.jpg?fit=bounds&amp;format=webply&amp;quality=80&amp;width=800&amp;height=800&amp;dpr=2 2x"));
//        setDBManager.insertSet(new Set("Star Wars Millennium Falcon Starship Building Set", "75257", "The Rise of Skywalker movie featuring Star Wars characters Finn, Chewbacca, Lando Calrissian, Boolio, C-3PO, R2-D2 and D-O. This iconic LEGO Star Wars set also makes a great collectible for any fan.", "€159.99", "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,\n" + "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5 1.5x,\n" + "https://www.lego.com/cdn/cs/set/assets/blt892f38a4fd55edeb/75257.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2 2x"));
//        setDBManager.insertSet(new Set("LEGO® DC Batman™ Batmobile™ Tumbler", "76240", "The ultimate, armored crime-fighting machine. The impressive build-and-display Tumbler from the Dark Knight™ trilogy of Batman movies features an opening roof, which provides access to the driver and passenger seats and control panel.", "€229.99", "https://www.lego.com/cdn/cs/set/assets/blt755a6d05eac6630d/76240.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blt755a6d05eac6630d/76240.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blt755a6d05eac6630d/76240.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Medieval Castle", "31120", "3in1 awesome toys fuel kids’ passions. Creative kids have the chance to build 3 different models with this LEGO Creator 3in1 set. They can start with the Medieval Castle, rebuild it into a castle tower with a catapult or transform it into a bustling marketplace with a water mill, tower and prison.", "€99.99", "https://www.lego.com/cdn/cs/set/assets/blt8979ea9bbd9b87e5/31120.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blt8979ea9bbd9b87e5/31120.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blt8979ea9bbd9b87e5/31120.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Taj Mahal", "21056", "Whether you have been lucky enough to visit the Taj Mahal yourself and want a special souvenir of the experience, dream of visiting one day or just appreciate elegant buildings, this LEGO® Architecture Landmarks Collection set (21056) is ideal for you.", "€119.99", "https://www.lego.com/cdn/cs/set/assets/blt10cbc9ffc0459599/21056.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blt10cbc9ffc0459599/21056.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blt10cbc9ffc0459599/21056.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Pirates of Barracuda Bay", "21322", "Rekindle nostalgic memories of childhood LEGO® construction projects with this LEGO Ideas Pirates of Barracuda Bay (21322) shipwreck island model for display and play. Enjoy some calm, quality time alone building – or share the fun with others.", "€199.99", "https://www.lego.com/cdn/cs/set/assets/blt7215c9cdc448ad23/21322_alt3.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blt7215c9cdc448ad23/21322_alt3.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blt7215c9cdc448ad23/21322_alt3.png?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Mos Eisley Cantina™", "75290", "Enjoy quality me-time and relive iconic Star Wars: A New Hope moments with the awesome LEGO® Star Wars™ Mos Eisley Cantina (75290) construction model for display.", "€349.99", "https://www.lego.com/cdn/cs/set/assets/bltb4d7806b805a25ef/75290.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/bltb4d7806b805a25ef/75290.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/bltb4d7806b805a25ef/75290.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Water Dragon", "71754", "Incredible stories from the NINJAGO®: Seabound TV series can be played out by kids with this highly detailed Water Dragon (71754) playset. The impressive dragon toy has a posable head, jaw, legs, neck, wings and a moving tail that can be used as a weapon, to help fuel hours of enriching play for youngsters.", "€69.99", "https://www.lego.com/cdn/cs/set/assets/blt7ff5ea7f5a6f9558/71754.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blt7ff5ea7f5a6f9558/71754.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blt7ff5ea7f5a6f9558/71754.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));
//        setDBManager.insertSet(new Set("Skull Sorcerer's Dragon", "71721", "Amazing playset for board game adventures. NINJAGO® fans can build and play with the Skull Sorcerer’s Dragon (71721). This dragon building kit features 6 buildable figures for captivating creative play.", "€79.99", "https://www.lego.com/cdn/cs/set/assets/blte68440311f004bee/71721.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1,%20https://www.lego.com/cdn/cs/set/assets/blte68440311f004bee/71721.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=1.5%201.5x,%20https://www.lego.com/cdn/cs/set/assets/blte68440311f004bee/71721.jpg?fit=bounds&format=webply&quality=80&width=800&height=800&dpr=2%202x"));

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

        //---------------------------------------------set image--------------------------------------------------
        imageList.add(R.drawable.toozn);
        imageList.add(R.drawable.sftfs);
        imageList.add(R.drawable.sstfz);
        imageList.add(R.drawable.tootz);
        imageList.add(R.drawable.tozfs);
        imageList.add(R.drawable.tottt);
        imageList.add(R.drawable.sftnz);
        imageList.add(R.drawable.sosff);
        imageList.add(R.drawable.sostz);

        //---------------------------------------------Home page--------------------------------------------------
        // the default list
        listView = (ListView)findViewById(R.id.list);

        SetList list = new SetList(this, setList, imageList);
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

        rb = (RadioButton) findViewById(R.id.home_menu); // set default option

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb = (RadioButton) findViewById(i);
                if(rb.getText().equals("Home")) {
                    listView.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    basketListView.setVisibility(View.GONE);
                    basketTotalView.setVisibility(View.GONE);
                    purchaseView.setVisibility(View.GONE);
                    orderListView.setVisibility(View.GONE);
                    basketEmptyView.setVisibility(View.GONE);
                    locationView.setVisibility(View.GONE);
                    localityView.setVisibility(View.GONE);
                    displayMapView.setVisibility(View.GONE);
                } else if(rb.getText().equals("Basket")) {
                    listView.setVisibility(View.GONE);
                    searchListView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    displayBasket();
                    displayBasketTotal();
                    basketListView.setVisibility(View.VISIBLE);
                    if((int)total == 0) {
                        basketTotalView.setVisibility(View.GONE);
                        purchaseView.setVisibility(View.GONE);
                        basketEmptyView.setVisibility(View.VISIBLE);
                    }else {
                        basketTotalView.setVisibility(View.VISIBLE);
                        purchaseView.setVisibility(View.VISIBLE);
                        basketEmptyView.setVisibility(View.GONE);
                    }
                    orderListView.setVisibility(View.GONE);
                    locationView.setVisibility(View.GONE);
                    localityView.setVisibility(View.GONE);
                    displayMapView.setVisibility(View.GONE);
                } else if(rb.getText().equals("Order")) {
                    listView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    basketListView.setVisibility(View.GONE);
                    basketTotalView.setVisibility(View.GONE);
                    purchaseView.setVisibility(View.GONE);
                    orderListView.setVisibility(View.GONE);
                    displayOrder();
                    orderListView.setVisibility(View.VISIBLE);
                    basketEmptyView.setVisibility(View.GONE);
                    locationView.setVisibility(View.GONE);
                    localityView.setVisibility(View.GONE);
                    displayMapView.setVisibility(View.GONE);
                } else if(rb.getText().equals("Find store")) {
                    listView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    basketListView.setVisibility(View.GONE);
                    basketTotalView.setVisibility(View.GONE);
                    purchaseView.setVisibility(View.GONE);
                    orderListView.setVisibility(View.GONE);
                    basketEmptyView.setVisibility(View.GONE);
                    locationView.setVisibility(View.VISIBLE);
                    localityView.setVisibility(View.VISIBLE);
                    displayMapView.setVisibility(View.VISIBLE);
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
        float price = 0, itemPrice = 0;
        total = 0;
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
                total = total + itemPrice;
            } while (cursor.moveToNext());
        }

        // format the price
        DecimalFormat decimalFormat = new DecimalFormat("00.00");

        // set the total
        basketTotalView.setText("Total: " + "€" + decimalFormat.format(total));

        basketDBManager.close();

        if(cursor.getCount() == 0) {
            basketTotalView.setVisibility(View.GONE);
            purchaseView.setVisibility(View.GONE);
            basketEmptyView.setVisibility(View.VISIBLE);
        }else {
            basketEmptyView.setVisibility(View.GONE);
        }

        if(rb.getText().equals("Home")) basketEmptyView.setVisibility(View.GONE);
        if(rb.getText().equals("Order")) basketEmptyView.setVisibility(View.GONE);
        if(rb.getText().equals("Find store")) basketEmptyView.setVisibility(View.GONE);
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
                b.putString("orderDate", orderNumberList.get(position).getOrder_date());

                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }

    //--------------------------------------------------use for GPS----------------------------------------------
    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_GPS);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String latestLocation = "";
        this.location = location;

        if (location != null) {
            latestLocation = String.format(
                    "Latitude: %1$s Longitude: %2$s",
                    Math.round(location.getLatitude()), Math.round(location.getLongitude()));
        }
        locationView.setText("Latitude and longitude of your current location:" + "\n" + latestLocation);

        try {
            Geocoder geo = new Geocoder(MainActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                localityView.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getFeatureName() + ", " +
                            addresses.get(0).getLocality() + ", " +
                            addresses.get(0).getAdminArea() + ", " +
                            addresses.get(0).getCountryName();
                    localityView.setText("Current location:" + "\n" + address);
                }
            }
        } catch (Exception e) {
        }
    }

    public void displayMap(View view) {
//        MapAvailable mapAvailable = new MapAvailable();
//        if(mapAvailable.isAvailable(this, "com.google.android.apps.maps")) {
//            System.out.println("123");
//        }else {
//            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        }

        Uri currentLocation = Uri.parse("geo:" + location.getLatitude()+ "," + location.getLongitude() + "?q=" + Uri.encode("Lego"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, currentLocation);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}