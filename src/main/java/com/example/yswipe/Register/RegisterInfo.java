package com.example.yswipe.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.yswipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterInfo extends AppCompatActivity {

    private Spinner mResSpinner, mYearSpinner;
    private EditText mName;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info2);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };
        mName = findViewById(R.id.name);
        mResSpinner = findViewById(R.id.resCollegeSpinner);
        mYearSpinner = findViewById(R.id.gradYearSpinner);

        ArrayAdapter<String> mResCollegeAdapter = new ArrayAdapter<String>(RegisterInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.college_array));
        mResCollegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResSpinner.setAdapter(mResCollegeAdapter);

        ArrayAdapter<String> mYearAdapter = new ArrayAdapter<String>(RegisterInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year_array));
        mResCollegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearSpinner.setAdapter(mYearAdapter);

    }

    public void toGender(View view) {
        final String name = mName.getText().toString();
        final String resCollege = mResSpinner.getSelectedItem().toString();
        final String gradYear = mYearSpinner.getSelectedItem().toString();

        String userId = mFirebaseAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        Map userInfo = new HashMap<>();;
        userInfo.put("name", name);
        userInfo.put("resCollege", resCollege);
        userInfo.put("year", gradYear);
        userInfo.put("profilePicUrl", "default");

        currentUserDb.updateChildren(userInfo);

        currentUserDb.updateChildren(userInfo);

        Intent intent = new Intent(RegisterInfo.this, RegisterGenderActivity.class);
        startActivity(intent);
        finish();
        return;

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

