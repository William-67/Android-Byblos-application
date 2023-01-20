package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private TextView warningText;
    private RadioGroup identityGroup;
    private String userName;
    private String password;
    private Identity identity;
    List<UserAccount> accounts;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference accountsReference=db.getReference("accounts");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameText=(EditText) findViewById(R.id.userNameText);
        passwordText=(EditText) findViewById(R.id.passwordText);
        identityGroup=(RadioGroup) findViewById(R.id.identityGroup);
        warningText= (TextView) findViewById(R.id.warningText);
        userName=userNameText.getText().toString();
        password=passwordText.getText().toString();
        accounts=new LinkedList<>();
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
    public void createAccount(View view) {
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        if(canBeCreated()){
            String key = accountsReference.push().getKey();
            UserAccount account = new UserAccount(key,userName,password,identity);
            accountsReference.child(key).setValue(account);
            finish();
        }
    }
    private boolean canBeCreated(){
        if (userName.equals("") ){
            warningText.setText("Your account can not be empty!");
            return false;
        }
        if (password.equals("") ){
            warningText.setText("Your password can not be empty!");
            return false;
        }
        switch (identityGroup.getCheckedRadioButtonId()){
            case R.id.customerBtn:
                identity=Identity.customer;
                break;
            case R.id.employeeBtn:
                identity=Identity.employee;
                break;
            default:
                warningText.setText("Please complete your registration information!");
                return false;
        }
        if (ListRecheck.isDuplicate(accounts,userName)){
            warningText.setText("The user name you have selected is already taken.");
            return false;
        }
        return true;
    }
}