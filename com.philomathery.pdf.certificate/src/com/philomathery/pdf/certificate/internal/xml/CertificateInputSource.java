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

package com.philomathery.pdf.certificate.internal.xml;

import org.xml.sax.InputSource;

import com.philomathery.pdf.certificate.Certificate;

/**
 * Allows for using a {@link Certificate} as an XML {@link InputSource}.
 */
public class CertificateInputSource extends InputSource
{
   private final Certificate certificate;

   public CertificateInputSource(final Certificate certificate)
   {
      this.certificate = certificate;
   }

   public Certificate getCertificate()
   {
      return certificate;
   }
}
