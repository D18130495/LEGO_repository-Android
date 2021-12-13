package com.yushun.lego_repo.basketOperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Set;
import com.yushun.lego_repo.setsOperate.SetDB;

public class BasketDBManager
{
    Context context;
    private BasketDB basketDB;
    private SQLiteDatabase myDatabase;

    private static String KEY_ROWID;
    private static String KEY_SETNAME;
    private static String KEY_SETNUMBER;
    private static String KEY_SETPRICE;
    private static String KEY_SETIMAGE;
    private static String KEY_SETQUANTITY;
    private static String DATABASE_TABLE;

    public BasketDBManager(Context context)
    {
        this.context = context;
    }

    public BasketDBManager open() throws SQLException {
        basketDB = new BasketDB(context);
        KEY_ROWID = basketDB.getKeyRowid();
        KEY_SETNAME = basketDB.getKeySetname();
        KEY_SETNUMBER = basketDB.getKeySetnumber();
        KEY_SETPRICE = basketDB.getKeySetprice();
        KEY_SETIMAGE = basketDB.getKeySetimage();
        KEY_SETQUANTITY = basketDB.getKeySetquantity();
        DATABASE_TABLE = basketDB.getDatabaseTable();

        myDatabase = basketDB.getWritableDatabase();
        return this;
    }

    //---closes the database--- any activity that uses the dB will need to do this
    public void close()
    {
        basketDB.close();
    }

    //---insert a basket item into the database---
    public long insertBasket(Basket basket)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SETNAME, basket.getSet_name());
        initialValues.put(KEY_SETNUMBER, basket.getSet_number());
        initialValues.put(KEY_SETPRICE, basket.getSet_price());
        initialValues.put(KEY_SETIMAGE, basket.getSet_image());
        initialValues.put(KEY_SETQUANTITY, basket.getSet_quantity());
        return myDatabase.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular basket item---
    public boolean deleteBasketByNumber(String setNumber)
    {
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return myDatabase.delete(DATABASE_TABLE, KEY_SETNUMBER + "=" + setNumber, null) > 0;
    }

    //---deletes all sets in the basket---
    public boolean deleteBasket()
    {
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return myDatabase.delete(DATABASE_TABLE, null, null) > 0;
    }

    //---retrieves all the rows ---
    public Cursor getAllBasketItem()
    {
        return myDatabase.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_SETNAME, KEY_SETNUMBER, KEY_SETPRICE, KEY_SETIMAGE, KEY_SETQUANTITY},
                null, null, null, null, null);
    }

    //---retrieves basket item by number---
    public Cursor getBasketItemByNumber(String setNumber) throws SQLException
    {
        Cursor mCursor = myDatabase.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_SETNAME, KEY_SETNUMBER, KEY_SETPRICE, KEY_SETIMAGE, KEY_SETQUANTITY},
                KEY_SETNUMBER + "=" + setNumber, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a Basket Item---
    public boolean updateBasket(String set_name, String set_number, String set_price, String set_image, String set_quantity)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_SETNAME, set_name);
        args.put(KEY_SETNUMBER, set_number);
        args.put(KEY_SETPRICE, set_price);
        args.put(KEY_SETIMAGE, set_image);
        args.put(KEY_SETQUANTITY, set_quantity);
        return myDatabase.update(DATABASE_TABLE, args, KEY_SETNUMBER + "=" + set_number, null) > 0;
    }
}
