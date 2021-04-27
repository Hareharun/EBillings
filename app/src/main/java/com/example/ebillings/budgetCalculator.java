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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import weka.associations.Apriori;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static weka.core.converters.ConverterUtils.*;

public class budgetCalculator extends AppCompatActivity {

    private TextView grocery;
    private EditText income;
    private int gorceryTotal = 0;
    private Button showChart;
    private DatabaseReference databaseReference;
    private String phone,groceryTotall,Income;
    private DataSource dataSource;
    Instance data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_calculator);
        String dataset = "https://storm.cis.fordham.edu/~gweiss/data-mining/weka-data/supermarket.arff";
        try {
            dataSource = new DataSource(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            data = (Instance) dataSource.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Apriori model = new Apriori();
        try {
            model.buildAssociations((Instances) data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Output:", String.valueOf(model));
    }
}