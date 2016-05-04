package cz.kuzna.android.dbvarna.processor.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import cz.kuzna.android.dbvarna.processor.definition.ColumnDefinition;
import cz.kuzna.android.dbvarna.processor.definition.TableDefinition;
import cz.kuzna.android.dbvarna.processor.utils.ProcessorUtils;
import cz.kuzna.android.dbvarna.processor.utils.StringUtils;

/**
 * @author Radek Kuznik
 */
public class MapperWriter extends BaseWriter {

    private TableDefinition tableDefinition;

    public MapperWriter(final TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    @Override
    public TypeSpec buildTypeSpec() {
        final ClassName abstractMapperClass = ClassName.get("cz.kuzna.android.dbvarna.mapper", "AbstractMapper");
        final TypeName superClass = ParameterizedTypeName.get(abstractMapperClass, tableDefinition.getClassName());

        final TypeSpec.Builder classBuilder = TypeSpec.classBuilder(tableDefinition.getClassName().simpleName() + "Mapper").superclass(superClass);
        classBuilder.addModifiers(Modifier.PUBLIC);
        classBuilder.addMethod(buildMapFromMethodSpec());
        classBuilder.addMethod(buildMapToMethodSpec());

        return classBuilder.build();
    }

    @Override
    public String getPackageName() {
        return tableDefinition.getClassName().packageName();
    }

    public MethodSpec buildMapFromMethodSpec() {
        ClassName cursorClass = ClassName.get("android.database", "Cursor");
        ClassName entityClass = tableDefinition.getClassName();
        ClassName schemaClass = ClassName.get(tableDefinition.getClassName().packageName(), tableDefinition.getClassName().simpleName() + TableWriter.SCHEMA_SUFFIX);

        final MethodSpec.Builder builder =  MethodSpec.methodBuilder("mapFrom")
                .addModifiers(Modifier.PUBLIC)
                .returns(entityClass)
                .addAnnotation(Override.class)
                .addParameter(cursorClass, "cursor", Modifier.FINAL)
                .addParameter(String.class, "prefix", Modifier.FINAL);

        builder.addStatement("final $T entity = new $T()", entityClass, entityClass);

        for(final ColumnDefinition columnDefinition : tableDefinition.getColumns()) {

            final String cursorType;

            final boolean isBoolean = columnDefinition.getValueClass().isAssignableFrom(Boolean.class) || columnDefinition.getValueClass().isAssignableFrom(boolean.class);

            if(isBoolean) {
                cursorType = StringUtils.capitalize(int.class.getSimpleName());
            } else {
                cursorType = StringUtils.capitalize(columnDefinition.getValueClass().getSimpleName());
            }

            final StringBuilder sb = new StringBuilder();
            sb.append("entity." + columnDefinition.getSetterMethod());
            sb.append("(cursor.get" + cursorType);
            sb.append("(cursor.getColumnIndex(prefix + $T." + TableWriter.COLUMN_PREFIX + columnDefinition.getName().toUpperCase() + "))");
            sb.append((isBoolean ? " == 1" : "") + ")");

            builder.addStatement(sb.toString(), schemaClass);
        }

        builder.addStatement("\nreturn entity");

        return builder.build();
    }

    public MethodSpec buildMapToMethodSpec() {
        ClassName contentValuesClass = ClassName.get("android.content", "ContentValues");
        ClassName entityClass = tableDefinition.getClassName();
        ClassName schemaClass = ClassName.get(tableDefinition.getClassName().packageName(), tableDefinition.getClassName().simpleName() + TableWriter.SCHEMA_SUFFIX);

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("mapTo")
                .addModifiers(Modifier.PUBLIC)
                .returns(contentValuesClass)
                .addAnnotation(Override.class)
                .addParameter(entityClass, "entity", Modifier.FINAL)
                .addParameter(TypeName.BOOLEAN, "insert", Modifier.FINAL);

        builder.addStatement("final $T values = new $T()", contentValuesClass, contentValuesClass);

        for(final ColumnDefinition columnDefinition : tableDefinition.getColumns()) {
            final boolean isBoolean = columnDefinition.getValueClass().isAssignableFrom(Boolean.class) || columnDefinition.getValueClass().isAssignableFrom(boolean.class);

            final StringBuilder sb = new StringBuilder();
            sb.append("values.put($T." + TableWriter.COLUMN_PREFIX + columnDefinition.getName().toUpperCase() + ", entity." + columnDefinition.getGetterMethod() + "()");
            sb.append((isBoolean ? " ? 1 : 0" : "") + ")");

            builder.addStatement(sb.toString(), schemaClass);
        }

        builder.addStatement("\nreturn values");

        return builder.build();
    }
}