package com.foundercy.pf.control;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 北京世纪新干线</p>
 * @author fangyi
 * @version 1.0
 */

public class ControlAttributeException extends ControlException {

  /**
	 * 
	 */
	private static final long serialVersionUID = -4866864558751165589L;

	public ControlAttributeException() {
  }

  public ControlAttributeException(String message) {
    super(message);
  }

  public ControlAttributeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ControlAttributeException(Throwable cause) {
    super(cause);
  }
}