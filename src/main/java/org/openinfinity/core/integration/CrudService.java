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
package org.openinfinity.core.integration;

import java.util.Collection;
import java.util.Map;

/**
 * CRUD (Create Read Update Delete) interface for domain services.
 * 
 * @author Ilkka Leinonen
 * @version 1.0.0
 * @since 2.0.0
 * @param <T> Represents the type safe entity.
 * @param <ID> Represents the type of the unique id.
 */
public interface CrudService<T, ID> {

	/**
	 * Creates a new entity and challenges business rules by entity validation.
	 * 
	 * @param type Represents the type safe entity to be created.
	 * @return Type safe unique id of the created domain entity.  
	 */
	public ID create(T type);
	
	/**
	 * Updates existing and challenges business rules by entity validation.
	 * 
	 * @param type Represents the type safe entity to be created.
	 * @return Represents the update entity.
	 */
	public T update(T type);

	/**
	 * Query interface with unique id of the entity. 
	 * 
	 * @param id Represent the unique id of the entity to be queried.
	 * @return Entity based on the unique id.
	 */
	public T queryById(ID id);
	
	/**
	 * Query interface with unique id of the collection of entities. 
	 * 
	 * @param id Represent the unique id of the entity to be queried.
	 * @return Entity based on the unique id.
	 */
	public Collection<T> queryAllById(ID id);
	
	/**
	 * Delete interface for removing entity by id.
	 * 
	 * @param id Represent the unique id of the entity to be queried.
	 */
	public void delete(ID id);
	
	/**
	 * Query interface for query by several parameters.
	 * 
	 * @param parameters Represents the query parameters for the interface.
	 * @return Collection of entities found by query parameters.
	 */
	public <K, V extends Object> Collection<T> queryByCriteria(Map<K, V> parameters);
	
}
