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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.azurecompute.arm.util.VMImages.isCustom;

import org.jclouds.azurecompute.arm.domain.Version.VersionProperties;
import org.jclouds.javax.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VMImage {
   /**
    * The publisher of the image reference.
    */
   @Nullable
   public abstract String publisher();

   /**
    * The offer of the image reference.
    */
   @Nullable
   public abstract String offer();

   /**
    * The sku of the image reference.
    */
   @Nullable
   public abstract String sku();

   /**
    * The version of the image reference.
    */
   @Nullable
   public abstract String version();

   /**
    * The location from where Image was fetched
    */
   @Nullable
   public abstract String location();

   /**
    * The group of the custom image
    */
   @Nullable
   public abstract String group();

   /**
    * The storage of the custom image.
    */
   @Nullable
   public abstract String storage();

   /**
    * The vhd1 of the custom image
    */
   @Nullable
   public abstract String vhd1();

   /**
    * The vhd2 of the custom image.
    */
   @Nullable
   public abstract String vhd2();

   /**
    * The name of the custom image template.
    */
   @Nullable
   public abstract String name();
   
   /**
    * True if custom image
    */
   public abstract boolean custom();

   /**
    * The id of the custom image template.
    */
   @Nullable
   public abstract String customImageId();
   
   /**
    * The resource group for the image in case of custom images.
    * @return
    */
   @Nullable
   public abstract String resourceGroup();

   /**
    * Extended version properties.
    */
   @Nullable
   public abstract VersionProperties versionProperties();
   
   private static Builder builder() {
      return new AutoValue_VMImage.Builder();
   }
   
   public static Builder azureImage() {
      return builder().custom(false);
   }
   
   public static Builder customImage() {
      return builder().custom(true);
   }
   
   VMImage() {
      
   }
   
   public abstract Builder toBuilder();
   
   @AutoValue.Builder
   public abstract static class Builder {

      public abstract Builder customImageId(String id);
      public abstract Builder resourceGroup(String resourceGroup);
      public abstract Builder publisher(String published);
      public abstract Builder offer(String offer);
      public abstract Builder sku(String sku);
      public abstract Builder version(String version);
      public abstract Builder location(String location);
      public abstract Builder group(String group);
      public abstract Builder storage(String storage);
      public abstract Builder vhd1(String vhd1);
      public abstract Builder vhd2(String vhd2);
      public abstract Builder name(String name);
      public abstract Builder custom(boolean custom);
      public abstract Builder versionProperties(VersionProperties versionProperties);
      
      public abstract VMImage build();
   }

   public String encodeFieldsToUniqueId() {
      return String.format("%s/%s/%s/%s", location(), publisher(), offer(), sku());
   }

   public String encodeFieldsToUniqueIdCustom() {
      return String.format("%s/%s/%s", resourceGroup(), location(), name());
   }

   public static VMImage decodeFieldsFromUniqueId(final String id) {
      VMImage vmImage;
      String[] fields = checkNotNull(id, "id").split("/");
      if (isCustom(id)) {
         /* id fields indexes
         0: imageReference.resourceGroup
         1: imageReference.location + "/" +
         2: imageReference.name
         */
         vmImage = VMImage.customImage().resourceGroup(fields[0]).location(fields[1]).name(fields[2]).build();
      } else {
         /* id fields indexes
         0: imageReference.location + "/" +
         1: imageReference.publisher + "/" +
         2: imageReference.offer + "/" +
         3: imageReference.sku + "/" +
         */
         vmImage = VMImage.azureImage().location(fields[0]).publisher(fields[1]).offer(fields[2]).sku(fields[3])
               .build();
      }
      return vmImage;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (o instanceof VMImage) {
         VMImage that = (VMImage) o;
         return ((this.publisher() == null) ? (that.publisher() == null) : this.publisher().equalsIgnoreCase(that.publisher()))
               && ((this.offer() == null) ? (that.offer() == null) : this.offer().equalsIgnoreCase(that.offer()))
               && ((this.sku() == null) ? (that.sku() == null) : this.sku().equalsIgnoreCase(that.sku()))
               && ((this.version() == null) ? (that.version() == null) : this.version().equalsIgnoreCase(that.version()))
               && ((this.location() == null) ? (that.location() == null) : this.location().equalsIgnoreCase(that.location()))
               && ((this.group() == null) ? (that.group() == null) : this.group().equalsIgnoreCase(that.group())
               && ((this.storage() == null) ? (that.storage() == null) : this.storage().equalsIgnoreCase(that.storage()))
               && ((this.vhd1() == null) ? (that.vhd1() == null) : this.vhd1().equalsIgnoreCase(that.vhd1()))
               && ((this.vhd2() == null) ? (that.vhd2() == null) : this.vhd2().equalsIgnoreCase(that.vhd2()))
               && ((this.name() == null) ? (that.name() == null) : this.name().equalsIgnoreCase(that.name()))
               && (this.custom() == that.custom())
               && ((this.customImageId() == null) ? (that.customImageId() == null) : this.customImageId().equalsIgnoreCase(
               that.customImageId()))
               && ((this.resourceGroup() == null) ? (that.resourceGroup() == null) : this.resourceGroup().equalsIgnoreCase(that
               .resourceGroup()))
               && ((this.versionProperties()) == null) ? (that.versionProperties() == null) : this.versionProperties()
               .equals(that.versionProperties()));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= (publisher() == null) ? 0 : this.publisher().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (offer() == null) ? 0 : this.offer().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (sku() == null) ? 0 : this.sku().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (version() == null) ? 0 : this.version().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (location() == null) ? 0 : this.location().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (group() == null) ? 0 : this.group().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (storage() == null) ? 0 : this.storage().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (vhd1() == null) ? 0 : this.vhd1().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (vhd2() == null) ? 0 : this.vhd2().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (name() == null) ? 0 : this.name().toLowerCase().hashCode();
      h *= 1000003;
      h ^= this.custom() ? 1231 : 1237;
      h *= 1000003;
      h ^= (customImageId() == null) ? 0 : this.customImageId().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (resourceGroup() == null) ? 0 : this.resourceGroup().toLowerCase().hashCode();
      h *= 1000003;
      h ^= (versionProperties() == null) ? 0 : this.versionProperties().hashCode();
      return h;
   }
}
