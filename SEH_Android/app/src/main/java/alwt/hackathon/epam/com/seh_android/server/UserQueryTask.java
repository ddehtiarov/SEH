package alwt.hackathon.epam.com.seh_android.server;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import alwt.hackathon.epam.com.seh_android.server.entity.User;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by dehtiarov on 11/28/2015.
 */
public class UserQueryTask extends QueryTask{

    private User user;

    protected String doInBackground(String... params) {
        try {
            String url = params[0];
            Log.d(LOG_TAG, url);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();

            HttpGet get = new HttpGet(url);
            Log.d(LOG_TAG, get.toString());

            String response = httpClient.execute(get, res);
            JSONObject result = new JSONObject(response);
            Log.d(LOG_TAG, response);

            user = new User(result);

            Log.d(LOG_TAG, user.toString());
        } catch (Throwable t) {
            user = new User();
            user.setId(-1L);
            Log.d(LOG_TAG, t.getMessage() + " " + t.toString());
            throwable = t;
        }
        return null;
    }

    public User getUser() {
        return this.user;
    }
}
