package ap.tybca.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText _main_et_username,_main_et_password;
    Button _main_btn_login;
    String username,password;
    TextView _main_tv_register;

    SharedPreferences sharedPreferences;
    public static final String myPrefs = "myPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _main_et_username = findViewById(R.id.main_et_username);
        _main_et_password = findViewById(R.id.main_et_password);
        _main_btn_login = findViewById(R.id.main_btn_login);
        _main_tv_register = findViewById(R.id.main_tv_register);

        sharedPreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);


        //Redirect to Dashboard page if user is already logged in
        if(sharedPreferences.contains("isLoggedIn")){
            if(sharedPreferences.getString("isLoggedIn","").equals("1")){
                Intent i = new Intent(MainActivity.this, dashboard.class);
                startActivity(i);
                finish();
            }
        }

        _main_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = _main_et_username.getText().toString().trim();
                password = _main_et_password.getText().toString().trim();
                if(username.length() <= 0 && username.isEmpty())
                {
                    _main_et_username.setError("Username should not be empty");
                    _main_et_username.requestFocus();
                }
                else if(password.length() <= 0 && password.isEmpty())
                {
                    _main_et_password.setError("Password should not be empty");
                    _main_et_password.requestFocus();
                }
                else if(password.length() > 0 && password.length() < 8)
                {
                    _main_et_password.setError("Password should be minimum 8 Characters");
                    _main_et_password.requestFocus();
                }
                else
                {
                    //This is the correct portion
                    if(username.equals("admin") && password.equals("password123"))
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.putString("isLoggedIn","1");
                        editor.commit();
                        //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), dashboard.class);
                       // i.putExtra("username",username);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Failed to Login",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    public void redirectToRegister(View view)
    {
        Intent i = new Intent(getApplicationContext(), registration.class);
        startActivity(i);
        finish();
    }
}