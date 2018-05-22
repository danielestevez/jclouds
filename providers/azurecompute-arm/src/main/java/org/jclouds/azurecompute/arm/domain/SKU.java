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
package org.jclouds.azurecompute.arm.domain;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SKU {
   @Nullable
   public abstract String location();
   @Nullable
   public abstract String name();
   @Nullable
   public abstract String id();
   @Nullable
   public abstract String family();
   
   @SerializedNames({"location", "name", "id", "family"})
   public static SKU create(final String location, final String name, final String id, final String family) {

      return new AutoValue_SKU(location, name, id, family);
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (o instanceof SKU) {
         SKU that = (SKU) o;
         return ((this.location() == null) ?
               (that.location() == null) :
               this.location().equalsIgnoreCase(that.location())) && ((this.name() == null) ?
               (that.name() == null) :
               this.name().equalsIgnoreCase(that.name())) && ((this.id() == null) ?
               (that.id() == null) :
               this.id().equalsIgnoreCase(that.id())) && ((this.family() == null) ?
               (that.family() == null) :
               this.family().equalsIgnoreCase(that.family()));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= (location() == null) ? 0 : this.location().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (name() == null) ? 0 : this.name().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (id() == null) ? 0 : this.id().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (family() == null) ? 0 : this.family().toLowerCase().hashCode();
      return h;
   }
}
