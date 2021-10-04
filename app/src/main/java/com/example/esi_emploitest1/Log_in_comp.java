package com.example.esi_emploitest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_in_comp extends AppCompatActivity {

    Button btn_login,back;
    EditText email,password;
    FirebaseAuth fAuth;
    ProgressBar progressBar3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_comp);
        back = findViewById(R.id.back);
        try {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Log_in_comp.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException ignored) { }





        email=findViewById(R.id.companyemail);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        fAuth= FirebaseAuth.getInstance();
        progressBar3 = findViewById(R.id.progressBar3);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lemail = email.getText().toString().trim();
                String lpassword=password.getText().toString().trim();





                     //les condition pour vérifier si les cases sont vides

                if(TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    email.setError("Email is Required");
                    password.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(lemail) && !TextUtils.isEmpty(lpassword) ){
                    email.setError("Email is Required");
                    return;
                }
                if(!TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    password.setError("Password is Required");
                    return;
                }





                progressBar3.setVisibility(View.VISIBLE);
                //authenticate the user
                fAuth.signInWithEmailAndPassword(lemail,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Log_in_comp.this, "Logded in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login_test.class));
                        }else{
                            Toast.makeText(Log_in_comp.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar3.setVisibility(View.GONE);


                        }
                    }
                });



            }
        });

























    }
}
