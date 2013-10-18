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

package com.philomathery.pdf.certificate;

import java.io.File;

import com.philomathery.pdf.certificate.exception.CertificateException;

/**
 * Entry point for rendering the end-product PDF from a {@link Certificate}.
 */
public interface CertificateRenderer
{
   /**
    * Render the certificate to the output path.
    * 
    * @param certificate
    *           The certificate to render.
    * 
    * @param outputFile
    *           the output file to render to
    * @throws CertificateException
    *            if unable to locate the path to which to write the final file
    */
   void render(Certificate certificate, File outputFile) throws CertificateException;
}
