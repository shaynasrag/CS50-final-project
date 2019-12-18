package com.example.yswipe.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yswipe.ChooseLoginRegistrationActivity;
import com.example.yswipe.MainActivity;
import com.example.yswipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterPreferencesActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private Button mRegister;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_preferences);
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                 if (user == null) {
                    Intent intent = new Intent(RegisterPreferencesActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRadioGroup = findViewById(R.id.RadioGroup);
        mRegister = findViewById(R.id.register);
        mRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int genderId = mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(genderId);
                if(radioButton.getText() == null) {
                    return;
                }
                mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        String userId = mFirebaseAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                        Map userInfo = new HashMap<>();
                        int preferenceNum = -1;
                        switch(radioButton.getText().toString()){
                            case "Men": preferenceNum = 0; break;
                            case "Women": preferenceNum = 1; break;
                            case "Anyone": preferenceNum = 2; break;
                            default: ;
                        }
                        userInfo.put("preferences", preferenceNum);


                        currentUserDb.updateChildren(userInfo);

                    }
                });
                Intent intent = new Intent(RegisterPreferencesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
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

    public void backtoGender(View view) {
        Intent intent = new Intent(RegisterPreferencesActivity.this, RegisterGenderActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}

