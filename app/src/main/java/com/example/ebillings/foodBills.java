package com.example.ebillings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ebillings.Model.Bill;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class foodBills extends AppCompatActivity {
    private String phone;
    private TextView billId;
    private DatabaseReference databaseReference;
    private RecyclerView billList;
    private BillAdapter adapter;
    private CardView billcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bills);
        phone = getIntent().getStringExtra("Phone");
        billcard = findViewById(R.id.BillCard);
        billId = findViewById(R.id.type);
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");
        Query query = databaseReference.orderByChild("type").equalTo("food");
        Query groceryQuery = databaseReference.orderByChild("type").equalTo("grocery");
        databaseReference.keepSynced(true);
        billList = findViewById(R.id.recycler_view1);
        billList.setHasFixedSize(true);
        billList.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Bill> options =
                new FirebaseRecyclerOptions.Builder<Bill>()
                        .setQuery(query, Bill.class)
                        .build();
        adapter = new BillAdapter(options, foodBills.this, phone);
        billList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}