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

public class Register extends AppCompatActivity {
    private LinearLayout RegLayout;
    private EditText InputEmail, InputPassword;
    private TextView BtnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        RegLayout = (LinearLayout) findViewById(R.id.register_layout);
        InputEmail = (EditText) findViewById(R.id.inputregisteremail);
        InputPassword = (EditText) findViewById(R.id.inputregisterpassword);
        BtnRegister = (TextView) findViewById(R.id.btnregister);

        auth = FirebaseAuth.getInstance();

        Snackbar snackbar = Snackbar.make(RegLayout,"ALREADY A MEMBER ?",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("LOGIN", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
        snackbar.show();

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            String email = InputEmail.getText().toString();
            String password = InputPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                InputEmail.setError("Required");
            }

                if (TextUtils.isEmpty(password)){
                InputPassword.setError("Required");
            }

                if (password.length() < 5){
                    InputPassword.setError("Weak Password...Try Another");
                    Toasty.warning(getApplicationContext(),"Weak Password...Try Another",Toast.LENGTH_SHORT,true).show();
            }
                else {
                if (!isOnline(getApplicationContext())){

                    Snackbar snackbar = Snackbar.make(RegLayout,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {

                    final ProgressDialog dialog = new ProgressDialog(Register.this);
                    dialog.setMessage("Loading....");
                    dialog.show();

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toasty.success(getApplicationContext(),"Welcome on board...",Toast.LENGTH_LONG,true).show();

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Registration", "Email sent.");
                                                }
                                            }
                                        });

                                startActivity(new Intent(Register.this, Login.class));
                                finish();
                            } else {
                                dialog.dismiss();
                                Toasty.error(getApplicationContext(), "Registration Failed...Please Try Again",Toast.LENGTH_LONG,true).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this, Welcome.class));
        finish();
    }
}
