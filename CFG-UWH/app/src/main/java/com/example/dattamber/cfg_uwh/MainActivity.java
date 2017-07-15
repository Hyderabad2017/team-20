package com.example.dattamber.cfg_uwh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    EditText email,pass;
    ProgressDialog pd;
    Button login, donor, insti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbtn);
        pass = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.emailid);
        login.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        if(v==login)
        {
            final String emailID,password;
            emailID = email.getText().toString();
            password = pass.getText().toString();
            if(emailID != "" && password != "") {
                pd.setMessage("Signing in");
                pd.show();
                mAuth.signInWithEmailAndPassword(emailID, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Toast.makeText(Signin.this, "Success", Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    pd.dismiss();
                                    //Toast.makeText(getApplicationContext(),globalVariable.getMyString(),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), DonorMain.class);
                                    startActivity(intent);
                                    if(email.equals("admin@uwh.com")) {
                                        pd.dismiss();
                                        //Toast.makeText(getApplicationContext(),globalVariable.getMyString(),Toast.LENGTH_LONG).show();
                                        Intent inte = new Intent(getApplicationContext(), DonorMain.class);
                                        startActivity(inte);
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // [START_EXCLUDE]
                                if (!task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(MainActivity.this, "Attempt failed. Please try again.", Toast.LENGTH_LONG).show();
                                }
                                // [END_EXCLUDE]
                            }
                        });
            }
            else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
