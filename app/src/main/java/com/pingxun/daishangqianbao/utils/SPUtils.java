package com.pingxun.daishangqianbao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Administrator on 2015/11/2.
 * 存储用户信息的SharedPreferences实体类
 */
public class SPUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SPUtils(Context context) {
        if (context != null) {
            sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            editor = sp.edit();
        }
    }

    /**
     * 清空保存在默认SharePreference下的所有数据


     */
    public void clear() {
        editor.clear().commit();
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public Map<String, ?> getAll(Context context) {
        if (context != null) {
            sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        }
        return sp.getAll();
    }
    /**
     * 是否记住登录名


     */
    public boolean getIsRemember() {
        if (sp != null) {
            return sp.getBoolean("isremember", false);
        } else {
            return false;
        }
    }

    public void setIsRemember(boolean isremember) {
        if (editor != null) {
            editor.putBoolean("isremember", isremember);
            editor.commit();
        }
    }







    /**
     * 是否登录
     */
    public boolean getIsLogin() {
        if (sp != null) {
            return sp.getBoolean("islogin", false);
        } else {
            return false;
        }
    }

    public void setIsLogin(boolean islogin) {
        if (editor != null) {
            editor.putBoolean("islogin", islogin);
            editor.commit();
        }
    }

    /**
     * 用户Base64字符串


     *
     * @param base64Str
     */
    public void setBase64Str(String base64Str) {
        if (editor != null) {
            editor.putString("base64Str", base64Str);
            editor.commit();
        }
    }

    public String getBase64Str() {
        if (sp != null) {
            return sp.getString("base64Str", "");
        } else {
            return "";
        }
    }


    /**
     * 用户ID
     */
    public void setUserId(int nID) {
        if (editor != null) {
            editor.putInt("nID", nID);
            editor.commit();
        }
    }

    public int getUserId() {
        if (sp != null) {
            return sp.getInt("nID", 0);
        } else {
            return 0;
        }
    }

    /**
     * 用户类别
     * @param nType
     */
    public void setUserNtype(int nType) {
        if (editor != null) {
            editor.putInt("nType", nType);
            editor.commit();
        }
    }

    public int getUserNtype() {
        if (sp != null) {
            return sp.getInt("nType", 0);
        } else {
            return 0;
        }
    }



    /**
     * 用户姓名
     */
    public void setUserName(String userNameStr) {
        if (editor != null) {
            editor.putString("userNameStr", userNameStr);
            editor.commit();
        }
    }

    public String getUserName() {
        if (sp != null) {
            return sp.getString("userNameStr", "");
        } else {
            return "";
        }
    }

    /**
     * 用户登录名
     */
    public void setUserLoginStr(String login) {
        if (editor != null) {
            editor.putString("userLogin", Base64Util.encode(login));
            editor.commit();
        }
    }

    public String getUserLoginStr() {
        if (sp != null) {

            return Base64Util.decode(sp.getString("userLogin", ""));
        } else {
            return "";
        }
    }

    /**
     * 用户密码
     */
    public void setUserPwd(String pwd) {
        if (editor != null) {
            editor.putString("userPwd", Base64Util.encode(pwd));
            editor.commit();
        }
    }

    public String getUserPwd() {
        if (sp != null) {
            return Base64Util.decode(sp.getString("userPwd", ""));
        } else {
            return "";
        }
    }


    /**
     * 用户部门编号
     */
    public void setUserDeptFlag(String DeptFlagStr) {
        if (editor != null) {
            editor.putString("DeptFlagStr", Base64Util.encode(DeptFlagStr));
            editor.commit();
        }
    }

    public String getUserDeptFlag() {
        if (sp != null) {
            return Base64Util.decode(sp.getString("DeptFlagStr", ""));
        } else {
            return "";
        }
    }

    /**
     * 用户公司编号
     */
    public void setUserUnitFlag(String UnitFlagStr) {
        if (editor != null) {
            editor.putString("UnitFlagStr", Base64Util.encode(UnitFlagStr));
            editor.commit();
        }
    }

    public String getUserUnitFlag() {
        if (sp != null) {
            return Base64Util.decode(sp.getString("UnitFlagStr", ""));
        } else {
            return "";
        }
    }

    /**
     * 用户部门名称
     */
    public void setUserUnitName(String UnitNameStr) {
        if (editor != null) {
            editor.putString("UnitNameStr", Base64Util.encode(UnitNameStr));
            editor.commit();
        }
    }

    public String getUserUnitName() {
        if (sp != null) {
            return Base64Util.decode(sp.getString("UnitNameStr", ""));
        } else {
            return "";
        }
    }


//    public void setSKind(String sKind) {
//        if (editor != null) {
//            editor.putString("sKind", sKind);
//            editor.commit();
//        }
//    }
//
//    public String getSKind() {
//        if (sp != null) {
//            return sp.getString("sKind", "");
//        } else {
//            return "";
//        }
//    }

    /**
     * 保存http类型

     */
//    public void setHttpKind(String HttpKindStr) {
//        if (editor != null) {
//            editor.putString("HttpKindStr", HttpKindStr);
//            editor.commit();
//        }
//    }
//
//    public String getHttpKind() {
//        if (sp != null) {
//            return sp.getString("HttpKindStr", "");
//        } else {
//            return "";
//        }
//    }

    /**
     * 保存服务器Ip
     * @param ServerIPStr
     */
//    public void setServerIP(String ServerIPStr) {
//        if (editor != null) {
//            editor.putString("ServerIPStr", ServerIPStr);
//            editor.commit();
//        }
//    }
//
//    public String getServerIP() {
//        if (sp != null) {
//            return sp.getString("ServerIPStr", "");
//        } else {
//            return "";
//        }
//    }


    /**
     * 保存服务器端口


     * @param ServerPortStr
     */
//    public void setServerPort(String ServerPortStr) {
//        if (editor != null) {
//            editor.putString("ServerPortStr", ServerPortStr);
//            editor.commit();
//        }
//    }
//
//    public String getServerPort() {
//        if (sp != null) {
//            return sp.getString("ServerPortStr", "");
//        } else {
//            return "";
//        }
//    }


    /**
     * 保存URL
     * @param sUrl
     */
//    public void setURL(String sUrl) {
//        if (editor != null) {
//            editor.putString("sUrl", sUrl);
//            editor.commit();
//        }
//    }
//
//    public String getURL() {
//        if (sp != null) {
//            return sp.getString("sUrl", "");
//        } else {
//            return "";
//        }
//    }



    /**
     * 保存到达播报米数
     */
    public void setArrive(String Arrive) {
        if (editor != null) {
            editor.putString("Arrive", Arrive);
            editor.commit();
        }
    }

    public String getArrive() {
        if (sp != null) {
            return sp.getString("Arrive", "");
        } else {
            return "";
        }
    }

    /**
     * 保存减速播报米数
     */
    public void setDeceleration(String Deceleration) {
        if (editor != null) {
            editor.putString("Deceleration", Deceleration);
            editor.commit();
        }
    }

    public String getDeceleration() {
        if (sp != null) {
            return sp.getString("Deceleration", "");
        } else {
            return "";
        }
    }


}