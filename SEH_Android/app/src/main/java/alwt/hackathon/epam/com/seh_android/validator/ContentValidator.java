package alwt.hackathon.epam.com.seh_android.validator;

import android.util.Log;

import alwt.hackathon.epam.com.seh_android.constant.PathConstants;
import alwt.hackathon.epam.com.seh_android.server.QRQueryTask;

/**
 * Created by dehtiarov on 11/26/2015.
 */
public class ContentValidator {

    /**
     * Validate qr code value. sending data to server
     * and checking if they are correct or not.
     *
     * @param contents
     * @return
     */
    public static boolean validateContent(String contents, String id) {
        String valid = null;
        String query = PathConstants.CHECK_QR;
        QRQueryTask queryTask = new QRQueryTask();

        Log.d("log", "VALIDATE CONTENT");

        queryTask.execute(query + contents + PathConstants.AMPERSAND + PathConstants.USER_ID + id);

        while (valid == null) {
            valid = queryTask.getValidation();
        }

        boolean result = !valid.equals(QRQueryTask.DEFAULT_VALUE) && Boolean.valueOf(valid);

        Log.d("log", valid + " " + result);

        return result;

    }
}