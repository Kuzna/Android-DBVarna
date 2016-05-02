package cz.kuzna.android.dbvarna.processor.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

/**
 * @author Radek Kuznik
 */
public abstract class BaseWriter {

    public abstract TypeSpec buildTypeSpec();

    public abstract String getPackageName();

    public JavaFile buildJavaFile() {
        return JavaFile.builder(getPackageName(), buildTypeSpec())
                .skipJavaLangImports(true)
                .build();
    }
}
