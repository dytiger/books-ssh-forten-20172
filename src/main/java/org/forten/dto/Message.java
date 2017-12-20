package org.forten.dto;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msg;
    private MsgType type;

    private Message(String msg, MsgType type) {
        super();
        this.msg = msg;
        this.type = type;
    }

    public static Message error(String msg) {
        return new Message(msg, MsgType.ERROR);
    }

    public static Message warn(String msg) {
        return new Message(msg, MsgType.WARN);
    }

    public static Message info(String msg) {
        return new Message(msg, MsgType.INFO);
    }

    public String getMsg() {
        return msg;
    }

    public MsgType getType() {
        return type;
    }

    public String getTypeDes() {
        return type.getDes();
    }
    public String getTypeChineseDes() {
        return type.getChineseDes();
    }

    private enum MsgType {
        ERROR("error","错误"), WARN("warn","警告"), INFO("info","信息");

        private String des;
        private String chineseDes;

        private MsgType(String des, String chineseDes) {
            this.des = des;
            this.chineseDes = chineseDes;
        }

        public String getDes() {
            return des;
        }

        public String getChineseDes() {
            return chineseDes;
        }
    }
}
