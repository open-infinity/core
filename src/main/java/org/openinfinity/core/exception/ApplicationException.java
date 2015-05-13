/*
 * Copyright (c) 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openinfinity.core.exception;

/**
 * This class holds information about application error and extends <code>org.openinfinity.core.exception.AbstractCoreException</code> exception.
 * This exception should be thrown when application specific problems occurs.
 * 
 * @author Ilkka Leinonen
 * @version 1.0.0
 * @since 1.0.0
 */
public class ApplicationException extends AbstractCoreException {

	/**
	 * Exception message thrown when adding exception messages based on message level.
	 */
	private static final String EXCEPTION_MESSAGE_NOT_TYPE_OF_APPLICATION_EXCEPTION = " not of type <code>org.openinfinity.core.exception.ApplicationException</code>.";
	
	/**
	 * Represents the serial version UID.
	 */
	private static final long serialVersionUID = -4066290757599687322L;

	 /** Constructs a new loggable exception with <code>null</code> as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ApplicationException() {
    	super();
    }

    /** Constructs a new loggable exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for 
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ApplicationException(String message) {
    	super(message);
    }

    /**
     * Constructs a new loggable exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Constructs a new loggable exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

	public void addAllExceptionLevelMessages(AbstractCoreException abstractCoreException) {
		if (!(abstractCoreException instanceof ApplicationException)) {
			throw new SystemException(abstractCoreException.getClass().getName() + EXCEPTION_MESSAGE_NOT_TYPE_OF_APPLICATION_EXCEPTION);
		}
		super.addAllExceptionLevelMessages(abstractCoreException);
	}
	
}