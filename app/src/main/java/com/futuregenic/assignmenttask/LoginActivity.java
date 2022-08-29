package com.futuregenic.assignmenttask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button loginBtn;
    String username = "admin@admin.com";
    String password = "admin123";

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        init();
    }

    private void init() {
        etEmail = findViewById(R.id.edTxt_Login_Email);
        etPassword = findViewById(R.id.edTxt_Login_Password);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().equals(username)){
                    if (etPassword.getText().toString().equals(password)){
                        editor.putString("status", "login");
                        editor.putLong("loginTime", System.currentTimeMillis());
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else {
                        etPassword.setError("Enter Correct Password");
                    }
                } else {
                    etEmail.setError("Enter Correct Email");
                }
            }
        });
    }
}