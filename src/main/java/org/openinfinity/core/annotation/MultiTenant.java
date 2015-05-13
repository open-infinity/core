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
package org.openinfinity.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation represents the metadata added to the runtime method.
 * The metadata executes the <code>org.openinfinity.core.aspect.MultiTenantAspect</code> aspect and executes population of <code>org.openinfinity.core.domain.entity.MultiTenantBaseEntity</code> object. Annotation can be defined in the method level.
 * 
 * @author Ilkka Leinonen 
 * @version 1.0.0
 * @since 1.4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MultiTenant {}
