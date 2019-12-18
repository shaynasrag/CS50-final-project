package com.example.yswipe.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yswipe.ChooseLoginRegistrationActivity;
import com.example.yswipe.LoginActivity;
import com.example.yswipe.MainActivity;
import com.example.yswipe.Matches.MatchesActivity;
import com.example.yswipe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private Button mNext, mVerifyButton;
    private EditText mEmail, mPassword;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                        Intent intent = new Intent(RegistrationActivity.this, ConfirmEmailActivity.class);
                        startActivity(intent);
                        finish();
                        return;

                }
            }
        };

        mVerifyButton = findViewById(R.id.verifyButton);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = "@yale.edu";
                final String password = mPassword.getText().toString();

                String email = mEmail.getText().toString();

                boolean contains = email.toLowerCase().contains(search.toLowerCase());
                if (contains) {
                    final String yaleEmail = email;

                    mFirebaseAuth.createUserWithEmailAndPassword(yaleEmail, password).addOnCompleteListener(RegistrationActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration Error: An account with this email and password already exists.", Toast.LENGTH_SHORT).show();
                        } else {
                            mFirebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(task1 -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegistrationActivity.this,
                                                    "A confirmation email has been sent to this address. Please check your email to verify your account.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                            }

                     });
                }
                else if (!contains){
                    Toast.makeText(RegistrationActivity.this,
                                    "Make sure you are using a valid 'yale.edu' email.",
                                    Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(firebaseAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    public void backToRegLogin(View view) {
        Intent intent = new Intent(RegistrationActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        return;
    }
}

