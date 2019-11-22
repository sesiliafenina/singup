package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
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

    //to store the animal images
    private final Integer[] imageIDarray;

    private final String[] timeArray;

    public CustomListAdapter(Activity context, String[] nameArray, String[] infoArray, Integer[] imageIDarray, String[] timeArray) {
        super(context, R.layout.listview_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.infoArray = infoArray;
        this.imageIDarray = imageIDarray;
        this.timeArray = timeArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.nametextViewID);
        TextView timeTextField = rowView.findViewById(R.id.eventTime);
        ImageView imageIDField = rowView.findViewById(R.id.imageView1ID);

        nameTextField.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        timeTextField.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        timeTextField.setText(timeArray[position]);
        imageIDField.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
