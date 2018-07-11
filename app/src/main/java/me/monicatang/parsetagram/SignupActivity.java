package me.monicatang.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    // Views
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etUsernameSignup) EditText etUsernameSignup;
    @BindView(R.id.etPasswordSignup) EditText etPasswordSignup;
    @BindView(R.id.btnCreateUser) Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsernameSignup.getText().toString();
                String password = etPasswordSignup.getText().toString();
                String email = etEmail.getText().toString();

                createUser(username, password, email);
            }
        });


    }

    private void createUser(String username, String password, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.put("handle", username);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(i);

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    e.printStackTrace();
                    Log.e("SignupActivity", "Sign up failed");
                }
            }
        });
    }
}
