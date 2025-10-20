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

Build the modules:

```bash
mvn clean package
```

Artifacts:

- Renderer jar: `pgc-render/target/pgc-render-<version>.jar`
- CLI jar: `pcg-cli/target/pcg-cli-<version>.jar`

## Run CLI in Docker

Option A: Download from GitHub Releases at build time

```dockerfile
FROM eclipse-temurin:21-jre
ARG PCG_VERSION=2.0.0
ADD https://github.com/ccmcomputing/pcg/releases/download/v${PCG_VERSION}/pcg-cli-${PCG_VERSION}.jar /opt/pcg/pcg-cli.jar
ENTRYPOINT ["java","-jar","/opt/pcg/pcg-cli.jar"]
```

Build and run:
```bash
docker build -t pcg-cli:${PCG_VERSION} --build-arg PCG_VERSION=2.0.0 .
docker run --rm -v "$PWD":/work pcg-cli:${PCG_VERSION} \
  --xml /work/input.xml \
  --xslt /work/styles/simplecertificate2.xsl \
  --out /work/out.pdf \
  --conf /work/res/fop.xconf
```

Option B: Multi-stage fetch with curl

```dockerfile
FROM alpine:3.20 AS fetch
ARG PCG_VERSION=2.0.0
RUN apk add --no-cache curl \
 && curl -fsSL -o /pcg-cli.jar https://github.com/ccmcomputing/pcg/releases/download/v${PCG_VERSION}/pcg-cli-${PCG_VERSION}.jar

FROM eclipse-temurin:21-jre
COPY --from=fetch /pcg-cli.jar /opt/pcg/pcg-cli.jar
ENTRYPOINT ["java","-jar","/opt/pcg/pcg-cli.jar"]
```

CLI usage inside container:
```bash
java -jar /opt/pcg/pcg-cli.jar \
  --xml /work/input.xml \
  --xslt /work/styles/simplecertificate2.xsl \
  --out /work/out.pdf \
  --conf /work/res/fop.xconf
```

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

