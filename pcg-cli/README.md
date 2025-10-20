## PCG CLI

Command-line tool to render certificates to PDF using Apache FOP and XSLT.

### Build

```bash
mvn -DskipTests -q -f pcg-cli/pom.xml package
# Output: pcg-cli/target/pcg-cli-<version>.jar
```

### Usage

```bash
java -jar pcg-cli-<version>.jar \
  --xml <input.xml> \
  --xslt <stylesheet.xsl> \
  --out <output.pdf> \
  [--conf <fop.xconf>]
```

- `--xml`: XML data input file
- `--xslt`: XSLT stylesheet to transform XML into XSL‑FO
- `--out`: Output PDF path or `-` to write to stdout
- `--conf`: Optional FOP configuration (fonts, render options)

### Examples

Render with files:
```bash
java -jar pcg-cli-<version>.jar \
  --xml examples/certificate.xml \
  --xslt styles/simplecertificate2.xsl \
  --out out/award.pdf \
  --conf res/fop.xconf
```

Pipe to stdout:
```bash
java -jar pcg-cli-<version>.jar \
  --xml examples/certificate.xml \
  --xslt styles/simplecertificate2.xsl \
  --out - > award.pdf
```

### Notes

- For custom fonts/images, configure them in `fop.xconf` (pass with `--conf`).
- The CLI streams output; it does not hold the full PDF in memory.
- Exit codes: 0 success; 2 usage; 4 render/I/O error.


