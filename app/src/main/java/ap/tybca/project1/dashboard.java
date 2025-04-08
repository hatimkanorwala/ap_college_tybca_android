package ap.tybca.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dashboard extends AppCompatActivity {
    TextView _dashboard_tv_heading;
    String username;

    SharedPreferences sharedPreferences;
    public static final String myPrefs = "myPrefs";
    Button _dashboard_btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _dashboard_tv_heading = findViewById(R.id.dashboard_tv_heading);
        _dashboard_btn_logout = findViewById(R.id.dashboard_btn_logout);

        sharedPreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        //Redirect to Mainactivity(Login) page if user is not logged in
        if(sharedPreferences.contains("isLoggedIn")){
            if(sharedPreferences.getString("isLoggedIn","").equals("1")){
                _dashboard_tv_heading.setText("Welcome, " + sharedPreferences.getString("username",""));
            }
            else{
                redirectToLogin(MainActivity.class);
            }
        }
        else{
            redirectToLogin(MainActivity.class);
        }

        _dashboard_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //remove -> to remove individual item from SP
                editor.remove("username");
                //clear -> clear the whole SP file
                editor.clear();
                editor.commit();

                Toast.makeText(dashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(dashboard.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });


//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        if(b.containsKey("username"))
//        {
//            if(b.getString("username") != "")
//            {
//                username = b.getString("username");
//                _dashboard_tv_heading.setText("Welcome, "+ username);
//            }
//        }

    }

    private void redirectToLogin(Class<MainActivity> mainActivityClass) {
        Intent i = new Intent(dashboard.this,mainActivityClass);
        startActivity(i);
        finish();
    }
}