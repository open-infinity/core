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
package org.openinfinity.core.integration;

/**
 * Standardized URL paths for create, read, update, and delete operations.
 * 
 * @author Ilkka Leinonen
 * @version 1.2.0 - Refactoring of the operations
 * @since 1.1.0 - Initial version
 */
public class CrudOperations {
	
	/**
	 * Standardized URL path for CREATE operations.
	 */
	public static final String CREATE = "/create";
	
	/**
	 * Standardized URL path for UDPATE operations.
	 */
	public static final String UPDATE = "/update";
	
	/**
	 * Standardized URL path for DELETE operations. Uses resource ID as a path variable.
	 */
	public static final String DELETE = "/delete/{id}";
	
	/**
	 * Standardized URL path for LOAD BY ID operations. Uses resource ID as a path variable.
	 */
	public static final String QUERY_BY_ID = "/querybyid/{id}";
	
	/**
	 * Standardized URL path for QUERY ALL operations.
	 */
	public static final String QUERY_ALL = "/queryall";

	/**
	 * Standardized URL path for query operations (?key=value).
	 */
	public static final String QUERY_BY_CRITERIA = "/query";
	
}