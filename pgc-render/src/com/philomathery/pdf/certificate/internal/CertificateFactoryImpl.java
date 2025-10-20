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
import java.util.Collection;
import java.util.Objects;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.CertificateFactory;
import com.philomathery.pdf.certificate.CertificateOptions;
import com.philomathery.pcg.model.elements.CertificateElement;
import com.philomathery.pdf.certificate.exception.CertificateException;

public class CertificateFactoryImpl implements CertificateFactory
{
   private static final String PROP_DEFAULT_XSLT_PATH = "com.philomathery.pdf.certificate.defaultxsltpath";

   // used for Declarative Service
   public void activate()
   {
      // noop
   }

   @Override
   public Certificate createCertificate(final Collection<CertificateElement> elements, final URI xslt, final CertificateOptions options) throws CertificateException
   {
      Objects.requireNonNull(elements, "Parameter elements must not be null");
      if(xslt == null)
      {
         /*
          * FIXME (or don't, really) the URL returned by getResource() does not
          * comply with RFC 2396 and will therefore always cause toURI() to
          * throw its exception.
          */
         // final URI defaultXslt =
         // Activator.getContext().getBundle().getResource("res/simplecertificate.xsl").toURI();

         /*
          * NOTE this is a stop gap for the first pass effort, because this is
          * not scalable in the first place and because this functionality will
          * eventually get refactored out of this implementation and into a
          * rendering implementation
          */
         final URI defaultXslt = URI.create(Activator.getContext().getProperty(PROP_DEFAULT_XSLT_PATH));
         return new SimpleCertificate(elements, defaultXslt, options);
      }
      return new SimpleCertificate(elements, xslt, options);
   }

   // used for Declarative Service
   public void dispose()
   {
      // noop
   }
}
