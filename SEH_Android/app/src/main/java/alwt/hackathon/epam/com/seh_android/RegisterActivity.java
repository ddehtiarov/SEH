package alwt.hackathon.epam.com.seh_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alwt.hackathon.epam.com.seh_android.constant.PathConstants;
import alwt.hackathon.epam.com.seh_android.server.UserQueryTask;
import alwt.hackathon.epam.com.seh_android.server.entity.User;

public class RegisterActivity extends Activity {
    Button register;
    EditText name, email, password, passwordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initRegisterActivity();
    }

    private void initRegisterActivity() {
        register = (Button) findViewById(R.id.registrationButton);
        name = (EditText) findViewById(R.id.nameInput);
        password = (EditText) findViewById(R.id.password);
        passwordAgain = (EditText) findViewById(R.id.passwordAgain);
        email = (EditText) findViewById(R.id.emailInput);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = PathConstants.ADD_USER + PathConstants.USER_EMAIL
                        + email.getText() + PathConstants.AMPERSAND + PathConstants.USER_PASS
                        + password.getText() + PathConstants.AMPERSAND + PathConstants.USER_NAME + name.getText();

                UserQueryTask queryTask = new UserQueryTask();

                if (password.getText().toString().equals(passwordAgain.getText().toString())) {
                    User user = null;
                    Log.d("log", "pass are equals");

                    queryTask.execute(query);

                    while (user == null) {
                        user = queryTask.getUser();
                    }

                    Log.d("log", "user" + user);

                    if (user.getId() > -1) {
                        sucsIntent();
                        saveUserDetails(user);
                        return;
                    } else {
                        Toast toast = Toast.makeText(RegisterActivity.this, "Invalid params or user exist", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(RegisterActivity.this, "pass are not equals", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }

    private void sucsIntent() {
        Intent intent = new Intent(RegisterActivity.this, QrLauncher.class);
        startActivity(intent);
    }

    private void saveUserDetails(User user) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.edit().putString(String.valueOf(R.string.user_login), user.getId().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}