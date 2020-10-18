package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebillings.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login_btn;
    TextView singup_txt;
    String Phone;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = email.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                if (email.getText().toString().isEmpty()){
                    showWarningDialog("Please Fill Phone Number!");
                    //Toast.makeText(LoginActivity.this, "Please fill email!", Toast.LENGTH_SHORT).show();
                }else {
                    if ( password.getText().toString().isEmpty()){
                        showWarningDialog("Please Fill Password!");
                        //Toast.makeText(LoginActivity.this, "Please fill password!", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.setMessage("Please Wait....");
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        tabel_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(email.getText().toString()).exists()) {
                                    progressDialog.dismiss();
                                    User user = dataSnapshot.child(email.getText().toString()).getValue(User.class);
                                    Phone = email.getText().toString();

                                    if (user.getPassword().equals(password.getText().toString())) {
                                        //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                                        toHome.putExtra("Phone", phone);
                                        startActivity(toHome);
                                    } else {
                                        showErrorDialog("Password Incorrect!");
                                        //Toast.makeText(LoginActivity.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    showErrorDialog("User Doesn't Exist!");
                                    //Toast.makeText(LoginActivity.this, "User Doesnt exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });
                    }
                }
            }
        });

    }

    private  void showWarningDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.AlerDialogTheme);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_warning_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.warning_text));
        ((TextView) view.findViewById(R.id.textMessage)).setText(text);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.button_ok));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private  void showErrorDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.AlerDialogTheme);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_error_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.error_text));
        ((TextView) view.findViewById(R.id.textMessage)).setText(text);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.button_ok));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_ierror);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
