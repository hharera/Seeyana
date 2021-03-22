package com.example.n_one.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.n_one.R;

public class SignUpWelcomeActivity extends AppCompatActivity {

    private Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup_welcome);
        btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpWelcomeActivity.this, SecondProgressActivity.class));
            }
        });
    }
}
