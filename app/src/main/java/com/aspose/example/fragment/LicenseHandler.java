package com.aspose.example.fragment;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.aspose.barcode.License;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class LicenseHandler
{
    private final static String TAG = "LicenseHandler";
    String licenseFileName;
    Activity activity;
//        AlertDialog.Builder dialog;

    public LicenseHandler(String licenseFileName, Activity activity)
    {
        this.licenseFileName = licenseFileName;
        this.activity = activity;
    }

    private InputStream readLicenseFile() throws Exception
    {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = assetManager.open(licenseFileName);
        return inputStream;
    }

    public String getLicenseFileContent() throws Exception
    {
        String content = "";
        try (InputStream inputStream = readLicenseFile())
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(licenseFileName).append("\n\n");
            while ((receiveString = bufferedReader.readLine()) != null)
            {
                if (receiveString.equals(""))
                {
                    stringBuilder.append(System.getProperty("line.separator"));
                } else
                {
                    stringBuilder.append(receiveString).append("\n");
                }
            }
            content = stringBuilder.toString();
            Log.d(TAG, content);
            return content;
        }
    }

    public String trySetupLicense() {
        try {
            InputStream inputStream = readLicenseFile();
            try {
                License lic = new License();
                lic.setLicense(inputStream);
                return "License installed";
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            Log.w(TAG, "License not installed: " + e.getMessage());
            return "License not installed: " + e.getMessage();
        }
    }
}
