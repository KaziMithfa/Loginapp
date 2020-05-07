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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmailEditText,signInpasswordEditText;
    private TextView signUpTextView;
    private Button signInButton;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Sign In Activity");

        signInEmailEditText = (EditText)findViewById(R.id.signInEmailEditTextId);
        signInpasswordEditText = (EditText)findViewById(R.id.signInPasswordEditTextId);
        signInButton = (Button) findViewById(R.id.signInButtonId);
        signUpTextView = (TextView)findViewById(R.id.signUpTextViewId);
        progressBar =(ProgressBar)findViewById(R.id.progressbarId);
        mAuth = FirebaseAuth.getInstance();




        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.signInButtonId:
                userLogin();


                break;

            case R.id.signUpTextViewId:
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;


        }


    }

    private void userLogin() {

        String email = signInEmailEditText.getText().toString().trim();
        String password = signInpasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signInEmailEditText.setError("Enter an email address");
            signInEmailEditText.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;

        }
        if(password.isEmpty())
        {
            signInpasswordEditText.setError("Enter a Password");
            signInpasswordEditText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            signInpasswordEditText.setError("The password length should be minimum 6 characters");
            signInpasswordEditText.requestFocus();
            return;


        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
