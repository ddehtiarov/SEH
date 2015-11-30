package alwt.hackathon.epam.com.seh_android.server;

import android.util.Log;

import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by dehtiarov on 11/29/2015.
 */
public class QRTES extends QueryTask {

    @Override
    protected String doInBackground(String... params) {
        try{
        String url = params[0];
        Log.d(LOG_TAG, url);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        ResponseHandler<String> res = new BasicResponseHandler();

        HttpPost get = new HttpPost(url);
        Log.d(LOG_TAG, get.toString());

        String response = httpClient.execute(get, res);
        Log.d(LOG_TAG, response);

    } catch (Throwable t) {
        Log.d(LOG_TAG, t.getMessage() + " " + t.toString());
        throwable = t;
    }
    return null;
    }
}
