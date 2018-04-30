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

import com.google.auto.value.AutoValue;
import org.jclouds.json.SerializedNames;

/**
 * A data center location that is valid for your subscription.
 */
@AutoValue
public abstract class Location {
   /**
    * The id of the data center.
    */
   public abstract String id();

   /**
    * The name of the data center location. Ex. {@code West Europe}.
    */
   public abstract String name();

   /**
    * The localized name of the data center location.
    */
   public abstract String displayName();

   /**
    * The longitude of the datacenter
    */
   public abstract double longitude();

   /**
    * The latitude of the datacenter
    */
   public abstract double latitude();

   @SerializedNames({"id", "name", "displayName", "longitude", "latitude"})
   public static Location create(final String id, final String name, final String displayName, final double longitude,
           final double latitude) {

      return new AutoValue_Location(id, name, displayName, longitude, latitude);
   }

   @Override
   public final boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (o instanceof Location) {
         Location that = (Location) o;
         return (this.id().equalsIgnoreCase(that.id()))
               && (this.name().equalsIgnoreCase(that.name()))
               && (this.displayName().equals(that.displayName()))
               && (Double.doubleToLongBits(this.longitude()) == Double.doubleToLongBits(that.longitude()))
               && (Double.doubleToLongBits(this.latitude()) == Double.doubleToLongBits(that.latitude()));
      }
      return false;
   }

   @Override
   public final int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= this.id().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.name().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.displayName().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (Double.doubleToLongBits(this.longitude()) >>> 32) ^ Double.doubleToLongBits(this.longitude());
      h *= 1000003;
      h ^= (Double.doubleToLongBits(this.latitude()) >>> 32) ^ Double.doubleToLongBits(this.latitude());
      return h;
   }
}
