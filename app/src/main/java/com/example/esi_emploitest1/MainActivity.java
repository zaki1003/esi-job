package com.example.esi_emploitest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.etudiantbutton);
        try {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Sign_up_etud.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException ignored) { }
        btn2 = (Button) findViewById(R.id.entreprisebutton);
        try {
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Log_in_comp.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException ignored) { }






    }
}