package alwt.hackathon.epam.com.seh_android.service;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import org.w3c.dom.ls.LSInput;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import alwt.hackathon.epam.com.seh_android.constant.PathConstants;
import alwt.hackathon.epam.com.seh_android.server.QRQueryTask;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

//public class BackgroundService{
//    private final String LOG_TAG = "background_service";
//
//    Activity activity;
//
//    Long date;
//
//    Long id;
//
//    ScheduledExecutorService executorService;
//
//    public BackgroundService(Long date, long id) {
//        this.date = date;
//        this.id = id;
//        executorService = Executors.newSingleThreadScheduledExecutor();
//        this.activity = new Activity();
//    }
//
//    public void onCreate() {
//        Log.d(LOG_TAG, "onCreate");
//        PhotosFinderRunnable runnable = new PhotosFinderRunnable(activity);
//        runnable.setPhotos(runnable.getCameraImages());
//        executorService.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
//    }
//
//    public void onDestroy() {
//        Log.d(LOG_TAG, "onDestroy");
//        executorService.shutdown();
//    }
//
//
//    private class PhotosFinderRunnable implements Runnable {
//
//        protected List<File> photos;
//
//        Context context;
//
//        public PhotosFinderRunnable(Context context) {
//            this.context = context;
//        }
//
//        public List<File> getCameraImages() {
//            List<File> files = new ArrayList<File>();
//            String state = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
//            Log.d(state, state);
//
//            Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//            String[] projection = {MediaStore.Images.ImageColumns.DATA};
//            Cursor c = null;
//            SortedSet<String> dirList = new TreeSet<String>();
//            ArrayList<String> resultIAV = new ArrayList<String>();
//
//            String[] directories = null;
//            if (u != null) {
//                c = activity.managedQuery(u, projection, null, null, null);
//            }
//            if ((c != null) && (c.moveToFirst())) {
//                do {
//                    String tempDir = c.getString(0);
//                    tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
//                    try {
//                        dirList.add(tempDir);
//                    } catch (Exception e) {
//
//                    }
//                }
//                while (c.moveToNext());
//                directories = new String[dirList.size()];
//                dirList.toArray(directories);
//            }
//            for (int i = 0; i < dirList.size(); i++) {
//                File imageDir = new File(directories[i]);
//                File[] imageList = imageDir.listFiles();
//                if (imageList == null)
//                    continue;
//                for (File imagePath : imageList) {
//                    try {
//
//                        if (imagePath.isDirectory()) {
//                            imageList = imagePath.listFiles();
//
//                        }
//                        if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
//                                || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
//                                || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
//                                || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
//                                || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
//                                ) {
//                            String path = imagePath.getAbsolutePath();
//                            Log.d("ARTUR", path);
//                            files.add(imagePath);
//                            resultIAV.add(path);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return files;
//        }
//
//        @Override
//        public void run() {
//            if(getChangedPhotos().size() > 0){
//                sendToServer(getChangedPhotos());
//            }
//            photos = getCameraImages();
//            Log.d("ARTUR_yep", "dddddddddddddddd");
//        }
//
//        public List<File> getPhotos() {
//            return photos;
//        }
//
//        public List<File> getChangedPhotos() {
//            List<File> cha = new ArrayList<>();
//            List<File> l = getCameraImages();
//            if(photos.size() != l.size()){
//                for(File f: l){
//                    if(!l.contains(f)){
//                        cha.add(f);
//                    }
//                }
//            }
//            return cha;
//        }
//
//        public void sendToServer(List<File> photos){
//            String query = PathConstants.CHECK_QR;
//            QRQueryTask queryTask = new QRQueryTask();
//
//            HttpClient client = new DefaultHttpClient();
//            HttpPost postMethod = new HttpPost();
//
////            MultipartEntity entity = new MultipartEntity();
////
////            for(File file : photos) {
////                Log.d("log", "VALIDATE CONTENT");
////                FileBody contentFile = new FileBody(file);
////                entity.addPart("userfile",contentFile);
////                StringBody contentString = new StringBody("This is contentString");
////                entity.addPart("contentString",contentString);
////
////                postMethod.setEntity(entity);
////                client.execute(postMethod);
////            }
//        }
//
//        public void setPhotos(List<File> photos) {
//            this.photos = photos;
//        }
//    }
//}
