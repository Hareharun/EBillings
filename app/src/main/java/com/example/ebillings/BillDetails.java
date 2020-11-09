package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillDetails extends AppCompatActivity {

    private  String totalAmount,billId;
    private DatabaseReference databaseReference;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        //Assigning BillID from the previous intent
        billId = getIntent().getStringExtra("billID");
        //Getting listview
        listView = findViewById(R.id.listview);
        //Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");
        databaseReference.child(billId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                totalAmount = bill.getAmount();
                Log.i("TOTALAMOUNT", totalAmount);
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
    }
}