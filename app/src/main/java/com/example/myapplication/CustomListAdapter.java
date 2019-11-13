package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends android.widget.ArrayAdapter {

    // reference to the activity
    private final Activity context;

    // to store the list of titles
    private final String[] nameArray;

    // to store the list of information
    private final String[] infoArray;

    public CustomListAdapter(Activity context, String[] nameArray, String[] infoArray) {
        super(context, R.layout.listview_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.infoArray = infoArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.nametextViewID);
        TextView infoTextField = rowView.findViewById(R.id.infotextViewID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);

        return rowView;

    };
}
