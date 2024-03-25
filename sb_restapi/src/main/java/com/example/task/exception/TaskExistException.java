package com.example.task.exception;

public class TaskExistException extends RuntimeException{
	public TaskExistException (String msg)
	{
		super(msg);
	}

}
