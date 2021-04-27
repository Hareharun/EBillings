package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView billId,billType,transactionDate;
    private CardView homeneeds,food,vegetables,shopping,medicine,accomodation;
    private RecyclerView billList;
    private DatabaseReference databaseReference;
    private BillAdapter adapter;
    private String phone;
    private ImageButton logout;
    private CardView billcard;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phone =getIntent().getStringExtra("Phone");
        logout = findViewById(R.id.logout);
        homeneeds = findViewById(R.id.grocery);
        food = findViewById(R.id.food);
        vegetables = findViewById(R.id.vegetables);
        shopping = findViewById(R.id.shopping);
        medicine = findViewById(R.id.medicine);
        accomodation = findViewById(R.id.accomodation);
        homeneeds.setOnClickListener(this);
        food.setOnClickListener(this);
        vegetables.setOnClickListener(this);
        //Logout button click listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
                sessionManagement.removeSession();
                moveToLoginActivity();
            }
        });

        //Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void moveToLoginActivity() {
        Intent toLogin = new Intent(HomeActivity.this, LoginActivity.class);
        toLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLogin);
    }

    private void movetoGroceryBills(String phone) {
        Intent groceryBills = new Intent(HomeActivity.this, com.example.ebillings.groceryBills.class);
        groceryBills.putExtra("Phone",phone);
        startActivity(groceryBills);
    }

    private void movetoFoodBills(String phone) {
        Intent foodBills = new Intent(HomeActivity.this, com.example.ebillings.foodBills.class);
        foodBills.putExtra("Phone",phone);
        startActivity(foodBills);
    }

    private void movetoVegetablebills(String phone) {
        Intent vegetableBills = new Intent(HomeActivity.this, com.example.ebillings.vegetableBills.class);
        vegetableBills.putExtra("Phone",phone);
        startActivity(vegetableBills);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grocery:
                movetoGroceryBills(phone);
                break;
            case R.id.vegetables:
                movetoVegetablebills(phone);
                break;
            case R.id.food:
                movetoFoodBills(phone);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_homeneeds:
                movetoGroceryBills(phone);
                break;
            case R.id.nav_food:
                break;
            case R.id.nav_accomodation:
                break;
            case R.id.nav_financeAnalysis:
                Intent intent = new Intent(HomeActivity.this,financeAnalysis.class);
                intent.putExtra("Phone",phone);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
