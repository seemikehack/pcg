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

package com.philomathery.pdf.certificate.elements;

import java.util.Map;

/**
 * Implementation for all certificate elements. Currently, subclasses only
 * represent placeholders to be caught by <code>xsl:apply-templates</code> tags
 * in the XSLT.
 */
public class CertificateElement
{
   protected final String name;
   protected final String content;

   // FIXME adding class invariant for first release, refactor ASAP
   // input is assumed to be sorted by consumer of this API
   protected final Map<String, String> multicontent;

   public CertificateElement(final String name, final String content)
   {
      this.name = name;
      this.content = content;
      this.multicontent = null;
   }

   public CertificateElement(final String name, final Map<String, String> subcontent)
   {
      this.name = name;
      this.content = null;
      this.multicontent = subcontent;
   }

   /**
    * Access the name of this {@link CertificateElement}, which will be matched
    * by the XSLT when rendering the final certificate.
    * 
    * @return the name of this {@link CertificateElement}
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the content to be rendered in this element.
    * 
    * @return the value of the content in this element
    */
   public String getContent()
   {
      return content;
   }

   public Map<String, String> getMulticontent()
   {
      return multicontent;
   }
}
