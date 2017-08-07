package com.pingxun.daishangqianbao.other;

/**
 * Created by LH on 2016/6/15.
 *
 */
public class EventMessage {
    public String message;
    public String id;
    public int status;

    public EventMessage(String message) {
        this.message = message;
    }

    public EventMessage(String message, int status) {
        this.message = message;
        this.status=status;
    }
    public EventMessage(String message, String id, int status) {
        this.message = message;
        this.id=id;
        this.status=status;
    }

}
