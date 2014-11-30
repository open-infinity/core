/*
 * Copyright (c) 2012-2014 the original author or authors.
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

/**
 * Represents callback interface for async result.
 * 
 * @author Ilkka Leinonen
 * @version 1.0.0
 * @since 2.0.0
 *
 * @param <T> Represent the typesafe object.
 */
public interface AsyncResultCallback<T> {
	
	/**
	 * Represents the type safe result provided through callback interface.
	 * @param result Represents the actual result.
	 */
	public void onResult(T result);

}
