package com.yushun.lego_repo.orderOperate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDB extends SQLiteOpenHelper
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ORDERNUMBER = "order_number";
    public static final String KEY_ORDERDATE = "order_date";
    public static final String KEY_SETNAME = "set_name";
    public static final String KEY_SETNUMBER = "set_number";
    public static final String KEY_SETPRICE = "set_price";
    public static final String KEY_SETQUANTITY = "set_quantity";

    public static final String DATABASE_NAME = "lego_repo";
    public static final String DATABASE_TABLE = "orders";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE  +
            " (_id integer primary key autoincrement, " +
            "order_number text not null," +
            "order_date text not null," +
            "set_name text not null, " +
            "set_number text not null, " +
            "set_price text not null, " +
            "set_quantity text not null);";

    public OrderDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public static String getKeyRowid() {
        return KEY_ROWID;
    }

    public static String getKeySetname() {
        return KEY_SETNAME;
    }

    public static String getKeySetnumber() {
        return KEY_SETNUMBER;
    }

    public static String getKeySetprice() {
        return KEY_SETPRICE;
    }

    public static String getDatabaseTable() {
        return DATABASE_TABLE;
    }

    public static String getKeySetquantity() {
        return KEY_SETQUANTITY;
    }

    public static String getKeyOrdernumber() {
        return KEY_ORDERNUMBER;
    }

    public static String getKeyOrderdate() {
        return KEY_ORDERDATE;
    }
}

