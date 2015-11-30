package alwt.hackathon.epam.com.seh_android.server;

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
public class QRQueryTask extends QueryTask {

    public static final String DEFAULT_VALUE = "-1";

    private String validation;

    @Override
    protected String doInBackground(String... params) {
        try {
            String url = params[0];
            Log.d(LOG_TAG, url);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();

            HttpGet get = new HttpGet(url);
            Log.d(LOG_TAG, get.toString());

            String response = httpClient.execute(get, res);
            Log.d(LOG_TAG, response);

            validation = (response);
            validation = (response);

            Log.d(LOG_TAG, validation);
        } catch (Throwable t) {
            validation = DEFAULT_VALUE;
            Log.d(LOG_TAG, t.getMessage() + " " + t.toString());
            throwable = t;
        }
        return null;
    }

    public String getValidation() {
        return validation;
    }
}
