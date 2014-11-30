
/*
 * Copyright (c) 2011-2014 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.common.IntegrationTest;
import org.openinfinity.core.common.domain.Account;
import org.openinfinity.core.exception.AbstractCoreException;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test for <code>org.openinfinity.core.validation.ValidationSupport</code>.
 * 
 * @author Ilkka Leinonen
 * @version 1.0.0
 * @since 2.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/t-core-integration-test-context.xml")
public class ValidationUtilIntegrationTest {
	
	private static final String SCRIPT_FUNCTION = "<script></script>";

	private static final int NOT_SCRIPT_ATTACKS_IN_OBJECT = 3;

	@Autowired
	private IntegrationTest integrationTest;

	@Autowired
	private ValidationUtil validationUtil;
	
	
	@Test 
	//@ExpectedException(ApplicationException.class)
	public void givenAccountPopulatedWithXSSAttackWhenAccessingMethodThenApplicationExceptionMustBeThrown() throws Throwable {
		try {
			Account account = new Account(SCRIPT_FUNCTION,SCRIPT_FUNCTION);
			account.setAddress( SCRIPT_FUNCTION);
			account.setCreationDate(new Date());
			account.setTerminationDate(new Date());
			integrationTest.validateMeAndThrowApplicationException(account);
		} catch(AbstractCoreException abstractCoreException) {
			assertEquals(ApplicationException.class.getName(), abstractCoreException.getClass().getName());
			assertEquals(NOT_SCRIPT_ATTACKS_IN_OBJECT, abstractCoreException.getWarningLevelExceptionIds().size());
		}
	}
	
}


