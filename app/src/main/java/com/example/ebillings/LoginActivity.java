package com.example.ebillings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private EditText email,password;
    private Button login_btn;
    private TextView singup_txt;
    private static String Phone;
    private TextView register;
    private TextView forgotPassword;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences phoneNumber = getSharedPreferences("PHONE_NUMBER", HomeActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor =  phoneNumber.edit();
        editor.putString("PHONE", Phone);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email           = findViewById(R.id.email);
        password        = findViewById(R.id.password);
        login_btn       = findViewById(R.id.login_btn);
        forgotPassword  = findViewById(R.id.forgotPassword);
        register        = findViewById(R.id.register);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        //Go to Register User Page
        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent toRegisterUser = new Intent(LoginActivity.this, registerUser.class);
                startActivity(toRegisterUser);
                return false;
            }
        });

        //Handle login logic
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
                                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                                        sessionManagement.saveSession(user);
                                        moveToHomeActivity(Phone);
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

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String isLoggedIn = sessionManagement.getSession();
        SharedPreferences phoneNumber = getSharedPreferences("PHONE_NUMBER", HomeActivity.MODE_PRIVATE);
        Phone = phoneNumber.getString("PHONE", null);
        if(isLoggedIn != null){
            moveToHomeActivity(Phone);
        }
    }

    private void moveToHomeActivity(String phone) {
        Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
        toHome.putExtra("Phone", phone);
        toHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toHome);
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
