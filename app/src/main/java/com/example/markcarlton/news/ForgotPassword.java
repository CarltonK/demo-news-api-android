package com.example.markcarlton.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class ForgotPassword extends AppCompatActivity {
    private LinearLayout PassLayout;
    private EditText InputEmail;
    private TextView BtnForgotPass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        PassLayout = (LinearLayout) findViewById(R.id.forgotpass_layout);
        InputEmail = (EditText) findViewById(R.id.inputemail);
        BtnForgotPass = (TextView) findViewById(R.id.btnforgotpassword);

        auth = FirebaseAuth.getInstance();

        BtnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = InputEmail.getText().toString();

                if (TextUtils.isEmpty(email)){
                    InputEmail.setError("Required");
                }
                else {
                    if (!isOnline(getApplicationContext())){
                        Snackbar snackbar = Snackbar.make(PassLayout,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {

                        final ProgressDialog dialog = new ProgressDialog(ForgotPassword.this);
                        dialog.setTitle("Password Recovery");
                        dialog.setMessage("Sending Email...");
                        dialog.show();

                        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()){
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            Toasty.success(getApplicationContext(),"Password Recovery Instructions sent to your Email", Toast.LENGTH_LONG, true).show();
                                            Intent intent = new Intent(ForgotPassword.this,Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    };

                                    android.os.Handler handler = new android.os.Handler();
                                    handler.postDelayed(runnable, 1000);
                                    dialog.hide();

                                } else {
                                    dialog.hide();
                                    Toasty.error(getApplicationContext(), "Password Recovery Failed...Please Try Again",Toast.LENGTH_LONG, true).show();
                                    Log.i(Constants.TAG,task.getException().getLocalizedMessage());

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
        startActivity(new Intent(ForgotPassword.this, Login.class));
        finish();
    }
}
