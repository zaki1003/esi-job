package com.example.esi_emploitest1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_up_etud extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    Button btn3,back,btn_signup;
    EditText email,fullname,password;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_etud);
        back= findViewById(R.id.back);
        btn3 = (Button) findViewById(R.id.btn_to_signup);
        try {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Sign_up_etud.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException ignored) { }
        try {
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Sign_up_etud.this, Log_in_etud.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException ignored) { }

        fullname=findViewById(R.id.fullname);
        email = findViewById(R.id.companyemail);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);
        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //dans le reste de code il faut que je remplace MainAvtivity avec l'activity qui suit l'incription  -----et ajouter un button et une methode for logout
/*
        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

*/






        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lfullname = fullname.getText().toString();
                final String lemail =email.getText().toString().trim();
                String lpassword=password.getText().toString().trim();


                //les condition pour vérifier si les cases sont vides
                if(TextUtils.isEmpty(lfullname)  && TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    fullname.setError("Full Name is Required");
                    email.setError("Email is Required");
                    password.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(lfullname)  && !TextUtils.isEmpty(lemail) && !TextUtils.isEmpty(lpassword) ){
                    fullname.setError("Full name is Required");
                    return;
                }
                if(!TextUtils.isEmpty(lfullname)  && TextUtils.isEmpty(lemail) && !TextUtils.isEmpty(lpassword) ){
                    email.setError("Email is Required");
                    return;
                }
                if(!TextUtils.isEmpty(lfullname)  && !TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    password.setError("Password is Required");
                    return;
                }
                if(!TextUtils.isEmpty(lfullname)  && TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    email.setError("Email is Required");
                    password.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(lfullname)  && TextUtils.isEmpty(lemail) && !TextUtils.isEmpty(lpassword) ){
                    fullname.setError("Full Name is Required");
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(lfullname)  && !TextUtils.isEmpty(lemail) && TextUtils.isEmpty(lpassword) ){
                    fullname.setError("Full Name is Required");
                    password.setError("Password is Required");
                    return;
                }





                //condition de l'email (obligatoire de ESI-SBA ) et le mot de passe contient au moins 6 caracteres

                String verifiermail = lemail.substring(lemail.length()-10);


                //l'email doit contenir au moins 11 caracteres pour vérifier si il est de ESI-SBA  et éviter les erreurs
                if(lemail.length() <= 11 ){
                    email.setError("Email Must be >= 11 Characters");
                    return;
                }



                if(lpassword.length() < 6 && !verifiermail.equals("esi-sba.dz")){
                    password.setError("Password Must be >= 6 Characters");
                    email.setError("You must Sign up with your ESI mail");
                    return;
                }
                if(lpassword.length() >= 6 && !verifiermail.equals("esi-sba.dz")){
                    email.setError("You must Sign up with your ESI mail");
                    return;
                }
                if(lpassword.length() < 6 && verifiermail.equals("esi-sba.dz")){
                    password.setError("Password Must be >= 6 Characters");
                    return;
                }








                progressBar.setVisibility(View.VISIBLE);
                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(lemail,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Sign_up_etud.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("fullName",lfullname);
                            user.put("universityEmail",lemail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: "+ e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Login_test.class));
                        }else {Toast.makeText(Sign_up_etud.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });



            }
        });
    }
}
