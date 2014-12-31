package net.specialattack.bukkit.core.games.exception;

/**
 * Author: Matt Date: 31 Dec 2014 Time: 5:48:19 pm (C) mbl111 2014
 **/

public class PlaygroundCreateException extends Exception {

	private static final long serialVersionUID = 1L;
	 
	private String reason;

	public PlaygroundCreateException(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

}
