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

import java.util.UUID;

public class Login extends AppCompatActivity {
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn, Register;
    private String users,email,password;
    public static FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ProgressBar progressBar;
    public static String nameFromDB;
    public Query currentUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = (Button) findViewById(R.id.login);
//        Register = (Button) findViewById(R.id.registerBtn);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById (R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById (R.id.editTextTextPassword);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        currentUser = databaseReference.orderByChild("adressText");
    }


    //login button
    public void loginFunc(View view) {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        System.out.println("The email is " + email + " and password " + password);

//        System.out.println(mAuth.signInWithEmailAndPassword(email, password).isSuccessful());

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //redirect to the activity profile
                    System.out.println("Yeeeeey we got in to the system with email !!!! " + mAuth.getCurrentUser().getEmail());
                    Intent i = new Intent(Login.this, DrawerActivity.class);
                    i.putExtra("id", nameFromDB);
                    System.out.println(nameFromDB);
//                    if(nameFromDB!=null) {
                        startActivity(i);
//                    }
                } else {
                    System.out.println("Yeeeeey we DONT got in to the system with name because mAuth dont works" );
                    Toast.makeText(Login.this, "You need to try again to login", Toast.LENGTH_LONG).show();
                }


            }
        });


//        //pull the current user
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

        System.out.println("IM in LOGIN");


    }
//    public void RegisterFunc (View view){
//        Intent i = new Intent(Login.this, Register.class);
//        startActivity(i);
//    }



}

