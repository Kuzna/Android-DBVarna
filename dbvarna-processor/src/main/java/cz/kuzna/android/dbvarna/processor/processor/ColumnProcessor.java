package cz.kuzna.android.dbvarna.processor.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.Index;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;
import cz.kuzna.android.dbvarna.processor.definition.ColumnDefinition;
import cz.kuzna.android.dbvarna.processor.sql.SqlLiteType;
import cz.kuzna.android.dbvarna.processor.utils.ProcessorUtils;
import cz.kuzna.android.dbvarna.processor.utils.StringUtils;


/**
 * @author Radek Kuznik
 */
public final class ColumnProcessor {

    public static ColumnDefinition proceed(final VariableElement element) {

        final Column column = element.getAnnotation(Column.class);
        final String name = getColumnName(column, element);
        final SqlLiteType type = storageType(element);
        final boolean index = element.getAnnotation(Index.class) != null;

        final boolean nullable;
        final boolean unique;
        final int length;
        final String defaultValue;

        if(column != null) {
            nullable = column.nullable();
            unique = column.unique();
            length = column.length();
            defaultValue = column.defaultValue();
        } else {
            nullable = true;
            unique = false;
            length = -1;
            defaultValue = "";
        }

        final boolean isPrimary = element.getAnnotation(PrimaryKey.class) != null;

        boolean autoincrement = false;

        if(isPrimary && type == SqlLiteType.INTEGER) {
            autoincrement = element.getAnnotation(PrimaryKey.class).autoincrement();
        }

        final String capName = StringUtils.capitalize(element.getSimpleName().toString());

        final String setterMethod = "set" + capName;
        final String getterMethod =  (ProcessorUtils.isBooleanColumn(element.asType()) ? "is" : "get") + capName;

        final Class valueClass = ProcessorUtils.getValueClass(ClassName.get(element.asType()));

        return new ColumnDefinition(name, type, valueClass, defaultValue, nullable, length, isPrimary, autoincrement, index, unique, setterMethod, getterMethod);
    }

    static String getColumnName(final Column column, final VariableElement element) {
        if (column != null && !StringUtils.isBlank(column.name())) {
            return column.name();
        }

        return element.getSimpleName().toString().toLowerCase();
    }

    static SqlLiteType storageType(final VariableElement element) {
        final TypeName type = ClassName.get(element.asType());

        return SqlLiteType.get(type);
    }
}
