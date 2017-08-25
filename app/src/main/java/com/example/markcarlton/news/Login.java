package com.example.markcarlton.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {
    private EditText InputEmail, InputPassword;
    private TextView BtnLogin, ForgotPass;
    private LinearLayout LoginLayout;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        InputEmail = (EditText) findViewById(R.id.inputloginemail);
        InputPassword = (EditText) findViewById(R.id.inputloginpassword);
        BtnLogin  = (TextView) findViewById(R.id.btnlogin);
        LoginLayout = (LinearLayout) findViewById(R.id.login_layout);
        ForgotPass = (TextView) findViewById(R.id.btnforgotpass);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    // User is signed out
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        Snackbar snackbar = Snackbar.make(LoginLayout,"NOT REGISTERED ?",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("REGISTER NOW", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });
        snackbar.show();

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
                finish();
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = InputEmail.getText().toString();
                String password = InputPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    InputEmail.setError("Required");
                }

                if (TextUtils.isEmpty(password)){
                    InputPassword.setError("Required");
                }
                else {
                    if (!isOnline(getApplicationContext())){

                        Snackbar snackbar = Snackbar.make(LoginLayout,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {

                        final ProgressDialog dialog = new ProgressDialog(Login.this);
                        dialog.setMessage("Loading....");
                        dialog.show();

                        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    Toasty.success(getApplicationContext(),"Welcome...", Toast.LENGTH_LONG,true).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    Toasty.error(getApplicationContext(), "Login Failed...Please check your credentials",Toast.LENGTH_LONG,true).show();
                                    Log.i(Constants.TAG, task.getException().getLocalizedMessage());
                                }
                            }
                        });
                    }

                }
            }
        });
    }

    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, Welcome.class));
        finish();
    }
}
