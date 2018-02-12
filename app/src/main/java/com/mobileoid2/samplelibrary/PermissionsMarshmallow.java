package com.mobileoid2.samplelibrary;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mobileoid2.samplelibrary.Dialog.BeanDialogsPermissions;
import com.mobileoid2.samplelibrary.Dialog.DialogsCustom;

import java.util.ArrayList;


public class PermissionsMarshmallow {

    private final int PERMISSION_ALL = 0;
    private final int PERMISSION_PHONE_STATE = 1;
    private final int PERMISSION_CAMERA = 2;
    private final int PERMISSION_STORAGE = 3;
    private final int PERMISSION_CONTACTS = 4;
    private final int PERMISSION_RECORD_AUDIO = 5;
    public static final int OVERLAY_PERMISSION_REQ_CODE = 1235;

    private Activity activity;
    private boolean proceedFurther = true;
    private String TAG = PermissionsMarshmallow.class.toString();
    private OnActivityResume onActivityResume;

    private ArrayList<BeanDialogsPermissions> permissions;


    public PermissionsMarshmallow(Activity activity, OnActivityResume onActivityResume) {
        this.activity = activity;
        this.onActivityResume = onActivityResume;
    }

    @SuppressLint("MissingPermission")
    public void checkAllPermissions() {

        boolean allPermissionsAvailable = true;
        permissions = new ArrayList<BeanDialogsPermissions>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (!checkPermissionPhoneState()) {
                allPermissionsAvailable = false;
                permissions.add(new BeanDialogsPermissions(activity.getString(R.string.text_call_status), getMessage(PERMISSION_PHONE_STATE)));
            }
            if (!checkPermissionCamera()) {
                allPermissionsAvailable = false;
                permissions.add(new BeanDialogsPermissions(activity.getString(R.string.text_camera), getMessage(PERMISSION_CAMERA)));
            }
            if (!checkPermissionRecordAudio()) {
                allPermissionsAvailable = false;
                permissions.add(new BeanDialogsPermissions(activity.getString(R.string.text_audio_record), getMessage(PERMISSION_RECORD_AUDIO)));
            }
            if (!checkPermissionContacts()) {
                allPermissionsAvailable = false;
                permissions.add(new BeanDialogsPermissions(activity.getString(R.string.text_contacts), getMessage(PERMISSION_CONTACTS)));
            }
            if (!checkPermissionStorage()) {
                allPermissionsAvailable = false;
                permissions.add(new BeanDialogsPermissions(activity.getString(R.string.text_storage), getMessage(PERMISSION_STORAGE)));
            }


            if (permissions.size() > 0)
                DialogsCustom.instance.showPermissionsDialog(activity, permissions, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DialogsCustom.instance.cancelDialog();

                        String[] permissionList = new String[]{
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(activity, permissionList, PERMISSION_ALL);

                    }
                });
        }
        if (allPermissionsAvailable) {
            proceedFurther = false;
            onActivityResume.onResume();
        }
    }

    public interface OnActivityResume {
        void onResume();
    }

    public boolean isProceedFurther() {
        return proceedFurther;
    }

    private boolean checkPermissionPhoneState() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionPhoneState() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
            showMessageOKCancel(getMessage(PERMISSION_PHONE_STATE),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_PHONE_STATE);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });

            // Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_PHONE_STATE);
        }
    }

    private boolean checkPermissionCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            showMessageOKCancel(getMessage(PERMISSION_CAMERA),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        }
    }

    private boolean checkPermissionRecordAudio() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionRecordAudio() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            showMessageOKCancel(getMessage(PERMISSION_RECORD_AUDIO),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
        }
    }

    private boolean checkPermissionStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showMessageOKCancel(getMessage(PERMISSION_STORAGE),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
        }
    }

    private boolean checkPermissionContacts() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionContacts() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
            showMessageOKCancel(getMessage(PERMISSION_CONTACTS),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONTACTS);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONTACTS);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        try {
            new AlertDialog.Builder(activity)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", cancelListener)
                    .create()
                    .show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMessage(int requestCode) {
        switch (requestCode) {

            case PERMISSION_PHONE_STATE:
                return activity.getString(R.string.text_phone_state_description);
            case PERMISSION_CAMERA:
                return activity.getString(R.string.text_record_video_description);
            case PERMISSION_STORAGE:
                return activity.getString(R.string.text_storage_description);
            case PERMISSION_CONTACTS:
                return activity.getString(R.string.text_contacts_description);
            case PERMISSION_RECORD_AUDIO:
                return activity.getString(R.string.text_record_audio_description);
        }
        return "";
    }

}