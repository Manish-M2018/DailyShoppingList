package com.example.dailyshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    private EditText email, pass;
    private TextView signin;
    private Button btnReg;

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_reg);
        pass = findViewById(R.id.password_reg);

        btnReg = findViewById(R.id.btn_reg);
        signin = findViewById(R.id.signin_txt);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Required field");
                    return;
                }
                if (TextUtils.isEmpty(mPass)){
                    pass.setError("Required field");
                    return;
                }

                mDialog.setMessage("Processing...");
                mDialog.show();

               mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                           Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                           mDialog.dismiss();
                       }
                       else {
                           Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                           mDialog.dismiss();
                       }
                   }
               });
            }
        });
    }
}
