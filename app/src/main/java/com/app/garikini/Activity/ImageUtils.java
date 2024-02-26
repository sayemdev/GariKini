package com.app.garikini.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtils {

    public static void compressImageLosslessly(Context context, Uri imageUri) {
        try {
            // Decode the image from the Uri
            Bitmap originalBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));

            // Create the output directory in the "Pictures" directory
            File outputDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CompressedImages");

            // Check if the directory exists, if not, create it
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            // Create an output file for the compressed image
            String outputFileName = "compressed_image.png";
            File outputFile = new File(outputDirectory, outputFileName);

            // Create an output stream to write the compressed image
            OutputStream outputStream = new FileOutputStream(outputFile);

            // Compress the image losslessly using PNG format
            originalBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // Close the output stream
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
