# PDF Certificate Generator
This is a simple OSGi bundle to generate an award certificate and render it to
PDF.

## WARNING
A major refactor is coming that will significantly change the workflow of this
library. You have been warned.

## How To Use
Get a `CertificateFactory` and a `CertificateRenderer` via OSGi, and follow the
API and the JavaDoc. I know, I know, code isn't its own documentation, just
keep your eyes peeled for more to follow. There are a couple of examples in the
localtest package, knock yourself out.

## Before You Say It...
It has been pointed out to me that this library stands to be much more
format-independent than its name implies, since XML can be rendered to pretty
much anything with XSLT and an appropriate rendering library, e.g., Apache POI.
These changes are on the horizon as well, so thanks for your support.

