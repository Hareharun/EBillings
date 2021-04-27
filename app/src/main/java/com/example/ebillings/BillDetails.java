package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebillings.Model.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BillDetails extends AppCompatActivity implements View.OnClickListener {

    private  String totalAmount,billId,billDate;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ImageView homeButton,backbutton;
    private TextView billID, totalamount, invoiceDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        //Assigning BillID and BillDate from the previous intent
        billId = getIntent().getStringExtra("billID");
        billDate = getIntent().getStringExtra("billDate");
        //Getting listview
        listView = findViewById(R.id.listview);
        homeButton = findViewById(R.id.homeButton);
        backbutton = findViewById(R.id.backButton);
        homeButton.setVisibility(View.VISIBLE);
        billID = findViewById(R.id.invoice_id);
        invoiceDate = findViewById(R.id.invoice_date);
        totalamount = findViewById(R.id.totalAmount);
        billID.setText("Bill ID: " + billId);
        invoiceDate.setText("Date: " + billDate);
        //Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");
        databaseReference.child(billId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                totalAmount = bill.getAmount();
                //Log.i("TOTALAMOUNT", totalAmount);
                totalamount.setText("Rs " + totalAmount);
                ArrayList<BillDetailsModel> billdetails = new ArrayList<>();
                for(DataSnapshot billSnapshot : dataSnapshot.child("Bill").getChildren()){
                    BillDetailsModel billDetailsModel = billSnapshot.getValue(BillDetailsModel.class);
                    billdetails.add(billDetailsModel);
                }
                BillDetailsAdapter adapter = new BillDetailsAdapter(BillDetails.this, R.layout.listview_row, billdetails);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        homeButton.setOnClickListener(this);
        backbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeButton:
                Intent homeActivity = new Intent(BillDetails.this, HomeActivity.class);
                homeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                BillDetails.this.startActivity(homeActivity);
                BillDetails.this.finish();
                break;
            case R.id.backButton:
                BillDetails.this.finish();
                break;
        }
    }
}