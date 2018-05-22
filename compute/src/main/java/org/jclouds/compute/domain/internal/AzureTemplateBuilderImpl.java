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
package org.jclouds.compute.domain.internal;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.tryFind;
import static java.lang.String.format;

import java.util.NoSuchElementException;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.compute.predicates.ImagePredicates;
import org.jclouds.domain.Location;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

public class AzureTemplateBuilderImpl extends TemplateBuilderImpl {

   @Inject
   protected AzureTemplateBuilderImpl(@Memoized Supplier<Set<? extends Location>> locations,
         @Memoized Supplier<Set<? extends Image>> images, @Memoized Supplier<Set<? extends Hardware>> hardwares,
         Supplier<Location> defaultLocation, @Named("DEFAULT") Provider<TemplateOptions> optionsProvider,
         @Named("DEFAULT") Provider<TemplateBuilder> defaultTemplateProvider) {
      super(locations, images, hardwares, defaultLocation, optionsProvider, defaultTemplateProvider);
   }

   @Override
   public Template build() {
      if (nothingChangedExceptOptions()) {
         TemplateBuilder defaultTemplate = defaultTemplateProvider.get();
         if (options != null)
            defaultTemplate.options(options);
         return defaultTemplate.build();
      }

      if (options == null)
         options = optionsProvider.get();
      logger.debug(">> searching params(%s)", this);
      Set<? extends Image> images = getImages();
      checkState(!images.isEmpty(), "no images present!");
      Set<? extends Hardware> hardwaresToSearch = hardwares.get();
      checkState(!hardwaresToSearch.isEmpty(), "no hardware profiles present!");

      Image image = null;
      if (imageId != null) {
         image = loadImageWithId(images);
         if (currentLocationWiderThan(image.getLocation()))
            this.location = image.getLocation();
      }

      Hardware hardware = null;
      if (hardwareId != null) {
         hardware = findHardwareWithId(hardwaresToSearch);
         if (currentLocationWiderThan(hardware.getLocation()))
            this.location = hardware.getLocation();
      }

      // if the user hasn't specified a location id, or an image or hardware
      // with location, let's search scoped to the implicit one
      if (location == null)
         location = defaultLocation.get();

      if (image == null) {
         Iterable<? extends Image> supportedImages = findSupportedImages(images);
         if (hardware == null)
            hardware = resolveHardware(hardwaresToSearch, supportedImages);
         image = resolveImage(hardware, supportedImages);
      } else {
         if (hardware == null)
            hardware = resolveHardware(hardwaresToSearch, ImmutableSet.of(image));
      }

      logger.debug("<<   matched image(%s) hardware(%s) location(%s)", image.getId(), hardware.getId(),
            location.getId());
      return new TemplateImpl(image, hardware, location, options);
   }

   private Image loadImageWithId(Iterable<? extends Image> images) {
      Optional<? extends Image> image = tryFind(images, ImagePredicates.idEqualsIgnoreCase(imageId));
      if (!image.isPresent()) {
         image = this.images.get(imageId); // Load the image from the cache, and refresh if missing
         if (!image.isPresent()) {
            throw throwNoSuchElementExceptionAfterLoggingImageIds(format("imageId(%s) not found", imageId), images);
         }
      }
      fromImage(image.get());
      return image.get();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public TemplateBuilder locationId(final String locationId) {
      Set<? extends Location> locations = this.locations.get();
      try {
         this.location = find(locations, new Predicate<Location>() {

            @Override
            public boolean apply(Location input) {
               return input.getId().equalsIgnoreCase(locationId);
            }

            @Override
            public String toString() {
               return "locationId(" + locationId + ")";
            }

         });
      } catch (NoSuchElementException e) {
         throw new NoSuchElementException(format("location id %s not found in: %s", locationId, locations));
      }
      return this;
   }

}
