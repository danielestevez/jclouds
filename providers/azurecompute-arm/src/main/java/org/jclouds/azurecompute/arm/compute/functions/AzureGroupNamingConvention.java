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

package org.jclouds.azurecompute.arm.compute.functions;

import org.jclouds.azurecompute.arm.compute.AzureSecurityGroupsNamingConvention;
import org.jclouds.compute.functions.GroupNamingConvention;

import com.google.inject.ImplementedBy;

public interface AzureGroupNamingConvention extends GroupNamingConvention {

   @ImplementedBy(AzureSecurityGroupsNamingConvention.Factory.class)
   public interface Factory {

      AzureGroupNamingConvention create();

      /**
       * top-level resources do not need a prefix, yet still may need to follow
       * a naming convention
       */
      AzureGroupNamingConvention createWithoutPrefix();

   }
}
