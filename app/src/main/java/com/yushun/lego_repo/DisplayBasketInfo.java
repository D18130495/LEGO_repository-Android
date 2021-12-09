package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushun.lego_repo.basketOperate.BasketDBManager;
import com.yushun.lego_repo.utils.LoadImage;

public class DisplayBasketInfo extends AppCompatActivity {
    private String setName;
    private String setNumber;
    private String setPrice;
    private String setQuantity;
    private String setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_basket_info);

        Bundle b = getIntent().getExtras();
        setName = b.getString("setName");
        setNumber = b.getString("setNumber");
        setPrice = b.getString("setPrice");
        setQuantity = b.getString("setQuantity");
        setImage = b.getString("setImage");

        TextView display_basketName = (TextView) findViewById(R.id.displayBasketName);
        TextView display_basketNumber = (TextView) findViewById(R.id.displayBasketNumber);
        TextView display_basketPrice = (TextView) findViewById(R.id.displayBasketPrice);
        TextView display_basketQuantity = (TextView) findViewById(R.id.displayBasketQuantity);
        ImageView display_basketImage = (ImageView) findViewById(R.id.displayBasketImage);

        display_basketName.setText(setName);
        display_basketNumber.setText(setNumber);
        display_basketPrice.setText(setPrice);
        display_basketQuantity.setText(setQuantity);

        display_basketImage.setTag(setImage);

        new LoadImage(display_basketImage).execute();
    }

    public void removeBasket(View view) {
        BasketDBManager basketDBManager = new BasketDBManager(this);
        basketDBManager.open();

        Cursor cursor= basketDBManager.getBasketItemByNumber(setNumber);
        String current = cursor.getString(5);
        Integer newQuantity = Integer.parseInt(current) - 1;

        if(newQuantity == 0) {
            basketDBManager.deleteBasketByNumber(setNumber);
            Toast.makeText(getApplicationContext(), "Set has been removed from basket", Toast.LENGTH_SHORT).show();
            this.finish();
            basketDBManager.close();
        }else {
            basketDBManager.updateBasket(setName, setNumber, setPrice, setImage, newQuantity.toString());

            basketDBManager.close();

            TextView display_basketQuantity = (TextView) findViewById(R.id.displayBasketQuantity);
            display_basketQuantity.setText(newQuantity.toString());
        }
    }

    public void addBasket(View view) {
        BasketDBManager basketDBManager = new BasketDBManager(this);
        basketDBManager.open();

        Cursor cursor= basketDBManager.getBasketItemByNumber(setNumber);
        String current = cursor.getString(5);

        if(Integer.parseInt(current) == 9) {
            Toast.makeText(getApplicationContext(), "You can only add maximum of nine", Toast.LENGTH_SHORT).show();
            this.finish();
            basketDBManager.close();
        }else {
            Integer newQuantity = Integer.parseInt(current) + 1;
            basketDBManager.updateBasket(setName, setNumber, setPrice, setImage, newQuantity.toString());

            basketDBManager.close();

            TextView display_basketQuantity = (TextView) findViewById(R.id.displayBasketQuantity);
            display_basketQuantity.setText(newQuantity.toString());
        }
    }
}