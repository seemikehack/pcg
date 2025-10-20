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

import java.io.IOException;
import java.util.Objects;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pcg.model.elements.CertificateElement;

public class CertificateXMLReader extends AbstractObjectReader
{
   @Override
   public void parse(final InputSource input) throws IOException, SAXException
   {
      // TODO find out if this check is even necessary
      // this reader is not exposed as an OSGi service
      // the only things that would use it already know about it
      if (input instanceof CertificateInputSource)
         parse(((CertificateInputSource)input).getCertificate());
      else
         throw new SAXException("Unsupported InputSource [" + input + "] specified.");
   }

   /**
    * Starts parsing the {@link Certificate} object.
    * 
    * @param certificate
    *           The object to parse
    * @throws SAXException
    *            In case of a problem during SAX event generation
    */
   public void parse(final Certificate certificate) throws SAXException
   {
      Objects.requireNonNull(certificate, "Parameter certificate must not be null");
      if (handler == null)
         throw new IllegalStateException("ContentHandler not set");

      // Start the document
      handler.startDocument();

      // Generate SAX events for the Certificate
      generateFor(certificate);

      // End the document
      handler.endDocument();
   }

   /**
    * Generates SAX events for a {@link Certificate} object.
    * 
    * @param certificate
    *           Certificate object to use
    * @throws SAXException
    *            In case of a problem during SAX event generation
    */
   protected void generateFor(final Certificate certificate) throws SAXException
   {
      Objects.requireNonNull(certificate, "Parameter certificate must not be null");
      if (handler == null)
         throw new IllegalStateException("ContentHandler not set");

      handler.startElement("certificate");
      for (final CertificateElement element : certificate.getElements())
         generateFor(element);
      handler.endElement("certificate");
   }

   /**
    * Generates SAX events for a CertificateElement object.
    * 
    * @param element
    *           {@link CertificateElement} object to use
    * @throws SAXException
    *            In case of a problem during SAX event generation
    */
   protected void generateFor(final CertificateElement element) throws SAXException
   {
      Objects.requireNonNull(element, "Parameter element must not be null");
      if (handler == null)
         throw new IllegalStateException("ContentHandler not set");

      if (element.getContent() != null)
         handler.element(element.getName(), element.getContent());
      else if (element.getMulticontent() != null)
      {
         handler.startElement(element.getName());
         // FIXME this requires that the XSLT have tacit knowledge about the
         // structure of the data
         for (final String subelementName : element.getMulticontent().keySet())
            handler.element(subelementName, element.getMulticontent().get(subelementName));
         handler.endElement(element.getName());
      }
   }
}
