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

package com.philomathery.pdf.certificate.elements;

/**
 * Superclass for all certificate elements.
 */
public abstract class CertificateElement
{
   protected final String elementText;

   public CertificateElement(final String elementText)
   {
      this.elementText = elementText;
   }

   public abstract String getName();

   public String getContent()
   {
      return elementText;
   }

   /*
    * Future development consideration:
    * 
    * Uses the given render strategy to output formatted text representing the
    * element.
    * 
    * @return a String containing formatted text representing the element
    */
//   public abstract String render(RenderStrategy strategy);
}
