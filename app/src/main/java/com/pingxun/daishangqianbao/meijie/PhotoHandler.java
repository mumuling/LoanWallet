package com.pingxun.daishangqianbao.meijie;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.pingxun.daishangqianbao.meijielib.bridge.BridgeInterface;
import com.pingxun.daishangqianbao.meijielib.bridge.CallBackFunction;
import com.pingxun.daishangqianbao.meijielib.bridge.DefaultHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by hbl on 2017/5/11.
 */

public class PhotoHandler extends DefaultHandler {
    String path = "";
    CallBackFunction function;
    Activity activity;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void handler(String data, CallBackFunction function, BridgeInterface bridgeInterface) {
        /**
         * 开启相机，添加权限和Android N 的FileProvider，避免冲突使用DemoFileProvider
         */
        activity = bridgeInterface.getActivity();
        this.function = function;
        if(Build.VERSION.SDK_INT >=23){
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                        && activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("请开启相机及相关权限，以便能打开摄像头拍照");
                    builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);

                        }
                    });
                    builder.create().show();
                } else {
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                }
                return;
            }

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("请开启相机权限，以便能打开相机");
                    builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 103);

                        }
                    });
                    builder.create().show();
                } else {
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 103);
                }
                return;
            }
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("请开启存储权限，以便能保存照片");
                    builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);

                        }
                    });
                    builder.create().show();
                } else {
                    activity.requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);
                }
                return;
            }
            //针对小米手机的权限检查
            int MODEC = MIUIUtil.checkAppops(activity, AppOpsManager.OPSTR_CAMERA);
            if (MODEC == MIUIUtil.MODE_ASK) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 103);
            } else if (MODEC == MIUIUtil.MODE_IGNORED) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                builder.setMessage("请开启相机权限，以便能对您进行拍照");
                builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    enterSetting();
                        sysPermissionSetting();
                    }
                }).create().show();
            }
            //针对小米手机的权限检查
            int MODEW = MIUIUtil.checkAppops(activity, AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE);
            if (MODEW == MIUIUtil.MODE_ASK) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);
            } else if (MODEW == MIUIUtil.MODE_IGNORED) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                builder.setMessage("请开启存储权限，以便能存储数据");
                builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    enterSetting();
                        sysPermissionSetting();
                    }
                }).create().show();
            }
        }
        path = Environment.getExternalStorageDirectory() + "/Demo/" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(path);
        if (!imageFile.exists()) {
            imageFile.getParentFile().mkdirs();
        }
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = DemoFileProvider.getUriForFile(activity, activity.getPackageName() + ".demoprovider", imageFile);
        } else {
            imageUri = Uri.fromFile(imageFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        bridgeInterface.startActivityForResult(this, intent, 101);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            String s = getBase64FromBitmap(path);
            function.onCallBack(s);
        }
    }

    /**
     * 将文件路径转换为Base64的图片编码，只进行尺寸压缩
     *
     * @param path
     * @return
     */
    private String getBase64FromBitmap(String path) {
        Bitmap bitmap = BitmapUtil.getBitmap(path, 960, 720);
        if(bitmap.getHeight()>bitmap.getWidth()){
            bitmap= BitmapUtil.rotate(bitmap,-90,bitmap.getWidth()/2f,bitmap.getHeight()/2,true);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        Log.d("TAG", "getBase64FromBitmap: "+bitmap.getWidth()+"/"+bitmap.getHeight());
        byte[] bytes = baos.toByteArray();
        String s1 = new String(Base64Utils.encode(bytes));
        return s1;
    }

    /**
     * 系统设置
     */
    private void sysPermissionSetting(){
        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }

}