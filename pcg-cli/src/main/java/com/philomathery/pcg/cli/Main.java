package com.philomathery.pcg.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
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

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "pcg", mixinStandardHelpOptions = true, version = "0.1.0",
        description = "Render certificates to PDF using Apache FOP")
public class Main implements Runnable {

    @Option(names = "--xml", description = "Path to XML data file", required = true)
    private File xmlFile;

    @Option(names = "--xslt", description = "Path to XSLT stylesheet", required = true)
    private File xsltFile;

    @Option(names = "--out", description = "Path to output PDF file, or '-' for stdout", required = true)
    private String out;

    @Option(names = "--conf", description = "Path to FOP configuration (fop.xconf)")
    private File fopConf;

    @Override
    public void run() {
        try {
            FopFactory fopFactory = buildFopFactory();
            try (InputStream xmlIn = new FileInputStream(xmlFile);
                 InputStream xsltIn = new FileInputStream(xsltFile);
                 OutputStream outStream = openOut(out)) {

                FOUserAgent userAgent = fopFactory.newFOUserAgent();
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

                TransformerFactory tf = TransformerFactory.newInstance();
                Templates templates = tf.newTemplates(new StreamSource(xsltIn));
                Transformer transformer = templates.newTransformer();

                Source source = new StreamSource(xmlIn);
                Result result = new SAXResult(fop.getDefaultHandler());
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            System.err.println("Render failed: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(4);
        }
    }

    private FopFactory buildFopFactory() throws Exception {
        if (fopConf != null) {
            FopConfParser parser = new FopConfParser(fopConf);
            FopFactoryBuilder builder = parser.getFopFactoryBuilder();
            return builder.build();
        }
        // default factory without external config
        return FopFactory.newInstance(new File(".").toURI());
    }

    private static OutputStream openOut(String out) throws Exception {
        if ("-".equals(out)) {
            return System.out;
        }
        Path p = Paths.get(out).toAbsolutePath();
        Files.createDirectories(p.getParent());
        return Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public static void main(String[] args) {
        int exit = new CommandLine(new Main()).execute(args);
        System.exit(exit);
    }
}


