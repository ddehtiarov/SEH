package alwt.hackathon.epam.com.seh_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import alwt.hackathon.epam.com.seh_android.constant.ActivityConstant;
import alwt.hackathon.epam.com.seh_android.constant.PathConstants;
import alwt.hackathon.epam.com.seh_android.server.QRQueryTask;
import alwt.hackathon.epam.com.seh_android.server.QRTES;
import alwt.hackathon.epam.com.seh_android.validator.ContentValidator;

public class QrLauncher extends Activity {

    private static final String LOGS_TAG = "QrLauncher: logs";

    Properties properties;

    BackgroundService backgroundService;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setProperties();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_launcher);
        setButtonTextValue();
        //id = Long.valueOf(properties.getProperty("id"));
        backgroundService = new BackgroundService((new Date()).getTime());
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

    private void setButtonTextValue() {
        Button button = (Button) findViewById(R.id.scannerQR_button);
        if (getAppStatus()) {
            button.setText("Stop app");
        } else {
            button.setText("Start app");
        }
    }

    private boolean getAppStatus() {
        boolean working = false;
        if (properties.getProperty("working-status") == null || properties.getProperty("working-status").isEmpty()) {
            working = false;
        } else {
            working = Boolean.valueOf(properties.getProperty("working-status"));
        }
        return working;
    }

    private boolean setAppStatus(boolean status) {
        properties.setProperty("working-status", String.valueOf(status));
        boolean working = status;
        return working;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qr_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startReading() throws ActivityNotFoundException {

        Log.d(LOGS_TAG, "scanning begins");
        Intent intent = new Intent(ActivityConstant.GOOGLE_ZXING + ActivityConstant.ACTION_SCAN);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

        startActivityForResult(intent, 0);

        Log.d(LOGS_TAG, "intent: " + intent.toString());

    }

    public void scanQR(View v) {
        try {
            startReading();
        } catch (ActivityNotFoundException anfe) {
            showDialog(QrLauncher.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        Log.d(LOGS_TAG, "title + message: " + title + " + " + message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + ActivityConstant.GOOGLE_ZXING);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    Log.e(LOGS_TAG, "ERROR: " + anfe);
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String contents = intent.getStringExtra("SCAN_RESULT");
            Log.d(LOGS_TAG, "contents: " + contents);

            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
            Log.d(LOGS_TAG, "format: " + format);

            Boolean valid = ContentValidator.validateContent(contents, "1");

            String message = "empty message";
            if (valid) {
                message = doOperation();
            } else {
                message = "error";
            }
            Toast toast = toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.show();
            Log.d(LOGS_TAG, "toast: " + contents);
        } else {
            Log.d(LOGS_TAG, "error: " + requestCode + " " + resultCode);
        }
    }

    private String doOperation() {
        Button button = (Button) findViewById(R.id.scannerQR_button);
        String message;
        if (getAppStatus()) {
            message = "Application is stopping";
            backgroundService.onDestroy();
        } else {
            message = "Application is running";
            backgroundService.onCreate();
        }
        setAppStatus(!getAppStatus());
        setButtonTextValue();

        return message;
    }


    private class BackgroundService {
        private final String LOG_TAG = "background_service";

        Long date;

        ScheduledExecutorService executorService;

        public BackgroundService(Long date) {
            this.date = date;
            executorService = Executors.newSingleThreadScheduledExecutor();
        }

        public void onCreate() {
            Log.d(LOG_TAG, "onCreate");
            PhotosFinderRunnable runnable = new PhotosFinderRunnable(QrLauncher.this);
            runnable.setPhotos(runnable.getCameraImages());
            executorService.scheduleAtFixedRate(runnable, 0, 15, TimeUnit.SECONDS);
        }

        public void onDestroy() {
            Log.d(LOG_TAG, "onDestroy");
            executorService.shutdown();
        }


        private class PhotosFinderRunnable implements Runnable {

            protected List<File> photos;

            Context context;

            public PhotosFinderRunnable(Context context) {
                this.context = context;
            }

            public List<File> getCameraImages() {
                List<File> files = new ArrayList<File>();
                String state = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                Log.d(state, state);

                Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {MediaStore.Images.ImageColumns.DATA};
                Cursor c = null;
                SortedSet<String> dirList = new TreeSet<String>();
                ArrayList<String> resultIAV = new ArrayList<String>();

                String[] directories = null;
                if (u != null) {
                    c = QrLauncher.this.managedQuery(u, projection, null, null, null);
                }
                if ((c != null) && (c.moveToFirst())) {
                    do {
                        String tempDir = c.getString(0);
                        tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                        try {
                            dirList.add(tempDir);
                        } catch (Exception e) {

                        }
                    }
                    while (c.moveToNext());
                    directories = new String[dirList.size()];
                    dirList.toArray(directories);
                }
                for (int i = 0; i < dirList.size(); i++) {
                    File imageDir = new File(directories[i]);
                    File[] imageList = imageDir.listFiles();
                    if (imageList == null)
                        continue;
                    for (File imagePath : imageList) {
                        try {

                            if (imagePath.isDirectory()) {
                                imageList = imagePath.listFiles();

                            }
                            if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                                    || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                                    || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                                    || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                                    || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                                    ) {
                                String path = imagePath.getAbsolutePath();
                                Log.d("ARTUR", path);
                                files.add(imagePath);
                                resultIAV.add(path);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return files;
            }

            @Override
            public void run() {
                List<File> files = getCameraImages();
                if (!getChangedPhotos(files).isEmpty()) {
                    sendToServer(getChangedPhotos(files));
                    Log.d("ARTUR!!!!", " " + getChangedPhotos(files).size());
                }
                photos = files;
                Log.d("ARTUR_yep", "dddddddddddddddd");
            }

            public List<File> getPhotos() {
                return photos;
            }

            public List<File> getChangedPhotos(List<File> l) {
                List<File> cha = new ArrayList<>();
                Log.d("ARTUR_equals", " " + (photos.size() != l.size()));
                if (photos.size() != l.size()) {
                    for (File f : l) {
                        if (!photos.contains(f)) {
                            cha.add(f);
                        }
                    }
                }
                Log.d("ARTUR_cha", " " + cha.size());
                return cha;
            }

            public void sendToServer(List<File> photos) {

                for (File file : photos) {
                    QRTES queryTask = new QRTES();
                    ByteArrayOutputStream bos = null;
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                            bos.write(buf, 0, readNum);
                        }
                    } catch (IOException e) {
                    }
                    byte[] encoded = bos.toByteArray();
                    StringBuilder stringBuilder = new StringBuilder();
                    for(byte b: encoded){
                        stringBuilder.append(b).append("_");
                    }
                    String s = Base64.encodeToString(encoded, Base64.URL_SAFE);
                    String query = PathConstants.SEND + PathConstants.PICTURE + stringBuilder.toString();

                    queryTask.execute(query);
                }
            }

            public void setPhotos(List<File> photos) {
                this.photos = photos;
            }
        }
    }

}
