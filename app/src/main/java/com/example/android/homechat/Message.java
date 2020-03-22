package com.example.android.homechat;

public class Message {
    public String msg;

    // default constructor for Firebase de-/serializing
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
