package com.example.android.homechat;

public class Message {
    private String msg;

    public Message() { }
    public Message(String msg){
        setMsg(msg);
    }
    public String getMsg(){
        return msg;
    }
    public void setMsg(String newMsg){
        msg = newMsg;
    }
}
