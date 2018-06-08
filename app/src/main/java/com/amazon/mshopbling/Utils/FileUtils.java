package com.amazon.mshopbling.Utils;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static List<File> prepareFileList(String folderPrefix) {
        List<File> fileList = new ArrayList<>();
        File screenshotsFolder = new File(Environment.getExternalStorageDirectory().getPath() + folderPrefix);
        File[] allFileList = screenshotsFolder.listFiles();
        Arrays.sort(allFileList, Collections.<File>reverseOrder());
        for (int i=0; i<allFileList.length; i++) {
            File file = allFileList[i];
            if(!file.getName().contains("tmp")){
                fileList.add(file);
            }
        }
        return fileList;
    }
}
