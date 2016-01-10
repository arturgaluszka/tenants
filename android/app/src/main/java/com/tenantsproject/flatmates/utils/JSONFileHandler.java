package com.tenantsproject.flatmates.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.IOException;

public class JSONFileHandler {
    private final String file;
    private final Context context;
    private static final String OBJECT_NAME = "savedobject";

    public JSONFileHandler(String file, Context context) {
        this.file = file;
        this.context = context;
    }

    public String stringify(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public Object unstringify(String json, Class cls) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, cls);
        return obj;
    }

    // object file saving
    public void save(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        FileOutputStream outputStream;

        try {
            outputStream = this.context.openFileOutput(file, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object load(Class cls) {
        File file = new File(context.getFilesDir(), this.file);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = text.toString();
        //convert to object
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, cls);
        return obj;
    }
}
