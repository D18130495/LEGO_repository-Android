package com.yushun.lego_repo.orderOperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yushun.lego_repo.basketOperate.BasketDB;
import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Order;

public class OrderDBManager
{
    Context context;
    private OrderDB orderDB;
    private SQLiteDatabase myDatabase;

    private static String KEY_ROWID;
    private static String KEY_ORDERNUMBER;
    private static String KEY_ORDERDATE;
    private static String KEY_SETNAME;
    private static String KEY_SETNUMBER;
    private static String KEY_SETPRICE;
    private static String KEY_SETQUANTITY;
    private static String DATABASE_TABLE;

    public OrderDBManager(Context context)
    {
        this.context = context;
    }

    public OrderDBManager open() throws SQLException {
        orderDB = new OrderDB(context);
        KEY_ROWID = orderDB.getKeyRowid();
        KEY_ORDERNUMBER = orderDB.getKeyOrdernumber();
        KEY_ORDERDATE = orderDB.getKeyOrderdate();
        KEY_SETNAME = orderDB.getKeySetname();
        KEY_SETNUMBER = orderDB.getKeySetnumber();
        KEY_SETPRICE = orderDB.getKeySetprice();
        KEY_SETQUANTITY = orderDB.getKeySetquantity();
        DATABASE_TABLE = orderDB.getDatabaseTable();

        myDatabase = orderDB.getWritableDatabase();
        return this;
    }

    //---closes the database--- any activity that uses the dB will need to do this
    public void close()
    {
        orderDB.close();
    }

    //---insert a order item into the database---
    public long insertOrder(Order order)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ORDERNUMBER, order.getOrder_number());
        initialValues.put(KEY_ORDERDATE, order.getOrder_date());
        initialValues.put(KEY_SETNAME, order.getSet_name());
        initialValues.put(KEY_SETNUMBER, order.getSet_number());
        initialValues.put(KEY_SETPRICE, order.getSet_price());
        initialValues.put(KEY_SETQUANTITY, order.getSet_quantity());
        return myDatabase.insert(DATABASE_TABLE, null, initialValues);
    }

    //---retrieves all the rows ---
    public Cursor getAllOrderNumber()
    {
        return myDatabase.query(true, DATABASE_TABLE, new String[] {KEY_ORDERNUMBER, KEY_ORDERDATE},
                null, null, null, null, null, null);
    }

    //---retrieves a particular order number---
    public Cursor getItemByOrderNumber(String orderNumber) throws SQLException
    {
        return myDatabase.query(DATABASE_TABLE, new String[] {KEY_ORDERNUMBER, KEY_SETNAME, KEY_SETNUMBER, KEY_SETPRICE, KEY_SETQUANTITY},
                KEY_ORDERNUMBER + "=" + orderNumber, null, null, null, null, null);
    }
}
