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
package org.openinfinity.core.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.openinfinity.core.exception.AbstractCoreException;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Java Bean Validation support utility integrated with core exception behaviour.
 * 
 * @author Ilkka Leinonen
 * @version 1.0.0
 * @since 2.0.0
 */
@Component
public class ValidationUtil {

	/**
	 * Default validation message.
	 */
	private static final String DEFAUL_VALIDATION_MESSAGE = "Object validation challenge noticed errors.";
	
	/**
	 * Represents bean validator.
	 */
	@Autowired
	private Validator validator;
	
	/**
	 * Throws <code>org.openinfinity.core.exception.BusinessViolationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 */
	public void throwBusinessViolationExceptionOnFailure(Object validationObject) {
		BusinessViolationException businessViolationException = new BusinessViolationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, ExceptionLevel.ERROR);	
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.BusinessViolationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		BusinessViolationException businessViolationException = new BusinessViolationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.BusinessViolationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 * @param message Represents the given message when throwing exception.
	 */
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		BusinessViolationException businessViolationException = new BusinessViolationException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.BusinessViolationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param businessViolationException Represents already created exception.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, BusinessViolationException businessViolationException, ExceptionLevel exceptionLevel) {
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.SystemException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 */
	public void throwSystemExceptionOnFailure(Object validationObject) {
		SystemException systemException = new SystemException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, ExceptionLevel.ERROR);	
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.SystemException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwSystemExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		SystemException systemException = new SystemException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.SystemException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 * @param message Represents the given message when throwing exception.
	 */
	public void throwSystemExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		SystemException systemException = new SystemException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.SystemException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param systemException Represents already created exception.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwSystemExceptionOnFailure(Object validationObject, SystemException systemException, ExceptionLevel exceptionLevel) {
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.ApplicationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 */
	public void throwApplicationExceptionOnFailure(Object validationObject) {
		ApplicationException applicationException = new ApplicationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, ExceptionLevel.ERROR);	
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.ApplicationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwApplicationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		ApplicationException applicationException = new ApplicationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, exceptionLevel);		
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.ApplicationException</code> if Bean validator notices validation errors with given parameter.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 * @param message Represents the given message when throwing exception.
	 */
	public void throwApplicationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		ApplicationException applicationException = new ApplicationException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, exceptionLevel);
	}
	
	/**
	 * Throws <code>org.openinfinity.core.exception.ApplicationException</code> if Bean validator notices validation errors with given parameter.
	 * @param validationObject Represents the actual object to be validated.
	 * @param applicationException Represents already created exception.
	 * @param exceptionLevel Represents the given exception level when throwing exception.
	 */
	public void throwApplicationExceptionOnFailure(Object validationObject, ApplicationException applicationException, ExceptionLevel exceptionLevel) {
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, exceptionLevel);
	}
	
	private void throwCoreExceptionOnValidationChallengeFailure(Object validationObject, AbstractCoreException abstractCoreException, ExceptionLevel exceptionLevel) {
		Set<ConstraintViolation<Object>> failures = validator.validate(validationObject);
		if (!failures.isEmpty()) {
			for (ConstraintViolation<Object> failure : failures) {
				abstractCoreException.addExceptionLevelBasedUniqueId(exceptionLevel, failure.getMessage());
			}
		if (failures.size() > 0)
			throw abstractCoreException;
		}
	}

}
