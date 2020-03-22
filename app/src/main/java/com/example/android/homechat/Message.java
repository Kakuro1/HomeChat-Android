package com.example.android.homechat;

public class Message {
    private String msg;

    public String getSender() {
        if(sender == null)
            return "anonymous";
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sender;

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
