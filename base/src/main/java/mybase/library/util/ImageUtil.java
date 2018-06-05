package mybase.library.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhenghai on 2016/10/20.
 */
public class ImageUtil {
    public static final String UPLOAD_FILE_FOLDER = "/uploads";
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    public static void scalImage(final String path,final CallBack callBack){
        new AsyncTask<String, String,String>(){
            @Override
            protected String doInBackground(String... params) {
                return scalImageProcess( path ).getPath();
            }
            @Override
            protected void onPostExecute(String result) {
                callBack.onScaled( result );
            }
        }.execute();
    }
    public static void scalImages(final ScaleImagsCallBack callBack, final String... paths){
        new AsyncTask<String, String,File[]>(){
            @Override
            protected File[] doInBackground(String... params) {
                return scalImagesProcess( paths );
            }
            @Override
            protected void onPostExecute(File[] result) {
                callBack.onScaled( result );
            }
        }.execute();
    }
    public static File[] scalImagesProcess(String... files){
        File[] fs = new File[files.length];
        for(int i = 0,l = files.length;i<l;i++){
            fs[i] = scalImageProcess( files[i] );
        }
        return fs;
    }
    public static File scalImageProcess(String path){
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        int degree =readPictureDegree(path);
        final long fileMaxSize = 400 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if(degree!=0){
                bitmap = rotateBitmap( bitmap,degree );
            }
            outputFile = new File(createUploadImgFile(path));
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }else{
                File tempFile = outputFile;
                outputFile = new File(createUploadImgFile(path));
                try {
                    copyFileUsingFileStreams(tempFile, outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputFile;
    }
    public static String saveFile(Bitmap bm) throws IOException {
        File myCaptureFile = new File(createUploadImgFile( "/head_"+ System.currentTimeMillis()+".jpg" ));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile.getPath();
    }
    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
    public static void cleanUploads(){
        File file=new File( Environment.getExternalStorageDirectory(),UPLOAD_FILE_FOLDER);
        if(!file.exists()){
            file.mkdirs();
        }
        FileUtils.delAllFile( file.getPath() );
    }
    public static String createUploadImgFile(String path){
        File file=new File( Environment.getExternalStorageDirectory(),UPLOAD_FILE_FOLDER);
        if(!file.exists()){
            file.mkdirs();
        }
        String newPath=file.getPath()+path.substring( path.lastIndexOf( "/" ) );
        return newPath;
    }
    public static void cutImage(Activity activity, Uri uri, int requestCodeCut, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCodeCut);
    }
    public static interface ScaleImagsCallBack{
        public void onScaled(File[] files);
    }

    public static interface CallBack{
        public void onScaled(String filePath);
    }
}
