package com.oliek.cartrout.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.oliek.cartrout.model.CategoryModel;

import java.util.ArrayList;


public class CategorySpinnerAdapter extends ArrayAdapter<CategoryModel> {

    private final Context context;
    private final ArrayList<CategoryModel> list;

    public CategorySpinnerAdapter(Context context, int textViewResourceId, ArrayList<CategoryModel> list) {
        super(context,  textViewResourceId, list);
        this.context=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CategoryModel getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //view = inflter.inflate(R.layout.spinner_list_taxi, null);
        //TextView names = (TextView) view.findViewById(R.id.taxi_view);
        TextView label = new TextView(context);
        label.setText(list.get(i).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setPadding(15,30,15,30);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);


        label.setText(list.get(position).getName());

        return label;
    }

}
