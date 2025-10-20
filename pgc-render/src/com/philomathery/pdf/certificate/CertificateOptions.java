/*
 * Copyright 2013 Michael Atkinson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.philomathery.pdf.certificate;

/**
 * Additional options to set on a certificate to control rendering.
 */
public class CertificateOptions
{
   public enum Orientation
   {
      LANDSCAPE, PORTRAIT;
   }

   /*
    * Future development consideration:
    * 
    * Load font files from JARs, or allow users to specify a URI from which to
    * load a font.
    */
//   public enum Font
//   {
//      SERIF, SANS, SCRIPT, FANCY, SILLY, CUSTOM;
//   }

   private Orientation orientation;

   public CertificateOptions()
   {
      orientation = Orientation.LANDSCAPE;
   }

   public Orientation getOrientation()
   {
      return orientation;
   }

   public void setLandscapeOrientation()
   {
      orientation = Orientation.LANDSCAPE;
   }

   public void setPortraitOrientation()
   {
      orientation = Orientation.PORTRAIT;
   }
}
