package cz.kuzna.android.dbvarna.processor.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import cz.kuzna.android.dbvarna.processor.definition.ColumnDefinition;
import cz.kuzna.android.dbvarna.processor.definition.TableDefinition;
import cz.kuzna.android.dbvarna.processor.utils.StringUtils;

/**
 * @author Radek Kuznik
 */
public class TableWriter extends BaseWriter {

    private TableDefinition tableDefinition;

    public TableWriter(final TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    @Override
    public TypeSpec buildTypeSpec() {
        TypeSpec.Builder classBuilder = TypeSpec.interfaceBuilder(tableDefinition.getClassName().simpleName() + "Schema");
        classBuilder.addModifiers(Modifier.PUBLIC);
        classBuilder.addField(buildTableNameFieldSpec());
        classBuilder.addFields(buildFieldSpecs());
        classBuilder.addField(buildSqlCreate());

        return classBuilder.build();
    }

    @Override
    public String getPackageName() {
        return tableDefinition.getClassName().packageName();
    }

    public List<FieldSpec> buildFieldSpecs() {
        List<FieldSpec> columns = new ArrayList<>();

        for(final ColumnDefinition columnDef : tableDefinition.getColumns()) {
            System.out.println("Adding column " + columnDef.toString());
            final FieldSpec fieldSpec = buildColumnFieldSpec(columnDef);
            columns.add(fieldSpec);
        }

        return columns;
    }

    public FieldSpec buildTableNameFieldSpec() {
        return FieldSpec.builder(String.class, "TABLE_NAME")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", tableDefinition.getName())
                .build();
    }

    public FieldSpec buildColumnFieldSpec(ColumnDefinition columnDef) {
        return FieldSpec.builder(String.class, "COL_" + columnDef.getName().toUpperCase())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", columnDef.getName())
                .build();
    }

    public FieldSpec buildSqlCreate() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(tableDefinition.getName());
        sb.append(" (");

        for(int i = 0, size = tableDefinition.getColumns().size(); i < size; i++) {
            final ColumnDefinition column = tableDefinition.getColumns().get(i);

            sb.append(column.getName());
            sb.append(" ");
            sb.append(column.getType().toString());
            sb.append(buildColumnFlags(column));

            if(i != size - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");


        return FieldSpec.builder(String.class, "SQL_CREATE")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", sb.toString())
                .build();
    }

    public String buildColumnFlags(ColumnDefinition column) {
        final StringBuilder sb = new StringBuilder();

        if(column.isPrimaryKey()) {
            sb.append(" PRIMARY KEY");

            if(column.isAutoincrement()) {
                sb.append(" AUTO INCREMENT");
            }
        } else {
            if (column.isUnique()) {
                sb.append(" UNIQUE");
            }

            if (!column.isNullable()) {
                sb.append(" NOT NULL");
            }
        }

        if (!StringUtils.isBlank(column.getDefaultValue())) {
            sb.append("DEFAULT " + column.getDefaultValue());
        }

        return sb.toString();
    }
}