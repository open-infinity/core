/*
 * Copyright (c) 2012-2015 the original author or authors.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.exception.SystemException;
import org.openinfinity.core.integration.CrudService;
import org.openinfinity.core.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.stereotype.Component;

/**
 * This class is responsible of high end optimized CRUD service interface integrations by parallel processing. Class can be utilized for service orchestration by parallel processing.
 * 
 * @author Ilkka Leinonen
 * 
 * @version 1.0.0
 * @since 2.0.0
 */
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ParallelServiceActivator {

	/**
	 * Executor service is responsible of maintaining the actual thread pool. Thread pool can be configured by dependency injection.
	 */
	@Autowired
	private ExecutorServiceAdapter executorServiceAdapter;
	
	/**
	 * Sets the Spring Framework's executor service.
	 * 
	 * @param executorServiceAdapter
	 */
	public void setExecutorServiceAdapter(ExecutorServiceAdapter executorServiceAdapter) {
		this.executorServiceAdapter = executorServiceAdapter;
	}

	/**
	 * Collection of callable interfaces.
	 */
	private Collection<Callable<Object>> callables;
	
	/**
	 * Result cache of the responses.
	 */
	private Map<String, Loadable> resultCache;
	
	/**
	 * Result presented as linked list.
	 */
	private LinkedList<Loadable> resultQueue;
	
	/**
	 * Constructor for the class. Initiates collection interfaces of the class.
	 */
	public ParallelServiceActivator() {
		this.callables = Collections.checkedCollection(new ArrayList(), Callable.class);
		this.resultCache = new HashMap<String, Loadable>();
		this.resultQueue = new LinkedList<Loadable>();
	}
	
	/**
	 * Parallel 'query all' interface by id. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual unique id to be queried.
	 * @return The type safe collection.
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryAllById(CrudService<T, ID> crudService, ID id) {
		GenericCrudServiceQueryAllByIdCallable<T, ID> result = new GenericCrudServiceQueryAllByIdCallable<T, ID>(crudService, id);
		callables.add(result);
		this.resultQueue.add(result);
		return this; 
	}
	
	/**
	 * Parallel 'query by id' interface by id. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual unique id to be queried.
	 * @return The type safe collection.
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryById(CrudService<T, ID> crudService, ID id) {
		GenericCrudServiceQueryByIdCallable<T, ID> result = new GenericCrudServiceQueryByIdCallable<T, ID>(crudService, id);
		callables.add(result);
		this.resultQueue.push(result);
		return this; 
	}
	
	/**
	 *  Parallel 'create' interface with type safe entity. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual type of the unique id.
	 * @param type Represents the actual type safe.
	 * @return Type safe entity.
	 */
	@Log
	public <T, ID, TYPE extends Object> ParallelServiceActivator prepareToCreate(CrudService<T, ID> crudService, ID id, TYPE type) {
		GenericCrudServiceCreateCallable<T, ID, TYPE> result = new GenericCrudServiceCreateCallable(crudService, id, type);
		callables.add(result);
		this.resultQueue.push(result);
		return this; 
	}
	
	/**
	 * Parallel 'delete by id' interface for removing unique entity. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual unique id to be queried.
	 * @return
	 */
	@Log
	public <T, ID, TYPE extends Object> ParallelServiceActivator prepareToDelete(CrudService<T, ID> crudService, ID id) {
		GenericCrudServiceDeleteCallable<T, ID> result = new GenericCrudServiceDeleteCallable(crudService, id);
		callables.add(result);
		return this; 
	}

	/**
	 * Parallel 'query by id' interface for quering type safe entities. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param parameters Represents the query parameters (key, value) for the query interface.
	 * @return Type safe result of the query.
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryById(CrudService<T, ID> crudService, Map<?, ?> parameters) {
		GenericCrudServiceQueryAllByCriteriaCallable<T, ID> result = new GenericCrudServiceQueryAllByCriteriaCallable<T, ID>(crudService, parameters);
		callables.add(result);
		this.resultQueue.add(result);
		return this; 
	}
	
	/**
	 * Parallel 'query all by id' interface for quering type safe entities. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual unique id to be queried.
	 * @param resultIdentifier
	 * @return
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryAllById(CrudService<T, ID> crudService, ID id, String resultIdentifier) {
		GenericCrudServiceQueryAllByIdCallable<T, ID> result = new GenericCrudServiceQueryAllByIdCallable<T, ID>(crudService, id);
		callables.add(result);
		resultCache.put(resultIdentifier, result);
		return this; 
	}
	
	/**
	 * Parallel 'query by id' interface for quering type safe entities. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param id Represents the actual unique id to be queried.
	 * @param resultIdentifier
	 * @return
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryById(CrudService<T, ID> crudService, ID id, String resultIdentifier) {
		GenericCrudServiceQueryByIdCallable<T, ID> result = new GenericCrudServiceQueryByIdCallable<T, ID>(crudService, id);
		callables.add(result);
		resultCache.put(resultIdentifier, result);
		return this; 
	}
	
	/**
	 * Parallel 'query by id' interface for quering collection of type safe entities. 
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param parameters
	 * @param resultIdentifier
	 * @return
	 */
	@Log
	public <T, ID extends Object> ParallelServiceActivator prepareToQueryAllByCriteria(CrudService<T, ID> crudService,  Map<?, ?> parameters, String resultIdentifier) {
		GenericCrudServiceQueryAllByCriteriaCallable<T, ID> result = new GenericCrudServiceQueryAllByCriteriaCallable<T, ID>(crudService, parameters);
		callables.add(result);
		resultCache.put(resultIdentifier, result);
		return this; 
	}

	/**
	 * Activates all prepared queries simultaniously.
	 * 
	 * @param crudService Represents the actual service implement <code>org.openinfinity.core.integration.CrudService</code>.
	 * @param timeout Represents the timeout if activation is not processed in given time frame. 
	 * @param unit Represents the time unit of the given time frame. 
	 * @return
	 * @throws SystemException
	 */
	@Log
	public ParallelServiceActivator activate(long timeout, TimeUnit unit) throws SystemException {
		try {
			executorServiceAdapter.invokeAll(callables, timeout, unit);
		} catch (InterruptedException interruptedException) {
			ExceptionUtil.throwSystemException(interruptedException.getMessage(), interruptedException, ExceptionLevel.ERROR, "unique.exception.system.threading.error");
		}
		return this;
	}
	
	/**
	 * Activates all prepared queries simultaniously.
	 *
	 * @return Instanse of the service activator.
	 * @throws SystemException
	 */
	public ParallelServiceActivator activate() throws SystemException {
		try {
			executorServiceAdapter.invokeAll(callables);
		} catch (InterruptedException interruptedException) {
			ExceptionUtil.throwSystemException(interruptedException.getMessage(), interruptedException, ExceptionLevel.ERROR, "unique.exception.system.threading.error");
		}
		return this;
	}
	
	/**
	 * Loads given result with identifier after parallel execution.
	 * 
	 * @param resultIdentifier Represents the unique id of the predefined result.
	 * @return
	 */
	public <T extends Object> T loadResult(String resultIdentifier) {
		if (resultCache.containsKey(resultIdentifier)) {
			return (T) resultCache.get(resultIdentifier).loadResult();
		} else {
			ExceptionUtil.throwSystemException("unique.exception.id.not.found", ExceptionLevel.ERROR, "unique.exception.system.threading.error");
		}
		return null;
	}
	
	/**
	 * Loads result from the response queue.
	 * 
	 * @return
	 */
	public <T extends Object> T loadResult() {
		if (resultQueue.size() > 0) {
			Loadable loadable = resultQueue.pop();
			return (T) loadable.loadResult();
		} else {
			ExceptionUtil.throwSystemException("unique.exception.id.not.found", ExceptionLevel.ERROR, "unique.exception.system.threading.error");
		}
		return null;
	}
	
	/**
	 * Result callback method for responses.
	 * 
	 * @param asyncResultCallback Represents the callback interface.
	 * @return
	 */
	public ParallelServiceActivator onResult(AsyncResultCallback asyncResultCallback) {
		if (resultQueue.size() > 0) {
			Loadable loadable = resultQueue.pop();
			 asyncResultCallback.onResult(loadable.loadResult());
		} else {
			ExceptionUtil.throwSystemException("unique.exception.id.not.found", ExceptionLevel.ERROR, "unique.exception.system.threading.error");
		}
		return this;
	}

	/**
	 * * Result callback method for response based on result indentifier.
	 * 
	 * @param resultIdentifier Represent the unique identifier of the result.
	 * @param asyncResultCallback Represent the callback interface for the result.
	 * @return
	 */
	public ParallelServiceActivator onResult(String resultIdentifier, AsyncResultCallback asyncResultCallback) {
		 asyncResultCallback.onResult(loadResult(resultIdentifier));
		 return this;
	}
	
}
