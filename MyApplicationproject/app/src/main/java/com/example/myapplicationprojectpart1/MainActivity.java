package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private String userName;
    private String password;
    private TextView warningText;
    List<UserAccount> accounts;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference accountsReference=db.getReference("accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameText=(EditText) findViewById(R.id.userNameText);
        passwordText=(EditText) findViewById(R.id.passwordText);
        userName=userNameText.getText().toString();
        password=passwordText.getText().toString();
        accounts=new LinkedList<>();
        warningText= (TextView) findViewById(R.id.warningText);
        accountsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    UserAccount account = child.getValue(UserAccount.class);
                    accounts.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void create(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    private boolean canLogIn(){
        if (userName.equals("") ){
            warningText.setText("Please enter a valid account!");
            return false;
        }
        if (password.equals("") ){
            warningText.setText("please enter a valid password!");
            return false;
        }
        if (!ListRecheck.isDuplicate(accounts,userName)){
            warningText.setText("The user name you entered does not exist!");
            return false;
        }
        if(!ListRecheck.match(accounts,userName,password)){
            warningText.setText("The password you entered does not match the user name!");
            return false;
        }
        return true;
    }
    public void login(View view){
        userName=userNameText.getText().toString();
        password=passwordText.getText().toString();
        if(canLogIn()){
            for(UserAccount account:accounts){
                if(account.getUserName().equals(userName)){
                    Intent intent=new Intent(this,WelcomeActivity.class);
                    intent.putExtra("userName",account.getUserName());
                    intent.putExtra("identity",account.getIdentity());
                    intent.putExtra("account",account);
                    startActivity(intent);
                }
            }
        }

    }
}