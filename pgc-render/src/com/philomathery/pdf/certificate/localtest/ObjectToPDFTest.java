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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopConfParser;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;

import com.philomathery.pdf.certificate.Certificate;

/**
 * An example test harness for proof of concept.
 */
public class ObjectToPDFTest{
   private static final String BASE_OUTPUT_DIR = "/home/cole/work";
   private static final String FILE_NAME = "award.pdf";

   public static void main(final String[] args){
      try{
         System.out.println("Starting ObjectToPDFTest");
         System.out.println("Initializing...");
         final File baseDir = new File(BASE_OUTPUT_DIR);
         final File pdfFile = new File(baseDir, FILE_NAME);
         final ObjectToPDFTest test = new ObjectToPDFTest();
         System.out.println("Transforming...");
         test.convertCertificateToPDF(ObjectToXMLTest.createSampleCertificate(), pdfFile);
         System.out.println("Success! File output to " + pdfFile);
      }catch(final Exception e){
         e.printStackTrace();
      }
   }

   private final FopFactory fopFactory;

   public ObjectToPDFTest() throws SAXException, IOException{
      File xconf = new File("res/fop.xconf");
      // parsing configuration
      FopConfParser parser = new FopConfParser(xconf);
      // building the factory with the user options
      FopFactoryBuilder builder = parser.getFopFactoryBuilder();
      fopFactory = builder.build();
   }

   public void convertCertificateToPDF(final Certificate certificate, final File pdf){
      try (final OutputStream out = new FileOutputStream(pdf)){
         final FOUserAgent userAgent = fopFactory.newFOUserAgent();
         final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, out);
         final TransformerFactory transformerFactory = TransformerFactory.newInstance();
         final Transformer transformer = transformerFactory.newTransformer(new StreamSource(Files.newInputStream(Paths.get(certificate.getXslt()), StandardOpenOption.READ)));
         final Source source = certificate.getSource();
         final Result result = new SAXResult(fop.getDefaultHandler());
         transformer.transform(source, result);
      }catch(final Exception e){
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
