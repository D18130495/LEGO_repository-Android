package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushun.lego_repo.basketOperate.BasketDBManager;
import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Set;
import com.yushun.lego_repo.utils.LoadImage;

public class DisplaySetInfo extends AppCompatActivity {
    private String setName;
    private String setNumber;
    private String setDesc;
    private String setPrice;
    private String setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_set_info);

        Bundle b = getIntent().getExtras();
        setName = b.getString("setName");
        setNumber = b.getString("setNumber");
        setDesc = b.getString("setDesc");
        setPrice = b.getString("setPrice");
        setImage = b.getString("setImage");

        TextView display_setName = (TextView) findViewById(R.id.displaySetName);
        TextView display_setNumber = (TextView) findViewById(R.id.displaySetNumber);
        TextView display_setDesc = (TextView) findViewById(R.id.displaySetDesc);
        TextView display_setPrice = (TextView) findViewById(R.id.displaySetPrice);
        ImageView display_setImage = (ImageView) findViewById(R.id.displaySetImage);

        display_setName.setText(setName);
        display_setNumber.setText(setNumber);
        display_setDesc.setText(setDesc);
        display_setPrice.setText(setPrice);

        display_setImage.setTag(setImage);

        new LoadImage(display_setImage).execute();
    }

    // use to update basket
    public void updateBasket(View view) {
        BasketDBManager basketDBManager = new BasketDBManager(this);
        basketDBManager.open();

        Cursor cursor= basketDBManager.getBasketItemByNumber(setNumber);

        if(cursor.getCount() == 0) { // if no this set in the database, add one
            basketDBManager.insertBasket(new Basket(setName, setNumber, setPrice, setImage, "1"));
        }else { // if this set in the database, update quantity
            String current = cursor.getString(5);
            Integer newQuantity = Integer.parseInt(current) + 1;

            basketDBManager.updateBasket(setName, setNumber, setPrice, setImage, newQuantity.toString());
        }

        basketDBManager.close();

        this.finish();
    }
}