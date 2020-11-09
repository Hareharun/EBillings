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

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView billId,billType,transactionDate;
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
        logout = findViewById(R.id.toolbar);
        billcard = findViewById(R.id.BillCard);
        billId = findViewById(R.id.type);
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

            adapter = new BillAdapter(options, HomeActivity.this);
            billList.setAdapter(adapter);

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
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void moveToLoginActivity() {
        Intent toLogin = new Intent(HomeActivity.this, LoginActivity.class);
        toLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        adapter.startListening();
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
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}
