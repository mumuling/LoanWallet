package com.pingxun.daishangqianbao.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * FIleUtil
 */
public class FileUtil {

    private Context context;

    private FileUtil(Context context) {
        this.context=context;
    }

    private static FileUtil fileUtil;
    public synchronized static FileUtil getInstance(Context context) {
        if (fileUtil == null) {
            return new FileUtil(context);
        }else{
            return fileUtil;
        }
    }

    private  final String LOCAL = "watermark";
    /**
     * 图片目录
     */
    //public  final String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + LOCAL + File.separator;

    /**
     * 判断是否存在存储空间	 *
     *
     * @return
     */
    public  boolean isExitSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private  boolean hasFile(String fileName) {
        File f = createFile(fileName);
        return null != f && f.exists();
    }

    public File createFile(String fileName) {
        String path=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        File myCaptureFile = new File(path,fileName);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        try {
            myCaptureFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }

    public String getImageFile(String imageName) {
        String path=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File myCaptureFile = new File(path,imageName + ".jpg");
        if (!myCaptureFile.exists()) {
            try {
                myCaptureFile.createNewFile();
                return myCaptureFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return myCaptureFile.getAbsolutePath();
    }

    public Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取压缩后图片的二进制数据

     * @param srcPath
     * @return
     */
    public  byte[] getCompressedImage(String srcPath) {
        if (!new File(srcPath).exists()){
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了

        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

        int be = 1;//be=1表示不缩放

        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放

            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放

            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 15;//每次都减少15
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        return baos.toByteArray();
    }

    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 15;//每次都减少15
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中

        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

    public Bitmap getImage(String srcPath, int width, int height) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了

        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        int realWidth  = newOpts.outHeight;
        int realHeight = newOpts.outWidth;
       // Logger.d("真实图片宽度"+realWidth+" 高度"+realHeight);
        // 计算缩放比&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        int scale = ((realHeight > realWidth ? realHeight : realWidth) / 500);
        if (scale <= 0) {
            scale = 1;
        }
        newOpts.inSampleSize = scale;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

       // Logger.d("缩放比例"+scale);
        newOpts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
       //     Logger.d("缩略图高度：" + h + "宽度:" + w);
            return bitmap;
        } else {
            return null;
        }
    }

    public String saveMyBitmap(Bitmap bmp, String name){
        if (!isExitSDCard()) {
            return "";
        }
        FileOutputStream outputStream = null;
        ByteArrayOutputStream baos=null;
        try {
            outputStream = new FileOutputStream(new File(getImageFile(name)));
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                options -= 25;//每次都减少15
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

                if (options<=0)break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (outputStream != null&&baos!=null) {
                try {
                    baos.writeTo(outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getImageFile(name);
    }

    /**
     * 处理图片旋转角度并生成bitmap
     * @param filePath
     */
    public Bitmap rotateOriginFile(String filePath) {
        Bitmap bitmap =null;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int result = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            int rotate = 0;
            switch(result) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                default:
                    break;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            if (options.outWidth < 0 || options.outHeight < 0) {
                return null;
            }
            if (options.outWidth>options.outHeight)rotate=90;
            options.inJustDecodeBounds = false;
            bitmap=  BitmapFactory.decodeFile(filePath, options);
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            Bitmap rotateBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);
            if(rotateBitmap != null) {
                bitmap.recycle();
                bitmap = rotateBitmap;
            }
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90+"");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 添加水印的方法

     *
     * @param bitmap bitmap对象
     * @param str1    经度信息
     * @param str2    纬度信息
     * @param str3    时间信息
     * @return
     */
    public String addWatermarkBitmap(Bitmap bitmap, String str1, String str2, String str3) {
        int destWidth = bitmap.getWidth();   //此处的bitmap已经限定好宽高

        int destHeight = bitmap.getHeight();
        Bitmap icon = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888); //定好宽高的全彩bitmap
        Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); //建立画笔
        photoPaint.setDither(true); //获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);//过滤一些

        canvas.drawBitmap(bitmap, 0, 0, photoPaint);
        Paint textPaint = new Paint();//设置画笔
        Rect textRect = new Rect();
        float fontDensity=context.getResources().getDisplayMetrics().scaledDensity;
        textPaint.setTextSize(18*fontDensity);//字体大小
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度

        textPaint.setAntiAlias(true);  //抗锯齿

        textPaint.setStrokeWidth(3);
        textPaint.setAlpha(15);
        textPaint.setStyle(Paint.Style.FILL); //实心
        textPaint.setColor(Color.GREEN);//采用的颜色

        int totalSpace=20;
        textPaint.getTextBounds(str3, 0, str3.length(), textRect);
        int textWidth = textRect.width();
        int textHeight = textRect.height();
        int left=destWidth-totalSpace-textWidth;
        for (int i=0;i<3;i++){
            if (i==0){
                canvas.drawText(str1,left,textHeight+totalSpace,textPaint);
            }else if(i==1){
                canvas.drawText(str2,left,(textHeight+totalSpace)*2,textPaint);
            }else if(i==2){
                canvas.drawText(str3,left,(textHeight+totalSpace)*3,textPaint);
            }
        }
        //textPaint.setShadowLayer(1f, 0f, 3f, Color.LTGRAY);
        //textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(android.R.color.white));//影音的设置

        //canvas.save(Canvas.ALL_SAVE_FLAG);
        //canvas.restore();
        bitmap.recycle();
//        ivPhoto1.setImageBitmap(icon);
        String fileName="IMG_"+new SimpleDateFormat("yyyyMMDDHHmmss", Locale.US).format(new Date());
        return FileUtil.getInstance(context).saveMyBitmap(icon, fileName); //保存至文件

//        return true;
    }

    //Create a thumbnail video bitmap
    @SuppressLint("NewApi")
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap output = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            Bitmap bitmap = retriever.getFrameAtTime(400 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawBitmap(bitmap, rect, rect, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            canvas.drawCircle(bitmap.getWidth()/2-5,bitmap.getHeight()/2,60,paint);
            Path path = new Path();
            path.moveTo(bitmap.getWidth()/2-25, bitmap.getHeight()/2-25);// 此点为多边形的起点

            path.lineTo(bitmap.getWidth()/2+25, bitmap.getHeight()/2);
            path.lineTo(bitmap.getWidth()/2-25, bitmap.getHeight()/2+25);
            path.close(); // 使这些点构成封闭的多边形
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPath(path,paint);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * 写入文件
     * @param saveFile
     * @param fileName
     * @param saveString
     */
    public static void inputTXTInfo(File saveFile, String fileName, String saveString){
        FileOutputStream fos = null;
        try {
            File file = new File(saveFile, fileName);
            if (file.exists()&&file.delete()){}
            fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            // 判断SDCard是否可写
            if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
                // 写入文件内容
                ps.print(saveString);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readTXTStr(File file){
        FileInputStream fis = null;
        if (!file.exists()||(file.isFile()&&file.length()==0))return "";
        StringBuilder sb=new StringBuilder("");
        try {
            fis = new FileInputStream(file);
            // 建立缓存字节数组
            byte[] bytes = new byte[1024];
            // 保存读取的字符串
            sb = new StringBuilder();
            // 每次读取的长度

            int length;
            // 读取文件
            while ((length = fis.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, length));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
