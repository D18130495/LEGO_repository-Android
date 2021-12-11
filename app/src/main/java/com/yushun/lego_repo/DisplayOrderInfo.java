package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushun.lego_repo.basketOperate.BasketList;
import com.yushun.lego_repo.orderOperate.OrderDBManager;
import com.yushun.lego_repo.orderOperate.OrderItemList;
import com.yushun.lego_repo.pojo.Order;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DisplayOrderInfo extends AppCompatActivity {
    private String orderNumber;
    private String orderDate;
    ArrayList<Order> orderList = new ArrayList<Order>(); // list use to display order item list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order_info);

        orderList.clear(); // clear the list to avoid display product many times

        int quantity = 0;
        float price = 0, itemPrice = 0, sum = 0;

        Bundle b = getIntent().getExtras();
        orderNumber = b.getString("orderNumber");
        orderDate = b.getString("orderDate");
        System.out.println(orderNumber);
        OrderDBManager orderDBManager = new OrderDBManager(this);
        orderDBManager.open();

        Cursor orderCursor = orderDBManager.getItemByOrderNumber(orderNumber);

        if (orderCursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setSet_name(orderCursor.getString(1));
                order.setSet_number(orderCursor.getString(2));
                order.setSet_price(orderCursor.getString(3));
                order.setSet_quantity(orderCursor.getString(4));
                price = Float.parseFloat(orderCursor.getString(3).substring(1));
                quantity = Integer.parseInt(orderCursor.getString(4));
                itemPrice = price * quantity;
                sum = sum + itemPrice;
                orderList.add(order);
            } while (orderCursor.moveToNext());
        }

        orderDBManager.close();

        ListView orderItemView = (ListView)findViewById(R.id.orderItemList);

        OrderItemList orderItemViewList = new OrderItemList(this, orderList);
        orderItemView.setAdapter(orderItemViewList);

        TextView textViewOrderTotal = (TextView) findViewById(R.id.textViewOrderTotal);

        DecimalFormat decimalFormat = new DecimalFormat("00.00");

        textViewOrderTotal.setText("Total: " + "€" + decimalFormat.format(sum));
    }

    // use for add the order to the calender
    public void addCalender(View view) {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(orderDate.substring(0, 4));
        int month = Integer.parseInt(orderDate.substring(5, 7)) - 1;
        int date = Integer.parseInt(orderDate.substring(8, 10));
        int hour = Integer.parseInt(orderDate.substring(11, 13));
        int minute = Integer.parseInt(orderDate.substring(14, 16));
        System.out.println(year);
        calendar.set(year, month, date, hour, minute);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
        calendar.set(year, month, date + 5, hour, minute);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Order Number: " + orderNumber);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home");
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Purchased Lego sets");
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, CalendarContract.EXTRA_EVENT_ALL_DAY);
        startActivity(calendarIntent);
    }
}