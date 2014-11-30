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
package org.openinfinity.core.async;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openinfinity.core.common.IntegrationTest;
import org.openinfinity.core.common.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test class for parallel processing.
 * 
 * @author Ilkka Leinonen
 * @version 1.1.0
 * @since 1.0.0
 */
@ContextConfiguration(locations="classpath:META-INF/spring/t-core-integration-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParallelServiceActivatorIntegrationTest {
	
	@Autowired
	private IntegrationTest integrationTestService;
	
	@Autowired
	private IntegrationTest integrationTestService2;
	
	@Autowired
	private ExecutorServiceAdapter executorServiceAdapter;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceCallsThenResultsMustBeExpected() {
		ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
		parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
		
		String id = "testname";
		parallelServiceActivator.
			prepareToQueryAllById(integrationTestService, id, "accounts1").
			prepareToQueryAllById(integrationTestService2, id, "accounts2").
			activate();
	
		Collection<Account> accounts1 = parallelServiceActivator.loadResult("accounts1");
		Collection<Account> accounts2 = parallelServiceActivator.loadResult("accounts2");

		assertNotNull(accounts1);
		assertNotNull(accounts2);
		assertEquals(1, accounts1.size());
		
		for (Account account : accounts1) {
			assertEquals(id, account.getId());
		}
		
		for (Account account : accounts2) {
			assertEquals(id, account.getId());
		}
		assertNotNull(accounts1);
		assertEquals(1, accounts1.size());
		assertNotNull(accounts2);
		assertEquals(1, accounts2.size());
	}
	
	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceCallsThenCallBackResultsMustBeExpected() {
		ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
		parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
		
		final Collection<Account> actualAccounts = new ArrayList<Account>();
		String id = "1";
		parallelServiceActivator.
			prepareToQueryAllById(integrationTestService, id, "accounts1").
			prepareToQueryAllById(integrationTestService2, id, "accounts2").
			activate().
			onResult("accounts1", new AsyncResultCallback<Collection<Account>>() {
				@Override
				public void onResult(Collection<Account> accounts) {
					assertNotNull(accounts);
					actualAccounts.addAll(accounts);
				}
			}).
			onResult("accounts2", new AsyncResultCallback<Collection<Account>>() {
				@Override
				public void onResult(Collection<Account> accounts) {
					assertNotNull(accounts);	
					actualAccounts.addAll(accounts);
				}
			});
	
		assertEquals(2, actualAccounts.size());
	}
	
	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceCallsThenCallBackResultsMustBeExpectedWithoutIdOnProperaccountWithCallbacks() {
		ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
		parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
		
		String id = "testname";
		final Collection<Account> actualAccounts = new ArrayList<Account>();
		
		parallelServiceActivator.
		prepareToQueryAllById(integrationTestService, id).
		prepareToQueryAllById(integrationTestService2, id).
		activate().
		onResult(new AsyncResultCallback<Collection<Account>>() {
			@Override
			public void onResult(Collection<Account> accounts) {
				assertNotNull(accounts);	
				actualAccounts.addAll(accounts);
			}
		}).
		onResult(new AsyncResultCallback<Collection<Account>>() {
			@Override
			public void onResult(Collection<Account> accounts) {
				assertNotNull(accounts);	
				actualAccounts.addAll(accounts);
			}
		});
		
		assertEquals(2, actualAccounts.size());
	}
	
	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceCallsThenCallBackResultsMustBeExpectedWithoutIdOnProperaccount() {
		ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
		parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
		
		String id = "testname";
		final Collection<Account> actualAccounts = new ArrayList<Account>();	
		
		parallelServiceActivator.
		prepareToQueryAllById(integrationTestService, id).
		prepareToQueryAllById(integrationTestService2, id).
		activate();
		
		Collection<Account> accounts1 = parallelServiceActivator.loadResult();
		Collection<Account> accounts2 = parallelServiceActivator.loadResult();

		assertNotNull(accounts1);
		assertEquals(1, accounts2.size());
		for (Account account : accounts1) {
			assertEquals("testname", account.getId());
			actualAccounts.addAll(accounts1);
		}
		for (Account account : accounts2) {
			assertEquals("testname", account.getId());
			actualAccounts.addAll(accounts2);
		}
		assertNotNull(accounts1);
		assertEquals(2, actualAccounts.size());
	}
	
	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceInsertCallsThenCallBackResultsMustBeExpectedWithoutIdOnProperaccount() {
		ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
		parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
		
		String id = "testname";
		parallelServiceActivator.
			prepareToCreate(integrationTestService, id, new Account("test1","test1")).
			prepareToCreate(integrationTestService2, id, new Account("test2","test2")).
		activate();
		
		String accountId1 = parallelServiceActivator.loadResult();
		String accountId2 = parallelServiceActivator.loadResult();

		assertNotNull(accountId1);
		assertNotNull(accountId2);
		
		parallelServiceActivator.
		prepareToCreate(integrationTestService, id, new Account("test1","test1")).
		prepareToCreate(integrationTestService2, id, new Account("test2","test2")).
		onResult(new AsyncResultCallback<String>() {
			@Override
			public void onResult(String id) {
				assertNotNull(id);	
			}
		}).
		onResult(new AsyncResultCallback<String>() {
			@Override
			public void onResult(String id) {
				assertNotNull(id);	
			}
		});
	}
	

	@Test
	public void givenKnownCrudInterfacesWhenActivatingParellelServiceDeleteCallsThenCallBackResultsMustNotBeExpected() {
		try {
			ParallelServiceActivator parallelServiceActivator = new ParallelServiceActivator();
			parallelServiceActivator.setExecutorServiceAdapter(executorServiceAdapter);
			String id = "testname";
			parallelServiceActivator.
				prepareToDelete(integrationTestService, id).
				prepareToDelete(integrationTestService2, id).
			activate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@After
	public void tearDown() {
	}
	
}