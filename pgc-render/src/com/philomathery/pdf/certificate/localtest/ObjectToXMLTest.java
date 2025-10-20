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

package com.philomathery.pdf.certificate.localtest;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.CertificateFactory;
import com.philomathery.pcg.model.elements.CertificateElement;
import com.philomathery.pcg.model.elements.Citation;
import com.philomathery.pcg.model.elements.Header;
import com.philomathery.pcg.model.elements.Officiator;
import com.philomathery.pcg.model.elements.Recipient;
import com.philomathery.pcg.model.elements.Salutation;
import com.philomathery.pdf.certificate.internal.CertificateFactoryImpl;

/**
 * An example test harness for proof of concept.
 */
public class ObjectToXMLTest
{
   private static final String XSLT_URI = "file:///home/cole/workspaces/mustang/com.philomathery.pdf.certificate/res/simplecertificate.xsl";
   private static final String BASE_OUTPUT_DIR = "/home/cole/work";
   private static final String FILE_NAME = "award.xml";

   public static Certificate createSampleCertificate()
   {
      try
      {
         final Header header = new Header("Certificate Generating Certificate");
         final Salutation salutation = new Salutation("Be it known that");
         final Recipient recipient = new Recipient("Michael Atkinson");
         final Citation citation = new Citation("has successfully implemented an award certificate generating framework.");
         final Officiator officiator = new Officiator("Michael Romulus Atkinson");

         final List<CertificateElement> elements = Arrays.asList(header, salutation, recipient, citation, officiator);

         final URI xslt = URI.create(XSLT_URI);
         final CertificateFactory factory = new CertificateFactoryImpl();
         return factory.createCertificate(elements, xslt, null);
      }catch(final Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }

   public static void main(final String[] args)
   {
      try
      {
         System.out.println("Starting ObjectToXMLTest");
         System.out.println("Initializing...");
         final File baseDir = new File(BASE_OUTPUT_DIR);
         final File xmlFile = new File(baseDir, FILE_NAME);
         final ObjectToXMLTest test = new ObjectToXMLTest();
         System.out.println("Transforming...");
         test.convertCertificateToXML(createSampleCertificate(), xmlFile);
         System.out.println("Success! File output to " + xmlFile);
      }catch(final Exception e)
      {
         e.printStackTrace();
      }
   }

   private void convertCertificateToXML(final Certificate certificate, final File xml)
   {
      try
      {
         final TransformerFactory factory = TransformerFactory.newInstance();
         final Transformer transformer = factory.newTransformer();
         final Source source = certificate.getSource();
         final StreamResult result = new StreamResult(xml);
         transformer.transform(source, result);
      }catch(final TransformerException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
