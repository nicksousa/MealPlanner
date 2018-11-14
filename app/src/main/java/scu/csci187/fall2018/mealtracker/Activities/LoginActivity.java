package scu.csci187.fall2018.mealtracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import scu.csci187.fall2018.mealtracker.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, passwordButton, createAccountButton;
    EditText emailInput, pwInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginButton = findViewById(R.id.login);
        passwordButton = findViewById(R.id.forgotPassword);
        createAccountButton = findViewById(R.id.createAccount);
        emailInput = findViewById(R.id.email);
        pwInput = findViewById(R.id.pw);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pw;

                email = emailInput.getText().toString();
                pw = pwInput.getText().toString();
                if(loginSuccessful(email, pw)) {
                    // instantiate user obj, send it in bundle
                    // when we go to HomeScreen activity
                }
                else {
                    Toast invalidInput = Toast.makeText(LoginActivity.this, "E-mail/password is not valid", Toast.LENGTH_SHORT);
                    invalidInput.setGravity(Gravity.CENTER, 0,60);
                    invalidInput.show();
                    emailInput.setText("");
                    pwInput.setText("");
                }

            }
        });

        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to password reminder screen
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
    }

    // to add if more time: salt + hashing function for password
    // store only hashed pw in DB, hash inputtedPw and then check against DB
    boolean loginSuccessful(String email, String pw) {
        // DB query to look-up email, pw in User table

        /*if e-mail not in database
              return false
        if e-mail in DB, check inputted pw against DB pw
            if inputtedPw == dbPw
                return true;
            else
                return false
        */

        // for testing dialogue Toast
        return false;
    }
}

