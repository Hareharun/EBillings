package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class financeAnalysis extends AppCompatActivity {
    private TextView grocery;
    private EditText income;
    private int gorceryTotal = 0;
    private Button showChart;
    private DatabaseReference databaseReference;
    private String phone,groceryTotall,Income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_analysis);
        grocery = findViewById(R.id.groceryTotal);
        showChart = findViewById(R.id.show_chart);
        income = findViewById(R.id.income);
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");
        Query query = databaseReference.orderByChild("Phone").equalTo("9597785902");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot amountSnapshot: dataSnapshot.getChildren()){
                    if(String.valueOf(amountSnapshot.child("type").getValue()).equals("grocery")){
                        gorceryTotal  += Integer.parseInt(String.valueOf(amountSnapshot.child("Amount").getValue()));
                    }
                }
                Log.i("Amount:", String.valueOf(gorceryTotal));
                groceryTotall = String.valueOf(gorceryTotal);
                grocery.setText("Grocery: \u20B9" + String.valueOf(gorceryTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error", "onCancelled", databaseError.toException());
            }
        });
        //textView.setText(gorceryTotal);

        showChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FA_Chart = new Intent(financeAnalysis.this,FinancialAnalysisChart.class);
                Income = income.getText().toString().trim();
                FA_Chart.putExtra("groceryTotall",groceryTotall);
                FA_Chart.putExtra("income",Income);
                startActivity(FA_Chart);
            }
        });
    }
}