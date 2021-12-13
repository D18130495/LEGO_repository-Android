package com.yushun.lego_repo.setsOperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yushun.lego_repo.pojo.Set;

public class SetDBManager
{
    Context context;
    private SetDB setDB;
    private SQLiteDatabase myDatabase;

    private static String KEY_ROWID;
    private static String KEY_SETNAME;
    private static String KEY_SETNUMBER;
    private static String KEY_SETDESC;
    private static String KEY_SETPRICE;
    private static String KEY_SETIMAGE;
    private static String DATABASE_TABLE;

    public SetDBManager(Context context)
    {
        this.context = context;
    }

    public SetDBManager open() throws SQLException {
        setDB = new SetDB(context);
        KEY_ROWID = setDB.getKeyRowid();
        KEY_SETNAME = setDB.getKeySetname();
        KEY_SETNUMBER = setDB.getKeySetnumber();
        KEY_SETDESC = setDB.getKeySetdesc();
        KEY_SETPRICE = setDB.getKeySetprice();
        KEY_SETIMAGE = setDB.getKeySetimage();
        DATABASE_TABLE = setDB.getDatabaseTable();

        myDatabase = setDB.getWritableDatabase();
        return this;
    }

    //---closes the database--- any activity that uses the dB will need to do this
    public void close()
    {
        setDB.close();
    }

    //---insert a task into the database---
    public long insertSet(Set set)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SETNAME, set.getSet_name());
        initialValues.put(KEY_SETNUMBER, set.getSet_number());
        initialValues.put(KEY_SETDESC, set.getSet_description());
        initialValues.put(KEY_SETPRICE, set.getSet_price());
        initialValues.put(KEY_SETIMAGE, set.getSet_image());
        return myDatabase.insert(DATABASE_TABLE, null, initialValues);
    }

    //---retrieves all the rows ---
    public Cursor getAllSet()
    {
        return myDatabase.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_SETNAME, KEY_SETNUMBER, KEY_SETDESC, KEY_SETPRICE, KEY_SETIMAGE},
                null, null, null, null, null);
    }

    //---retrieves the set by set number ---
    public Cursor getSetByNumber(String setNumber) throws SQLException
    {
        Cursor mCursor = myDatabase.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_SETNAME, KEY_SETNUMBER, KEY_SETDESC, KEY_SETPRICE, KEY_SETIMAGE},
                KEY_SETNUMBER + "=" + setNumber, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
