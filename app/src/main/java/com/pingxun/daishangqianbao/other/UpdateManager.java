//package com.pingxun.daishangqianbao.other;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.FileCallback;
//
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import okhttp3.Call;
//import okhttp3.Response;
//import rx.subscriptions.CompositeSubscription;
//
//
//public class UpdateManager {
//    private String curVersion;
//    private String newVersion;
//    private int curVersionCode;
//    private int newVersionCode;
//    private String updateInfo;
//    private UpdateCallback callback;
//    private final Context ctx;
//
//    private float nProgress;
//    private Boolean haveNewVersion;
//    private Boolean canceled;
//
//    //存放更新APK文件的下载地址
//    private String apkUrl = null;
//
//    // 更新APK文件的名称
//
//    private String apkName = null;
//
//    // 更新APK文件的SHA1校验值
//
//    private String apkChksum = null;
//
//    private static final int UPDATE_CHECKCOMPLETED = 1;
//    private static final int UPDATE_DOWNLOADING = 2;
//    private static final int UPDATE_DOWNLOAD_ERROR = 3;
//    private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
//    private static final int UPDATE_DOWNLOAD_CANCELED = 5;
//
//    protected Toast mToast = null;
//    protected CompositeSubscription mCompositeSubscription;
//
//
//    //从服务器上下载apk存放文件夹
//    private String savefolder = InitDatas.attachmentDownloadPath + "/";
//
//    public UpdateManager(Context context, UpdateCallback updateCallback) {
//        ctx = context;
//        callback = updateCallback;
//        canceled = false;
//        mCompositeSubscription = new CompositeSubscription();
//        getCurVersion();
//    }
//
//    private void getCurVersion() {
//        try {
//            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
//            curVersion = pInfo.versionName;
//            curVersionCode = pInfo.versionCode;
//        } catch (NameNotFoundException e) {
//            curVersion = "1.0";
//            curVersionCode = 999999;
//        }
//    }
//
//    /**
//     * 启动获取升级信息线程
//     *
//     * @return true，升级信息线程已启动；false，启动升级信息线程失败
//     */
//    public boolean checkUpdate(final String filePath) {
//
//        // 升级信息路径
//        final String upgradeInfoUrl = Urls.URL_GET_FIND_PRODUCT_VERSION;
//        haveNewVersion = false;
//        new Thread() {
//
//            @Override
//            public void run() {
//                newVersionCode = -1;
//                newVersion = "";
//                updateInfo = "";
//
//                try {
//                    InitDatas.isHttpUpdate = true;
//                    OkGo.get(upgradeInfoUrl)//
//                            .tag(this)//
//                            .execute(new FileCallback(filePath, "updata.txt") {
//                                @Override
//                                public void onBefore(BaseRequest request) {
//
//                                }
//
//                                @Override
//                                public void onSuccess(File file, Call call, Response response) {
//                                    try {
//                                        BufferedReader br = new BufferedReader(new FileReader(file));
//                                        String readline = "";
//                                        StringBuffer sb = new StringBuffer();
//                                        while ((readline = br.readLine()) != null) {
//                                            sb.append(readline);
//                                        }
//                                        br.close();
//                                        JSONObject jsonObject = new JSONObject(sb.toString());
//                                        newVersionCode = jsonObject.getInt("versionCode");
//                                        newVersion = jsonObject.getString("versionName");
//                                        updateInfo = "";
//                                        apkUrl = jsonObject.getString("packageUrl");
//                                        apkName = jsonObject.getString("packageName");
//                                        apkChksum = jsonObject.getString("packageChecksum");
//
//                                        String upgradeInfo = jsonObject.getString("upgradeInfo");
//                                        StringBuilder builder = new StringBuilder("V");
//                                        builder.append(newVersion);
//                                        builder.append("\n");
//                                        String upgrade = upgradeInfo.replace("|", "\n").toString();
//                                        builder.append(upgrade);
//
//                                        Constant.setUpgradeInfo(builder.toString());
//
//                                        if (newVersionCode > curVersionCode
//                                                && apkUrl != null && !apkUrl.isEmpty()
//                                                && apkName != null && !apkName.isEmpty()
//                                                && apkChksum != null && !apkChksum.isEmpty()) {
//                                            haveNewVersion = true;
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
//                                }
//
//                                @Override
//                                public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                                    // System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
//
//                                }
//
//                                @Override
//                                public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
//                                    super.onError(call, response, e);
//
//                                }
//                            });
//
//                } catch (Exception e) {
//                    Log.e("checkUpdate()", "e: " + e);
//                    updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
//                }
//            }
//        }.start();
//
//        return true;
//    }
//
//    protected String computeSHA1Hash(String path) {
//        String SHA1Hash = null;
//
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            InputStream in = new FileInputStream(path);
//            byte[] buf = new byte[8192];
//            int len = -1;
//            while ((len = in.read(buf)) > 0) {
//                md.update(buf, 0, len);
//            }
//            in.close();
//            byte[] data = md.digest();
//            SHA1Hash = bytesToString(data);
//
//        } catch (NoSuchAlgorithmException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return SHA1Hash;
//    }
//
//    private String bytesToString(byte[] bytes) {
//        StringBuffer sb = new StringBuffer();
//        for (byte b : bytes) {
//            int low = b & 0xF;
//            int high = (b >> 4) & 0xF;
//            sb.append(Character.forDigit(high, 16));
//            sb.append(Character.forDigit(low, 16));
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * 校验APK
//     *
//     * @return
//     */
//    public boolean verifyApk() {
//        String sha1Computed = computeSHA1Hash(savefolder + apkName);
//        Log.d("verifyApk", "sha1Computed: " + sha1Computed);
//
//        return sha1Computed != null && sha1Computed.equals(apkChksum);
//    }
//
//    public void update() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//
//        intent.setDataAndType(Uri.fromFile(new File(savefolder, apkName)),
//                "application/vnd.android.package-archive");
//        ctx.startActivity(intent);
//
//        ((Activity) ctx).finish();
//    }
//
//    private InputStream is;
//    private long length;
//
//
//    /**
//     * 下载安装包
//     */
//    public void downloadPackage() {
//        new Thread() {
//            @Override
//            public void run() {
//
//                try {
//                    Constant.isHttpUpdate = true;
//                    OkGo.get(apkUrl)//
//                            .tag(this)//
//                            .execute(new FileCallback(savefolder, apkName) {
//                                @Override
//                                public void onBefore(BaseRequest request) {
//
//                                }
//
//                                @Override
//                                public void onSuccess(File file, Call call, Response response) {
//                                    updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
//                                }
//
//                                @Override
//                                public void downloadProgress(long currentSize, long totalSize, final float progress, long networkSpeed) {
//                                    System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
//                                    nProgress = progress;
//                                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING));
//
//
//                                }
//
//                                @Override
//                                public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
//                                    super.onError(call, response, e);
//                                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
//                                }
//                            });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, "升级失败！"));
//                }
//            }
//        }.start();
//    }
//
//    public void cancelDownload() {
//        canceled = true;
//    }
//
//    Handler updateHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UPDATE_CHECKCOMPLETED://检查更新完成
//
//                    callback.checkUpdateCompleted(haveNewVersion, newVersion);
//                    break;
//                case UPDATE_DOWNLOADING://正在下载
//                    callback.downloadProgressChanged(nProgress);
//                    break;
//                case UPDATE_DOWNLOAD_ERROR://下载出错
//                    callback.downloadCompleted(false, msg.obj.toString());
//                    break;
//                case UPDATE_DOWNLOAD_COMPLETED:
//                    callback.downloadCompleted(true, "");
//                    break;
//                case UPDATE_DOWNLOAD_CANCELED:
//                    callback.downloadCanceled();
//                default:
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 显示一个Toast信息
//     *
//     * @param content
//     */
//    public void showToast(String content) {
//        if (mToast == null) {
//            mToast = Toast.makeText(ctx, content, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(content);
//        }
//        mToast.show();
//    }
//
//
//    public interface UpdateCallback {
//        void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo);
//
//        void downloadProgressChanged(float progress);
//
//        void downloadCanceled();
//
//        void downloadCompleted(Boolean sucess, CharSequence errorMsg);
//    }
//
//}
