package com.foundercy.pf.control;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ControlException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 5554508020351111333L;

public ControlException() {
  }

  public ControlException(String message) {
    super(message);
  }

  public ControlException(String message, Throwable cause) {
    super(message, cause);
  }

  public ControlException(Throwable cause) {
    super(cause);
  }
}