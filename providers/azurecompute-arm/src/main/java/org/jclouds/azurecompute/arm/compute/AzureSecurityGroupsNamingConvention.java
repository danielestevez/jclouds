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

import java.security.SecureRandom;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.azurecompute.arm.compute.functions.AzureGroupNamingConvention;
import org.jclouds.compute.internal.FormatSharedNamesAndAppendUniqueStringToThoseWhichRepeat;
import org.jclouds.predicates.Validator;

import com.google.common.base.Supplier;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import static org.jclouds.compute.config.ComputeServiceProperties.RESOURCENAME_DELIMITER;
import static org.jclouds.compute.config.ComputeServiceProperties.RESOURCENAME_PREFIX;

public class AzureSecurityGroupsNamingConvention extends FormatSharedNamesAndAppendUniqueStringToThoseWhichRepeat
      implements AzureGroupNamingConvention {

   public AzureSecurityGroupsNamingConvention(String prefix, char delimiter, Supplier<String> suffixSupplier,
         Validator<String> groupValidator) {
      super(prefix, delimiter, suffixSupplier, groupValidator);
   }

   @Singleton
   public static class Factory implements AzureGroupNamingConvention.Factory {
      @Inject(optional = true)
      @Named(RESOURCENAME_PREFIX)
      private String prefix = "jclouds";
      @Inject(optional = true)
      @Named(RESOURCENAME_DELIMITER)
      private char delimiter = '-';
      @Inject(optional = true)
      private Supplier<String> suffixSupplier = new Supplier<String>() {
         final SecureRandom random = new SecureRandom();

         @Override
         public String get() {
            return Integer.toHexString(random.nextInt(4095));
         }
      };

      @Inject(optional = true)
      private Validator<String> groupValidator = new AzureGroupNameValidator(2, 63);

      // lazy init, so that @Inject stuff can work, and avoid calling the
      // constructor
      // each time, as it compiles new regexes
      LoadingCache<String, AzureGroupNamingConvention> cache = CacheBuilder.newBuilder()
            .build(new CacheLoader<String, AzureGroupNamingConvention>() {

               @Override
               public AzureGroupNamingConvention load(String key) throws Exception {
                  return new AzureSecurityGroupsNamingConvention(key, delimiter, suffixSupplier, groupValidator);
               }

            });

      @Override
      public AzureGroupNamingConvention create() {
         return cache.getUnchecked(prefix);
      }

      @Override
      public AzureGroupNamingConvention createWithoutPrefix() {
         return cache.getUnchecked("");
      }
   }
}
