package alwt.hackathon.epam.com.seh_android.server;

import android.os.AsyncTask;

/**
 * Created by dehtiarov on 11/28/2015.
 */
public abstract class QueryTask  extends AsyncTask<String, String, String> {

    protected static final String LOG_TAG = "QueryTask: logs";

    protected Throwable throwable;

    protected abstract String doInBackground(String ... params);

}
