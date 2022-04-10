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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private String users;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn = (Button) findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById (R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById (R.id.editTextTextPassword);
        //progressBar == (ProgressBar) findViewById (R.id.progressBar);


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
                        System.out.println("Yeeeeey");

                        startActivity(new Intent(  Login.this, ProfileActivity.class));
                    } else {

                    }
                    ;

                }
            });


    }
}

