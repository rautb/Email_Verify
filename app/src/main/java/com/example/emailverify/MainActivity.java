package com.example.emailverify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Boolean emailAdressChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();
    }

    private  void VerifyEmailAddress()
    {
        FirebaseUser user=mAuth.getCurrentUser();
        emailAdressChecker=user.isEmailVerified();
        if (emailAdressChecker)
        {
            sendUserToMainActivity();
        }else
        {
            Toast.makeText(this, "Please veriy your account first", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }

    }
    private void sendUserToMainActivity()
    {
        Intent i=new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
