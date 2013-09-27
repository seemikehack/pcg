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
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.elements.CertificateElement;
import com.philomathery.pdf.certificate.internal.xml.CertificateInputSource;
import com.philomathery.pdf.certificate.internal.xml.CertificateXMLReader;

public class SimpleCertificate implements Certificate
{
   private final Collection<CertificateElement> elements;
   private final URI xslt;

   public SimpleCertificate(final Collection<CertificateElement> elements, final URI xslt)
   {
      this.elements = elements;
      this.xslt = xslt;
   }

   @Override
   public Collection<CertificateElement> getElements()
   {
      return Collections.unmodifiableCollection(elements);
   }

   public Path getXslt() throws FileSystemNotFoundException
   {
      return Paths.get(xslt);
   }

   @Override
   public Source getSource()
   {
      return new SAXSource(new CertificateXMLReader(), new CertificateInputSource(this));
   }
}