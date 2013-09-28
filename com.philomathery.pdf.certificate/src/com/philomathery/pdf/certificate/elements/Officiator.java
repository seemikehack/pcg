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
 * Officiator element of a {@link com.philomathery.pdf.certificate.Certificate
 * Certificate}, for signature blocks of those approving the award and such.
 */
public class Officiator extends CertificateElement
{
   public Officiator(final String elementText)
   {
      super(elementText);
      // TODO Auto-generated constructor stub
   }

   @Override
   public String getName()
   {
      return "officiator";
   }
}
