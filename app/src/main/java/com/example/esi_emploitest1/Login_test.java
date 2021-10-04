package com.example.esi_emploitest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Login_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();;
        startActivity(new Intent(getApplicationContext() ,MainActivity.class));
        finish();
    }
}
