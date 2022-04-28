package com.example.namaprojectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private String users;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ProgressBar progressBar;
    public String nameFromDB;
//    ActivityReadDataBinding binding;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        binding = ActivityReadDataBinding.inflate(getLayoutInflater());
        signIn = (Button) findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById (R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById (R.id.editTextTextPassword);
//        progressBar = (ProgressBar) findViewById (R.id.progressBar);



        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query currentUser = databaseReference.orderByChild("adressText");

        currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameFromDB = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("fullName").getValue(String.class);
                if(dataSnapshot.exists()){
                    System.out.println("Data snap shoot work" );
                    System.out.println("Password is " +  nameFromDB);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }


    public void loginFunc(View view) {
        userLogin();
        System.out.println("IM in LOGIN");
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
//        System.out.println("email is " + email + "pass" + password);

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
       // progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //redirect to the
                        System.out.println("Yeeeeey we got in to the system with name !!!! " + nameFromDB);

                        Intent i = new Intent(Login.this, ProfileActivity.class);
                        i.putExtra("id", nameFromDB);
                        System.out.println(nameFromDB);
                        if(nameFromDB!=null) {
                            startActivity(i);
                        }



//                        startActivity(new Intent(  Login.this, ProfileActivity.class));
                    } else {

                    }
                    ;

                }
            });


    }
}

