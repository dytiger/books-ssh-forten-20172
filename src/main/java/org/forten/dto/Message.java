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

	private enum MsgType {
		ERROR("error"), WARN("warn"), INFO("info");

		private String des;

		private MsgType(String des) {
			this.des = des;
		}

		public String getDes() {
			return des;
		}
	}
}
