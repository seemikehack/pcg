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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopConfParser;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;

import com.philomathery.pdf.certificate.Certificate;
import com.philomathery.pdf.certificate.CertificateRenderer;
import com.philomathery.pdf.certificate.exception.CertificateException;

public class CertificateRendererImpl implements CertificateRenderer{
   private Map<URI, Transformer> transformerCache = new ConcurrentHashMap<>(5);
   private Map<File, FopFactory> factoryCache = new ConcurrentHashMap<>(5);

   // used for Declarative Service
   public void activate(){
      // noop
   }

   // used for Declarative Service
   public void dispose(){
      // noop
   }

   @Override
   public void render(final Certificate certificate, final File outputFile, final File configurationFile) throws CertificateException{
      final Path filePath = Paths.get(outputFile.toURI());
      try (final OutputStream out = Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)){
         FopFactory fopFactory = factoryCache.get(configurationFile);
         if(fopFactory == null){
            try{
               long startTime = System.currentTimeMillis();
               // parsing configuration
               FopConfParser parser = new FopConfParser(configurationFile);
               // building the factory with the user options
               FopFactoryBuilder builder = parser.getFopFactoryBuilder();
               fopFactory = builder.build();
               factoryCache.put(configurationFile, fopFactory);
               System.out.println("Fop config time: " + (System.currentTimeMillis() - startTime));
            }catch(IOException | SAXException e){
               throw new CertificateException("Unable to open or parse configuration file", e);
            }
         }
         long startTime = System.currentTimeMillis();
         final FOUserAgent userAgent = fopFactory.newFOUserAgent();
         Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, out);
         System.out.println("Fop build time: " + (System.currentTimeMillis() - startTime));

         startTime = System.currentTimeMillis();
         Transformer transformer = transformerCache.get(certificate.getXslt());
         if(transformer == null){
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try (InputStream transformInputStream = certificate.getXslt().toURL().openStream()){
               transformer = transformerFactory.newTransformer(new StreamSource(transformInputStream));
               transformerCache.put(certificate.getXslt(), transformer);
            }
         }
         System.out.println("Transformer build time: " + (System.currentTimeMillis() - startTime));

         startTime = System.currentTimeMillis();
         final Source source = certificate.getSource();
         final Result result = new SAXResult(fop.getDefaultHandler());
         transformer.transform(source, result);
         System.out.println("Transform time: " + (System.currentTimeMillis() - startTime));
      }catch(final IOException e){
         throw new CertificateException("Unable to create or open file at [" + filePath + "] for writing", e);
      }catch(final FOPException e){
         throw new CertificateException("Unable to create or use new Formatting Objects Processor model", e);
      }catch(final TransformerConfigurationException e){
         throw new CertificateException("Unable to open XSLT at [" + certificate.getXslt() + "] for reading", e);
      }catch(final TransformerException e){
         throw new CertificateException("Unable to transform introspected POJO source to XML", e);
      }
   }
}
