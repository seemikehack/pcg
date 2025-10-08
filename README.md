# PDF Certificate Generator
This is a simple OSGi bundle to generate an award certificate and render it to
PDF.

## WARNING
A major refactor is coming that will significantly change the workflow of this
library. You have been warned.

## Build (Java 11+ runtime, built with JDK 21)

Prerequisites:

- Java 11+ runtime (bundle requires Java 11 or newer)
- Maven 3.8+
- Build machine JDK 21 recommended (project compiles with --release 11)

Build the bundle:

```bash
cd com.philomathery.pdf.certificate
mvn clean package
```

Artifacts:

- OSGi bundle: `com.philomathery.pdf.certificate/target/com.philomathery.pdf.certificate-<version>.jar`
 

## Consuming from Maven (GitHub Packages)

Add the GitHub Packages repository and dependency.

Maven `settings.xml` (credentials required, even for public packages):

```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>
```

Project `pom.xml`:

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/seemikehack/pcg</url>
  </repository>
  <!-- plus Maven Central for transitive deps -->
  <repository>
    <id>central</id>
    <url>https://repo1.maven.org/maven2/</url>
  </repository>
  
</repositories>

<dependencies>
  <dependency>
    <groupId>com.philomathery</groupId>
    <artifactId>com.philomathery.pdf.certificate</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

Note: Replace version with the latest release tag you published (e.g., `v1.0.0`).

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

