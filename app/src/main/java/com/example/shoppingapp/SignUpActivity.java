package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.data.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etName, etPhone, etAddress, etPassword, etRePassword;
    Button btnSignUp;
    private static final String TAG = "SignUpActivity";
    private FirebaseDatabase database;
    private DatabaseReference dataBaseReference;
    private FirebaseAuth firebaseAuth;
    private String userName, userType, userPhone, userEmail, userPassword,
            userAddress;
    private SharedPrefs sharedPrefs;
    private RadioButton rbUser, rbMerchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_re_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        rbUser = findViewById(R.id.rb_user);
        rbMerchant = findViewById(R.id.rb_merchant);

        sharedPrefs = new SharedPrefs(this);
        database = FirebaseDatabase.getInstance();
        dataBaseReference = database.getReference();
        btnSignUp.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();


    }

    private void startSignUpProcess() {
        //progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            uploadUserInfo(user.getUid());

                        } else {
                            // progressDialog.dismiss();
                            // If sign in fails.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.:" + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadUserInfo(String uid) {
        UserDetails userDetails = new UserDetails(userName, userPhone, userType, userAddress);
        dataBaseReference.child("Users").child(uid).setValue(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Cache the user Info
                sharedPrefs.setUserData(uid, userName, userEmail, userPhone, userType, userAddress);
                // progressDialog.dismiss();
                Intent intent;
                if (rbUser.isChecked()) {
                    intent = new Intent(SignUpActivity.this, UserMainActivity.class);
                    startActivity(intent);
                }
                if (rbMerchant.isChecked()) {
                    intent = new Intent(SignUpActivity.this, MerchantMainActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getMessage());
                //progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Failed to complete sign up process.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isUserInputsValid() {

        userName = etName.getText().toString().trim();
        userPhone = etPhone.getText().toString().trim();
        userEmail = etEmail.getText().toString().trim();
        userPassword = etPassword.getText().toString().trim();
        userAddress = etAddress.getText().toString().trim();
        if (rbMerchant.isChecked()) {
            userType = "Merchant";
        } else {
            userType = "User";
        }
        if (TextUtils.isEmpty(userName)) {
            etName.setError("Empty name");
            return false;
        }
        if (TextUtils.isEmpty(userPhone) || !userPhone.matches("^01[0-2]{1}[0-9]{8}")) {
            etPhone.setError("Invalid phone");
            return false;
        }
        if (TextUtils.isEmpty(userEmail)) {
            etEmail.setError("Empty email");
            return false;
        }
        if (TextUtils.isEmpty(userPassword)) {
            etPassword.setError("Empty password");
            return false;
        }
        if (userPassword.length() < 6) {
            etPassword.setError("Min length 6");
            return false;
        }

        if (TextUtils.isEmpty(userAddress)) {
            etAddress.setError("Empty address");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_sign_up:
                if (isUserInputsValid()) {
                    if (AppUtils.isNetworkAvailable(this)) {
                        startSignUpProcess();
                    } else {
                        Toast.makeText(this, "Check Mobile data or wifi!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
