/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jclouds.azurecompute.arm.compute;

import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.predicates.Validator;

import com.google.common.base.CharMatcher;
import com.google.inject.Singleton;

import static com.google.common.base.CharMatcher.anyOf;
import static com.google.common.base.CharMatcher.inRange;

/**
 * Validates name for azure groups
 * https://docs.microsoft.com/en-us/azure/architecture/best-practices/naming-conventions
 *
 * @see org.jclouds.rest.InputParamValidator
 * @see org.jclouds.predicates.Validator
 */
@Singleton
public class AzureGroupNameValidator extends Validator<String> {
   private int min;
   private int max;

   @Inject
   public AzureGroupNameValidator(@Named("jclouds.azure_group_name_length_min") int min,
         @Named("jclouds.azure_group_name_length_max") int max) {
      this.min = min;
      this.max = max;
   }

   public void validate(String name) {

      if (name == null || name.length() < min || name.length() > max)
         throw exception(name, "Can't be null or empty. Length must be " + min + " to " + max + " symbols.");
      if (CharMatcher.JAVA_LETTER_OR_DIGIT.indexIn(name) != 0)
         throw exception(name, "Should start with letter/number");

      CharMatcher range = getAcceptableRange();
      if (!range.matchesAllOf(name))
         throw exception(name, "Should have lowercase ASCII letters, " + "numbers, or dashes");
   }

   protected CharMatcher getAcceptableRange() {
      return inRange('a', 'z').or(inRange('A', 'Z')).or(inRange('0', '9')).or(anyOf("-_."));
   }

   protected IllegalArgumentException exception(String vAppName, String reason) {
      return new IllegalArgumentException(
            String.format("Object '%s' doesn't match Azure group naming constraints. " + "Reason: %s.", vAppName,
                  reason));
   }

}
