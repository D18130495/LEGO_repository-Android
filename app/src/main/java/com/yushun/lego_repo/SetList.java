package com.yushun.lego_repo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushun.lego_repo.utils.LoadImage;

import java.util.ArrayList;

public class SetList extends ArrayAdapter {
    private ArrayList<Set> setList;
    private Activity context;

    public SetList(Activity context, ArrayList<Set> setList) {
        super(context, R.layout.row_item, setList);
        this.context = context;
        this.setList = setList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);

        TextView textViewName = (TextView) row.findViewById(R.id.textViewName);
        TextView textViewNumber = (TextView) row.findViewById(R.id.textViewNumber);
        ImageView imageViewImage = (ImageView) row.findViewById(R.id.imageViewImage);

        textViewName.setText(setList.get(position).getSet_name());
        textViewNumber.setText(setList.get(position).getSet_number());
        imageViewImage.setTag(setList.get(position).getSet_image());

        new LoadImage(imageViewImage).execute();

        return row;
    }
}