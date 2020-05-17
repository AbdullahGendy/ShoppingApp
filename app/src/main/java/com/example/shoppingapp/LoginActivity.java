package com.example.shoppingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    AppCompatEditText etEmail, etPassword;
    Button btnLogin;
    //CallbackManager callbackManager;
    TextView tvSignUp;
    private String userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    //private Dialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPrefs sharedPrefs;
    private FirebaseUser currentUser;
    //LoginButton loginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        //facebookInit();
    }

   /* private void facebookInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private void initViews() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        sharedPrefs = new SharedPrefs(this);
        firebaseAuth = FirebaseAuth.getInstance();
        //progressDialog = AppUtils.progressDialog(this);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.bnt_login);
        tvSignUp = findViewById(R.id.tv_sign_up);
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private boolean isUserInputsValid() {

        userEmail = Objects.requireNonNull(etEmail.getText()).toString().trim();
        userPassword = Objects.requireNonNull(etPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(userEmail)) {
            etEmail.setError("Invalid email");
            return false;
        }
        if (TextUtils.isEmpty(userPassword)) {
            etPassword.setError("Invalid password");
            return false;
        }
        return true;
    }

    private void fetchUserData(String uid) {
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // progressDialog.dismiss();
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    sharedPrefs.setUserData(uid, (String) dataMap.get("userName"), currentUser.getEmail(),
                            (String) dataMap.get("userPhone"), (String) dataMap.get("userType"),
                            (String) dataMap.get("userAddress"));
                    //sharedPrefs.setUserPhotoUrl((String) dataMap.get("userImage"));
                    if (sharedPrefs.getUserType().equals("Merchant")) {
                        startActivity(new Intent(LoginActivity.this, MerchantMainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                    }
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG, databaseError.getDetails().toString() + "\n" + databaseError.getMessage().toString());
            }
        });

    }

    private void startLoginProcess() {
        //progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        currentUser = firebaseAuth.getCurrentUser();
                        fetchUserData(currentUser.getUid());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();
                    }

                    // ...
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bnt_login:
                if (isUserInputsValid()) {
                    if (AppUtils.isNetworkAvailable(this)) {
                        startLoginProcess();
                    } else {
                        Toast.makeText(this, "Check Mobile data or wifi!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
