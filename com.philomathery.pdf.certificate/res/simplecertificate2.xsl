<?xml version="1.0" encoding="utf-8"?>
<!--
  Copyright 2013 Michael Atkinson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
   <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
   <xsl:param name="versionParam" select="'1.0'"/> 
   <xsl:template match="certificate">
      <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" id="docroot">
         <layout-master-set xmlns="http://www.w3.org/1999/XSL/Format">
            <simple-page-master margin-bottom="0.25in" margin-left="1in" margin-right="1in" margin-top="0.25in" master-name="s1-simple" page-height="8.5in" page-width="11in">
               <region-body margin-bottom="0mm" margin-left="0mm" margin-right="0mm" margin-top="19mm"/>
               <region-before extent="12mm" region-name="xsl-region-before-simple"/>
               <region-after extent="12mm" region-name="xsl-region-after-simple"/>
            </simple-page-master>
            <page-sequence-master master-name="s1">
               <repeatable-page-master-alternatives>
                  <conditional-page-master-reference master-reference="s1-simple"/>
               </repeatable-page-master-alternatives>
            </page-sequence-master>
         </layout-master-set>
         <fo:page-sequence id="section_s1" format="" master-reference="s1" initial-page-number="1">
            <fo:flow flow-name="xsl-region-body">
               <fo:block color="#000000" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="18.0pt" line-height="100%" space-after="0in" start-indent="3.5in" text-indent="0.5in">
                  <xsl:value-of select="year"/>
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="65.0pt" line-height="100%" space-after="0in" text-align="center">
                  <xsl:value-of select="meet"/>
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="80.0pt" line-height="100%" space-after="0in" text-align="center">
                  <xsl:value-of select="place"/>
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="18.0pt" line-height="100%" space-after="0in" text-align="center">
                  has been awarded to
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="40.0pt" line-height="100%" space-after="0in" text-align="center">
                  <xsl:value-of select="recipient"/>
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="18.0pt" line-height="100%" space-after="0in" text-align="center">
                  in
               </fo:block>
               <fo:block color="#000000" margin-top="3mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="50.0pt" line-height="100%" space-after="0in" text-align="center">
                  <xsl:value-of select="event"/>
               </fo:block>
               <xsl:apply-templates select="officiator"/>
            </fo:flow>
         </fo:page-sequence>
      </fo:root>
   </xsl:template>
   <xsl:template match="officiator">
      <fo:block color="#000000" margin-top="25mm" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="14.0pt" line-height="100%" space-after="0in" text-indent="0.5in" text-align-last="justify">
         <xsl:value-of select="name"/>
         <fo:leader leader-pattern="space"/>
         <xsl:value-of select="date"/>
      </fo:block>
      <fo:block color="#000000" font-family="Brush Script MT Italic" font-weight="normal" font-style="normal" font-size="14.0pt" line-height="100%" space-after="0in" text-indent="0.5in">
         <xsl:value-of select="title"/>
      </fo:block>
   </xsl:template>
</xsl:stylesheet>

