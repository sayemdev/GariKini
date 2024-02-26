package com.app.garikini;

import android.net.Uri;

import java.util.List;

public class UriUtils {

    // Function to check if a specific Uri exists in a List<Uri>
    public static boolean doesUriExistInList(Uri targetUri, List<Uri> uriList) {
        for (Uri uri : uriList) {
            if (uri.equals(targetUri)) {
                return true; // Uri found in the list
            }
        }
        return false; // Uri not found in the list
    }
}