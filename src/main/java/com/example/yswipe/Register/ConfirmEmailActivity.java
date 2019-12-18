package com.example.yswipe.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yswipe.LoginActivity;
import com.example.yswipe.MainActivity;
import com.example.yswipe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfirmEmailActivity extends AppCompatActivity {

    private Button mConfirm;


    private EditText mEmail, mPassword;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };
        mConfirm = findViewById(R.id.confirm);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);


        mConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ConfirmEmailActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(ConfirmEmailActivity.this,
                                    "Sign In Error", Toast.LENGTH_SHORT).show();
                        }else{
                            if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(ConfirmEmailActivity.this, RegisterInfo.class));
                            }else{
                                Toast.makeText(ConfirmEmailActivity.this,
                                        "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
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
}
