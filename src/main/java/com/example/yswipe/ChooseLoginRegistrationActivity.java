package com.example.yswipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yswipe.Register.RegistrationActivity;

public class ChooseLoginRegistrationActivity extends AppCompatActivity {

    private Button mLogin, mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registration2);
        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);

        mLogin.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseLoginRegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        });
        mRegister.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseLoginRegistrationActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
            return;
        });



    }
}
