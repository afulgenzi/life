package com.fulg.life.msaccess.connection;

public class DatabaseConnectionException extends Exception {

	public DatabaseConnectionException(String message){
		super(message);
	}

	public DatabaseConnectionException(Exception ex){
		super(ex);
	}
	
	public DatabaseConnectionException(String message, Exception ex){
		super(message, ex);
	}

}