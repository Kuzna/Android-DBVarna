package cz.kuzna.android.dbvarna.processor.processor;

import com.squareup.javapoet.ClassName;

import java.util.ArrayList;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;
import cz.kuzna.android.dbvarna.annotation.Table;
import cz.kuzna.android.dbvarna.processor.definition.ColumnDefinition;
import cz.kuzna.android.dbvarna.processor.definition.TableDefinition;
import cz.kuzna.android.dbvarna.processor.utils.StringUtils;

/**
 * @author Radek Kuznik
 */
public final class TableProcessor {

    public static TableDefinition proceed(final ProcessingEnvironment processingEnv, final TypeElement typeElement) {
        final Table table = typeElement.getAnnotation(Table.class);

        final String tableName = getTableName(table, typeElement);
        final ClassName modelClass = ClassName.get(typeElement);

        final TableDefinition tableDefinition = new TableDefinition(tableName, modelClass, table.generateMapper(), table.generateDao());
        tableDefinition.getColumns().addAll(collectColumns(processingEnv, typeElement));

        return tableDefinition;
    }

//    static ClassName getModelClass(final Table table, final TypeElement typeElement) {
//        final ClassName className = ClassName.get(typeElement);
//
//        if(table == null || StringUtils.isBlank(table.name())) {
//            return className;
//        }
//
//        return ClassName.get(className.packageName(), table.name());
//    }

    static String getTableName(final Table table, final TypeElement typeElement) {
        final ClassName className = ClassName.get(typeElement);

        if(table == null || StringUtils.isBlank(table.name())) {
            return className.simpleName().toLowerCase();
        }

        return table.name();
    }

    static ArrayList<ColumnDefinition> collectColumns(final ProcessingEnvironment processingEnv, final TypeElement typeElement) {
        final ArrayList<ColumnDefinition> columns = new ArrayList<>();

        final TypeMirror superclass = typeElement.getSuperclass();

        if (!superclass.toString().equals(Object.class.getCanonicalName())) {
            final TypeElement superclassElement = processingEnv.getElementUtils().getTypeElement(superclass.toString());
            columns.addAll(collectColumns(processingEnv, superclassElement));
        }

        for(final Element element : typeElement.getEnclosedElements()) {
            if (element instanceof VariableElement) {
                if (element.getAnnotation(Column.class) != null || element.getAnnotation(PrimaryKey.class) != null) {
                    columns.add(ColumnProcessor.proceed((VariableElement) element));
                }
            }
        }

        return columns;
    }
}