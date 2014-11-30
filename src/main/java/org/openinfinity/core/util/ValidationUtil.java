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

@Component
public class ValidationUtil {

	private static final String DEFAUL_VALIDATION_MESSAGE = "Object validation challenge noticed errors.";
	
	@Autowired
	private Validator validator;
	
	public void throwBusinessViolationExceptionOnFailure(Object validationObject) {
		BusinessViolationException businessViolationException = new BusinessViolationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, ExceptionLevel.ERROR);	
	}
	
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		BusinessViolationException businessViolationException = new BusinessViolationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		BusinessViolationException businessViolationException = new BusinessViolationException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	public void throwBusinessViolationExceptionOnFailure(Object validationObject, BusinessViolationException businessViolationException, ExceptionLevel exceptionLevel) {
		throwCoreExceptionOnValidationChallengeFailure(validationObject, businessViolationException, exceptionLevel);
	}
	
	public void throwSystemExceptionOnFailure(Object validationObject) {
		SystemException systemException = new SystemException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, ExceptionLevel.ERROR);	
	}
	
	public void throwSystemExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		SystemException systemException = new SystemException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	public void throwSystemExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		SystemException systemException = new SystemException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	public void throwSystemExceptionOnFailure(Object validationObject, SystemException systemException, ExceptionLevel exceptionLevel) {
		throwCoreExceptionOnValidationChallengeFailure(validationObject, systemException, exceptionLevel);
	}
	
	public void throwApplicationExceptionOnFailure(Object validationObject) {
		ApplicationException applicationException = new ApplicationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, ExceptionLevel.ERROR);	
	}
	
	public void throwApplicationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel) {
		ApplicationException applicationException = new ApplicationException(DEFAUL_VALIDATION_MESSAGE);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, exceptionLevel);
		
	}
	
	public void throwApplicationExceptionOnFailure(Object validationObject, ExceptionLevel exceptionLevel, String message) {
		ApplicationException applicationException = new ApplicationException(message);
		throwCoreExceptionOnValidationChallengeFailure(validationObject, applicationException, exceptionLevel);
	}
	
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
