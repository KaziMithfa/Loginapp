package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpEmailEditText,signUppasswordEditText;
    private TextView signInTextView;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up Activity");

        signUpEmailEditText = (EditText)findViewById(R.id.signUpEmailEditTextId);
        signUppasswordEditText = (EditText)findViewById(R.id.signUpPasswordEditTextId);
        signUpButton = (Button) findViewById(R.id.signUpButtonId);
        signInTextView = (TextView)findViewById(R.id.signInTextViewId);
        progressBar = (ProgressBar)findViewById(R.id.progressbarId);

        mAuth = FirebaseAuth.getInstance();
        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.signUpButtonId:
                userRegister();

                break;
            case R.id.signInTextViewId:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;


        }



    }

    private void userRegister() {

        String email = signUpEmailEditText.getText().toString().trim();
        String password = signUppasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {


            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;

        }

        if(password.isEmpty())
        {
            signUppasswordEditText.setError("Enter a Password");
            signUppasswordEditText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            signUppasswordEditText.setError("The password length should be minimum 6 characters");
            signUppasswordEditText.requestFocus();
            return;


        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    
                }else {

                   if(task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(SignUpActivity.this, "User is already registered", Toast.LENGTH_SHORT).show();
                   }
                   else{

                       Toast.makeText(SignUpActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }



            }
        });







    }


}
