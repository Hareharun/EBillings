package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView billId,billType,transactionDate;
    private RecyclerView billList;
    private DatabaseReference databaseReference;
    private BillAdapter adapter;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phone =getIntent().getStringExtra("Phone");
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");
        //Toast.makeText(HomeActivity.this,phone,Toast.LENGTH_SHORT).show();
        Query query = databaseReference.orderByChild("Phone").equalTo(phone);
        databaseReference.keepSynced(true);
        billList = findViewById(R.id.recycler_view);
        billList.setHasFixedSize(true);
        billList.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<Bill> options =
                    new FirebaseRecyclerOptions.Builder<Bill>()
                            .setQuery(query, Bill.class)
                            .build();

            adapter = new BillAdapter(options);
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
