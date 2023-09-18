package com.samodule.util;

public class GetORAError {
	public static Throwable getRootCause(Throwable ex) {
	    while (true) {
	        Throwable cause = ex.getCause();
	        if (cause == null) return ex;
	        ex = cause;
	    }
	}
}
