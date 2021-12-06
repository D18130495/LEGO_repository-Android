package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushun.lego_repo.utils.LoadImage;

public class DisplaySetInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_set_info);

        Bundle b = getIntent().getExtras();
        String setName = b.getString("setName");
        String setNumber = b.getString("setNumber");
        String setDesc = b.getString("setDesc");
        String setPrice = b.getString("setPrice");
        String setImage = b.getString("setImage");

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
}