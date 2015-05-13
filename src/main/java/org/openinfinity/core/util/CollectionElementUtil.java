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

import java.util.Collection;
import java.util.Map;

/**
 * Utility for handling <code>java.util.Collection</code> element callbacks.
 * 
 * @author Ilkka Leinonen
 * @version 1.1.0 - Added support traversal object on iteration
 * @since 1.2.0 - Initial version
 */
public class CollectionElementUtil {
	
	/**
	 * Callbacks each type safe element of <code>java.util.Collection</code>.
	 * 
	 * @param collection Represents typesafe <code>java.util.Collection</code>.
	 * @param callback Represents the <code>java.util.Collection</code> element callback interface.
	 */
	public static <V extends Object> void iterate(Collection<V> collection, CollectionElementCallback<V> callback) {
		for (V value : collection) {
			if (value != null)
				callback.callback(value);
		}
	}
	
	/**
	 * Callbacks each type safe element of <code>java.util.Collection</code>.
	 * 
	 * @param collection Represents typesafe <code>java.util.Collection</code>.
	 * @param callback Represents the <code>java.util.Collection</code> element callback interface.
	 */
	public static <V, T extends Object> void iterate(Collection<V> collection,  T traverseObject, TraverseObjectCollectionElementCallback<V, T> callback) {
		for (V value : collection) {
			if (value != null)
				callback.callback(value, traverseObject);
		}
	}
	
	/**
	 * Callbacks each type safe entry of <code>java.util.Map</code>.
	 * 
	 * 
	 * @param map Represents typesafe <code>java.util.Map</code>.
	 * @param callback Represents the <code>java.util.Map</code> entry callback interface.
	 * @param traverseObject Represents the traverse object available for each callback entry.
	 */
	public static <K, V, T extends Object> void iterate(Map<K, V> map, T traverseObject, TraverseObjectMapElementCallback<K, V, T> callback) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (entry.getKey() != null)
				callback.callback(entry.getKey(), entry.getValue(), traverseObject);
		}
	}
	
	/**
	 * Callbacks each type safe entry of <code>java.util.Map</code>.
	 * 
	 * 
	 * @param map Represents typesafe <code>java.util.Map</code>.
	 * @param callback Represents the <code>java.util.Map</code> entry callback interface.
	 */
	public static <K, V extends Object> void iterate(Map<K, V> map, MapElementCallback<K, V> callback) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (entry.getKey() != null)
				callback.callback(entry.getKey(), entry.getValue());
		}
	}

}