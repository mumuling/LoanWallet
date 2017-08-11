package com.pingxun.daishangqianbao.meijie;


import com.pingxun.daishangqianbao.meijielib.bridge.BridgeInterface;
import com.pingxun.daishangqianbao.meijielib.bridge.CallBackFunction;
import com.pingxun.daishangqianbao.meijielib.bridge.DefaultHandler;

/**
 * Created by hbl on 2017/5/11.
 */

public class LcoationHandler extends DefaultHandler {
    @Override
    public void handler(String data, CallBackFunction function, BridgeInterface bridgeInterface) {

        function.onCallBack("location");
    }
}
