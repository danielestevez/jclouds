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
public abstract class Image {
   
   /**
    * The id of the image
    */
   @Nullable
   public abstract String id();

   /**
    * The name of the image
    */
   @Nullable
   public abstract String name();

   /**
    * The location of the image
    */
   public abstract String location();

   /**
    * The image properties.
    */
   public abstract ImageProperties properties();

   /**
    * the tags of the image
    */
   @Nullable public abstract Map<String, String> tags();

   @SerializedNames({"id", "name", "location", "properties", "tags"})
   public static Image create(final String id, final String name, final String location,
         final ImageProperties properties, final Map<String, String> tags) {
      return builder().id(id).name(name).location(location).properties(properties).tags(tags).build();
   }

   public abstract Builder toBuilder();

   public static Builder builder() {
      return new AutoValue_Image.Builder();
   }

   @AutoValue.Builder
   public abstract static class Builder {
      public abstract Builder id(String id);
      public abstract Builder name(String name);
      public abstract Builder location(String location);
      public abstract Builder properties(ImageProperties properties);
      public abstract Builder tags(Map<String, String> tags);

      abstract Map<String, String> tags();

      abstract Image autoBuild();

      public Image build() {
         tags(tags() != null ? ImmutableMap.copyOf(tags()) : null);
         return autoBuild();
      }
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (o instanceof Image) {
         Image that = (Image) o;
         return ((this.id() == null) ? (that.id() == null) : this.id().equalsIgnoreCase(that.id()))
               && ((this.name() == null) ? (that.name() == null) : this.name().equalsIgnoreCase(that.name()))
               && (this.location().equalsIgnoreCase(that.location()))
               && (this.properties().equals(that.properties()))
               && ((this.tags() == null) ? (that.tags() == null) : this.tags().equals(that.tags()));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= (id() == null) ? 0 : this.id().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (name() == null) ? 0 : this.name().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.location().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.properties().hashCode();
      h *= 1000003;
      h ^= (tags() == null) ? 0 : this.tags().hashCode();
      return h;
   }
}
