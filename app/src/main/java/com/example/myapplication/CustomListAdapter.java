package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends android.widget.ArrayAdapter {

    // reference to the activity
    private final Activity context;

    // to store the list of titles
    private final List<String> nameArray;

    // to store the list of information
    private final List<String> infoArray;

    //to store the animal images
    private final List<Bitmap> imageIDarray;

    private final List<String> timeArray;

    public CustomListAdapter(Activity context, List<String> nameArray, List<String> infoArray, List<Bitmap> imageIDarray, List<String> timeArray) {
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
        nameTextField.setText(nameArray.get(position));
        timeTextField.setText(timeArray.get(position));
        imageIDField.setImageBitmap(imageIDarray.get(position)); // changed this to bitmap

        return rowView;

    };
}
