package com.example.coinmate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coinmate.R;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdit, passEdit;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setVariable();
    }

    private void initView() {
        userEdit = findViewById(R.id.editTextTextPersonName);
        passEdit = findViewById(R.id.editTextTextPersonPassword);
        loginButton = findViewById(R.id.buttonLogIn);
    }

    private void setVariable() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEdit.getText().toString().isEmpty() || passEdit.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
                } else if (userEdit.getText().toString().equals("admin") && passEdit.getText().toString().equals("admin")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });
    }
}