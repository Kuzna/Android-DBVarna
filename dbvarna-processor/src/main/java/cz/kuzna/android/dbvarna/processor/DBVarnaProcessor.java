package cz.kuzna.android.dbvarna.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import cz.kuzna.android.dbvarna.annotation.Table;
import cz.kuzna.android.dbvarna.processor.definition.TableDefinition;
import cz.kuzna.android.dbvarna.processor.generator.BaseWriter;
import cz.kuzna.android.dbvarna.processor.generator.TableWriter;
import cz.kuzna.android.dbvarna.processor.processor.TableProcessor;

/**
 * @author Radek Kuznik
 */
@AutoService(Processor.class)
public class DBVarnaProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return true;
        }

        final ArrayList<TableDefinition> tableDefinitions = buildTableSchemas(roundEnv);

        try {
            for(final TableDefinition tableDefinition : tableDefinitions) {
                writeCodeForEachModel(tableDefinition, new TableWriter(tableDefinition));

                System.out.println("Table " + tableDefinition.getClassName().simpleName() + " class generated");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public LinkedHashSet<String> getSupportedAnnotationTypes() {
        final LinkedHashSet<String> supportedTypes = new LinkedHashSet<>();
        supportedTypes.add(Table.class.getCanonicalName());

        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public ArrayList<TableDefinition> buildTableSchemas(RoundEnvironment roundEnv) {
        final ArrayList<TableDefinition> tables = new ArrayList<TableDefinition>();

        for(final Element element : roundEnv.getElementsAnnotatedWith(Table.class)) {
            tables.add(TableProcessor.proceed(processingEnv, (TypeElement) element));
        }

        return tables;
    }

    public void writeCodeForEachModel(TableDefinition schema, BaseWriter writer) throws Exception {
        writeToFiler(writer.buildJavaFile());
    }

    private void writeToFiler(JavaFile javaFile) throws Exception {
        try {
            javaFile.writeTo(new SynchronizedFiler(processingEnv.getFiler()));
        } catch (Exception e) {
            throw new Exception("Failed to write " + javaFile.typeSpec.name, e);
        }
    }
}
