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

import java.util.Map;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class NetworkSecurityGroup {
   public abstract String id();

   public abstract String name();

   @Nullable
   public abstract String location();

   @Nullable
   public abstract Map<String, String> tags();

   @Nullable
   public abstract NetworkSecurityGroupProperties properties();

   @Nullable
   public abstract String etag();

   @SerializedNames({ "id", "name", "location", "tags", "properties", "etag" })
   public static NetworkSecurityGroup create(final String id, final String name, final String location,
         final Map<String, String> tags, final NetworkSecurityGroupProperties properties, final String etag) {
      return new AutoValue_NetworkSecurityGroup(id, name, location, (tags == null) ? null : ImmutableMap.copyOf(tags),
            properties, etag);
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (o instanceof NetworkSecurityGroup) {
         NetworkSecurityGroup that = (NetworkSecurityGroup) o;
         return (this.id().equalsIgnoreCase(that.id())) && (this.name().equalsIgnoreCase(that.name())) && ((
               this.location() == null) ? (that.location() == null) : this.location().equalsIgnoreCase(that.location()))
               && ((this.tags() == null) ? (that.tags() == null) : this.tags().equals(that.tags())) && ((
               this.properties() == null) ? (that.properties() == null) : this.properties().equals(that.properties()))
               && ((this.etag() == null) ? (that.etag() == null) : this.etag().equalsIgnoreCase(that.etag()));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= this.id().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.name().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (location() == null) ? 0 : this.location().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (tags() == null) ? 0 : this.tags().hashCode();
      h *= 1000003;
      h ^= (properties() == null) ? 0 : this.properties().hashCode();
      h *= 1000003;
      h ^= (etag() == null) ? 0 : this.etag().toLowerCase().hashCode();
      return h;
   }
}
