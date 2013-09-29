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

package com.philomathery.pdf.certificate.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.CertificateElement;
import com.philomathery.pdf.certificate.CertificateFactory;
import com.philomathery.pdf.certificate.CertificateOptions;
import com.philomathery.pdf.certificate.exception.CertificateException;
import com.philomathery.pdf.certificate.exception.UnsupportedCertificateException;

public class CertificateFactoryImpl implements CertificateFactory
{
   // used for Declarative Service
   public void activate()
   {
      // noop
   }

   // used for Declarative Service
   public void dispose()
   {
      // noop
   }

   @Override
   public Certificate createCertificate(final Collection<CertificateElement> elements, final URI xslt, final CertificateOptions options, final Class<? extends Certificate> certificateType) throws CertificateException
   {
      Objects.requireNonNull(elements, "Parameter elements must not be null");
      Objects.requireNonNull(certificateType, "Parameter certificateType must not be null");
      if (certificateType.isAssignableFrom(SimpleCertificate.class))
      {
         if (xslt == null)
         {
            try
            {
               // FIXME This hasn't been tested yet, but I seem to remember that
               // the URL returned by getResource() does not comply with RFC
               // 2396 and will therefore always cause toURI() to throw its
               // exception.
               final URI defaultXslt = Activator.getContext().getBundle().getResource("res/simplecertificate.xsl").toURI();
               return new SimpleCertificate(elements, defaultXslt, options);
            }
            catch (final URISyntaxException e)
            {
               throw new CertificateException("Unable to retrieve a default XSLT file for certificate type [" + certificateType + "]", e);
            }
         }
         else
            return new SimpleCertificate(elements, xslt, options);
      }
      throw new UnsupportedCertificateException("Unsupported certificate type [" + certificateType + "]");
   }
}
