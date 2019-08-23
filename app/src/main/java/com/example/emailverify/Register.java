package com.example.emailverify;

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
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText UserEmail,UserPassword,UserConfirmPassword;
    private Button CreateAccountButton;
   // private ProgressBar loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        //loadingBar=(ProgressBar)findViewById(R.id.l)
        UserEmail=(EditText)findViewById(R.id.UserEmail);
        UserPassword=(EditText)findViewById(R.id.UserPassword);
        UserConfirmPassword=(EditText)findViewById(R.id.UserConfirmPassword);
        CreateAccountButton=(Button)findViewById(R.id.CreateAccountButton);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            SendUserToMainActivity();
        }
    }

    private void SendUserToMainActivity()
    {
        Intent j=new Intent(Register.this,MainActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(j);
        finish();
    }

    private void CreateNewAccount()
    {
        String email=UserEmail.getText().toString();
        String password=UserPassword.getText().toString();
        String confirmPassword=UserConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email...", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(password))
    {
        Toast.makeText(this, "password...", Toast.LENGTH_SHORT).show();
    }if (TextUtils.isEmpty(confirmPassword))
    {
        Toast.makeText(this, "cpassword...", Toast.LENGTH_SHORT).show();
    }else if (!password.equals(confirmPassword))
    {
        Toast.makeText(this, "not match...", Toast.LENGTH_SHORT).show();
    }else {
            //loadingBar.setTitle("Create new account");
           // loadingBar.setMessage("please wait");
           // loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            SendEmail();
                        }else
                        {
                            String message=task.getException().getMessage();
                            Toast.makeText(Register.this, "Error..."+message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        }
    }


    private void SendEmail()
    {
        FirebaseUser user=mAuth.getCurrentUser();
        if (user !=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        SendUserToSetupActivity();
                        Toast.makeText(Register.this, "Reg. Sec and Verify acc...", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();

                    }else
                    {
                        String error= task.getException().getMessage();
                        Toast.makeText(Register.this, "Error..."+error, Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }



    private void SendUserToSetupActivity()
    {
        Intent k=new Intent(Register.this,MainActivity.class);
        k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(k);
        finish();
    }
}
