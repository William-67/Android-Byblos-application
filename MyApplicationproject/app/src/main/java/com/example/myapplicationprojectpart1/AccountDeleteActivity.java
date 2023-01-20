package com.example.myapplicationprojectpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountDeleteActivity extends AppCompatActivity {
    private TextView userNameTextView;
    private TextView passwordTextView;
    private TextView identityTextView;
    private UserAccount userAccount;
    private DatabaseReference accountsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_delete);
        userNameTextView=(TextView) findViewById(R.id.userNameTextView);
        passwordTextView=(TextView) findViewById(R.id.passwordTextView);
        identityTextView=(TextView) findViewById(R.id.identityTextview);
        Intent intent=getIntent();
        userAccount=(UserAccount) intent.getSerializableExtra("account");
        userNameTextView.setText(userAccount.getUserName());
        passwordTextView.setText(userAccount.getPassword());
        identityTextView.setText(userAccount.getIdentity().toString());
        accountsReference = FirebaseDatabase.getInstance().getReference("accounts");

        if(userAccount.getIdentity()==Identity.administrator){
            Button deleteAccount = (Button) findViewById(R.id.deleteAccount);
            deleteAccount.setEnabled(false);
        }
    }
    public void deleteAcc(View view){
        accountsReference.child(userAccount.getKey()).setValue(null);
        finish();
    }
}