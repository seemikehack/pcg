/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.philomathery.pdf.certificate.internal.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Base class for {@link XMLReader}s that generate SAX events from Java objects.
 */
public abstract class AbstractObjectReader implements XMLReader
{

   private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
   private static final String NS_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";

   private final Map<String, Boolean> features = new HashMap<>();
   private ContentHandler orgHandler;

   /** Proxy for easy SAX event generation */
   protected EasyGenerationContentHandlerProxy handler;
   /** Error handler */
   protected ErrorHandler errorHandler;

   /**
    * Constructor for the AbstractObjectReader object
    */
   public AbstractObjectReader()
   {
      setFeature(NAMESPACES, false);
      setFeature(NS_PREFIXES, false);
   }

   // ///////////////////
   // XMLReader interface

   /**
    * @see org.xml.sax.XMLReader#getContentHandler()
    */
   @Override
   public ContentHandler getContentHandler()
   {
      return this.orgHandler;
   }

   /**
    * @see org.xml.sax.XMLReader#setContentHandler(ContentHandler)
    */
   @Override
   public void setContentHandler(final ContentHandler handler)
   {
      this.orgHandler = handler;
      this.handler = new EasyGenerationContentHandlerProxy(handler);
   }

   /**
    * @see org.xml.sax.XMLReader#getErrorHandler()
    */
   @Override
   public ErrorHandler getErrorHandler()
   {
      return this.errorHandler;
   }

   /**
    * @see org.xml.sax.XMLReader#setErrorHandler(ErrorHandler)
    */
   @Override
   public void setErrorHandler(final ErrorHandler handler)
   {
      this.errorHandler = handler;
   }

   /**
    * @see org.xml.sax.XMLReader#getDTDHandler()
    */
   @Override
   public DTDHandler getDTDHandler()
   {
      return null;
   }

   /**
    * @see org.xml.sax.XMLReader#setDTDHandler(DTDHandler)
    */
   @Override
   public void setDTDHandler(final DTDHandler handler)
   {
   }

   /**
    * @see org.xml.sax.XMLReader#getEntityResolver()
    */
   @Override
   public EntityResolver getEntityResolver()
   {
      return null;
   }

   /**
    * @see org.xml.sax.XMLReader#setEntityResolver(EntityResolver)
    */
   @Override
   public void setEntityResolver(final EntityResolver resolver)
   {
   }

   /**
    * @see org.xml.sax.XMLReader#getProperty(String)
    */
   @Override
   public Object getProperty(final String name)
   {
      return null;
   }

   /**
    * @see org.xml.sax.XMLReader#setProperty(String, Object)
    */
   @Override
   public void setProperty(final String name, final Object value)
   {
   }

   /**
    * @see org.xml.sax.XMLReader#getFeature(String)
    */
   @Override
   public boolean getFeature(final String name)
   {
      return features.get(name).booleanValue();
   }

   /**
    * Returns true if the NAMESPACES feature is enabled.
    * 
    * @return boolean true if enabled
    */
   protected boolean isNamespaces()
   {
      return getFeature(NAMESPACES);
   }

   /**
    * Returns true if the MS_PREFIXES feature is enabled.
    * 
    * @return boolean true if enabled
    */
   protected boolean isNamespacePrefixes()
   {
      return getFeature(NS_PREFIXES);
   }

   /**
    * @see org.xml.sax.XMLReader#setFeature(String, boolean)
    */
   @Override
   public void setFeature(final String name, final boolean value)
   {
      this.features.put(name, new Boolean(value));
   }

   /**
    * @see org.xml.sax.XMLReader#parse(String)
    */
   @Override
   public void parse(final String systemId) throws IOException, SAXException
   {
      throw new SAXException(this.getClass().getName() + " cannot be used with system identifiers (URIs)");
   }

   /**
    * @see org.xml.sax.XMLReader#parse(InputSource)
    */
   @Override
   public abstract void parse(InputSource input) throws IOException, SAXException;

}
