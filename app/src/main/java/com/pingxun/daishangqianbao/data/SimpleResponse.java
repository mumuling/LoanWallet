
package com.pingxun.daishangqianbao.data;

import java.io.Serializable;

/**
 * 暂时没用到
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public String code;
    public String msg;

    public BaseBean toBase() {
         BaseBean baseBean=new BaseBean();
         baseBean.code=code;
         baseBean.message=msg;
         return baseBean;
    }
}
