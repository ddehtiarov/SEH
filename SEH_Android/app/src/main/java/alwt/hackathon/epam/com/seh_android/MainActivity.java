package alwt.hackathon.epam.com.seh_android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import alwt.hackathon.epam.com.seh_android.constant.PathConstants;
import alwt.hackathon.epam.com.seh_android.server.UserQueryTask;
import alwt.hackathon.epam.com.seh_android.server.entity.User;

public class MainActivity extends Activity {
    private Button login, register;
    private EditText email, password;
    Properties properties;
    private static final String LOG_TAG = "logs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProperties();
        Log.d("ONCREATEMAIN", "START WATCHING");
        if (isLoggedIn()) {
            sucsIntent();
        } else {
            initMainActivity();
        }

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        Log.d("ONCREATEMAIN", "STARTED");
        Log.d("ONCREATEMAIN", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());

    }

    private void setProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(new File("res/appSettings.properties"));
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sucsIntent() {
        Intent intent = new Intent(MainActivity.this, QrLauncher.class);
        startActivity(intent);
    }

    private void initMainActivity() {

        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.loginButton);
        register = (Button) findViewById(R.id.registerButton);
        email = (EditText) findViewById(R.id.emailEdit);
        password = (EditText) findViewById(R.id.enterPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = PathConstants.VALID_USER + email.getText() + PathConstants.AMPERSAND
                        + PathConstants.USER_PASS + password.getText();
                User user = null;
                UserQueryTask queryTask = new UserQueryTask();

                queryTask.execute(query);
                while (user == null) {
                    user = queryTask.getUser();
                }

                Log.d(LOG_TAG, user.toString());

                if (user.getId() > -1) {
                    saveUserDetails(user);
                    sucsIntent();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Invalid params", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isLoggedIn() {
        if (properties.getProperty("id") != null && !properties.getProperty("id").isEmpty()) {
            String userId = properties.getProperty("id");
            Log.d(LOG_TAG, "saved userLogin: " + userId);
            if (userId != null) {
                return true;
            }
        }
        return false;
    }

    private void saveUserDetails(User user) {
        Log.d(LOG_TAG, "saved id: " + user.getId());
        properties.setProperty("id", "" + user.getId());
    }
}
