package com.oliek.cartrout.utill;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileCompressor {

    public static File getCompressedImage(Uri imageUri, Activity activity){
        File destination = null;
        if(checkSizeOfFile(imageUri,activity)) {

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


                // this block added by asnad for setting directory of path on 21-8-2016
                File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/aiwadial/upload");
                sd.mkdirs();

                // end here
                destination = new File(sd,
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                Toast.makeText(activity, "Error in image file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(activity, "Error in image file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        return destination;
    }

    private static boolean checkSizeOfFile(Uri imageUri, Activity activity){
        boolean result = true;
        Cursor cursor = activity.getContentResolver().query(imageUri,null, null, null, null);
        cursor.moveToFirst();
        long size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
        if(size>=5000000){
            result=false;
        }
        return result;
    }
}
