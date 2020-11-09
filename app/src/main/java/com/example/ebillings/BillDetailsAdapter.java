package com.example.ebillings;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BillDetailsAdapter extends ArrayAdapter<BillDetailsModel> {

    private static final String TAG = "BillDetailsAdapter";
    private Context context;
    int resource;

    public BillDetailsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BillDetailsModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView product, quantity, total, mrp;
        String Product = getItem(position).getProduct();
        String Quantity = getItem(position).getQuantity();
        String Total = getItem(position).getTotal();
        String MRP = getItem(position).getMRP();

        BillDetailsModel bill = new BillDetailsModel(Product, Quantity, Total, MRP);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        convertView.setBackgroundColor(Color.TRANSPARENT);
        product = convertView.findViewById(R.id.product);
        quantity = convertView.findViewById(R.id.quantity);
        total = convertView.findViewById(R.id.total);
        mrp = convertView.findViewById(R.id.mrp);
        product.setText(Product);
        quantity.setText(Quantity);
        total.setText(Total);
        mrp.setText(MRP);

        return convertView;
    }
}
