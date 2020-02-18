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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.CertificateFactory;
import com.philomathery.pdf.certificate.elements.CertificateElement;
import com.philomathery.pdf.certificate.elements2.Event;
import com.philomathery.pdf.certificate.elements2.Meet;
import com.philomathery.pdf.certificate.elements2.Officiator;
import com.philomathery.pdf.certificate.elements2.Place;
import com.philomathery.pdf.certificate.elements2.Recipient;
import com.philomathery.pdf.certificate.elements2.Year;
import com.philomathery.pdf.certificate.internal.CertificateFactoryImpl;

/**
 * An example test harness for proof of concept.
 */
public class ObjectToXMLTest2{
   private static final String XSLT_URI = new File(".").toURI() + "/res/simplecertificate2.xsl";
   private static final String BASE_OUTPUT_DIR = ".";
   private static final String FILE_NAME = "award.xml";

   public static Certificate createSampleCertificate(){
      try{
         final Year year = new Year("2012-2013");
         final Meet meet = new Meet("Virtual Challenge Meet #4");
         final Place place = new Place("4th Place");
         // final Recipient recipient = new Recipient("Ryan Hogan - Abernathy
         // HS");
         final Recipient recipient = new Recipient("Michael Constantine Loyola \"Kolbe\" Chesterton - College Station Independent School District");
         final Event event = new Event("2A Calculator");

         // the API expects to receive sorted content, hence, LinkedHashMap
         final Map<String, String> officiatorContent = new LinkedHashMap<>();
         officiatorContent.put("name", "Chuck Thompson");
         officiatorContent.put("date", "February 16, 2013");
         officiatorContent.put("title", "Challenge Meet Director");
         final Officiator officiator = new Officiator(officiatorContent);

         final List<CertificateElement> elements = Arrays.asList(year, meet, place, recipient, event, officiator);

         final URI xslt = URI.create(XSLT_URI);
         final CertificateFactory factory = new CertificateFactoryImpl();
         return factory.createCertificate(elements, xslt, null);
      }catch(final Exception e){
         e.printStackTrace();
         return null;
      }
   }

   public static void main(final String[] args){
      try{
         System.out.println("Starting ObjectToXMLTest");
         System.out.println("Initializing...");
         final File baseDir = new File(BASE_OUTPUT_DIR);
         final File xmlFile = new File(baseDir, FILE_NAME);
         final ObjectToXMLTest2 test = new ObjectToXMLTest2();
         System.out.println("Transforming...");
         test.convertCertificateToXML(createSampleCertificate(), xmlFile);
         System.out.println("Success! File output to " + xmlFile);
      }catch(final Exception e){
         e.printStackTrace();
      }
   }

   private void convertCertificateToXML(final Certificate certificate, final File xml){
      try{
         final TransformerFactory factory = TransformerFactory.newInstance();
         final Transformer transformer = factory.newTransformer();
         final Source source = certificate.getSource();
         final StreamResult result = new StreamResult(xml);
         transformer.transform(source, result);
      }catch(final TransformerException e){
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
