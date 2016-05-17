package cz.kuzna.android.dbvarna.processor.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Field;
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

    public static final String COLUMN_PREFIX    = "COL_";
    public static final String INDEX_PREFIX     = "index_";
    public static final String SCHEMA_SUFFIX    = "Schema";
    public static final String SQL_CREATE_TABLE = "SQL_CREATE_TABLE";
    public static final String SQL_CREATE_INDEX = "SQL_CREATE_INDEX_";
    public static final String TABLE_NAME       = "TABLE_NAME";

    private TableDefinition tableDefinition;

    public TableWriter(final TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    @Override
    public TypeSpec buildTypeSpec() {
        TypeSpec.Builder classBuilder = TypeSpec.interfaceBuilder(tableDefinition.getClassName().simpleName() + SCHEMA_SUFFIX);
        classBuilder.addModifiers(Modifier.PUBLIC);
        classBuilder.addField(buildTableNameFieldSpec());
        classBuilder.addFields(buildFieldSpecs());
        classBuilder.addField(buildSqlCreateFieldSpec());
        classBuilder.addFields(buildSqlIndexFields());

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
        return FieldSpec.builder(String.class, TABLE_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", tableDefinition.getName())
                .build();
    }

    public FieldSpec buildColumnFieldSpec(ColumnDefinition columnDef) {
        return FieldSpec.builder(String.class, COLUMN_PREFIX + columnDef.getName().toUpperCase())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", columnDef.getName())
                .build();
    }

    public FieldSpec buildSqlCreateFieldSpec() {
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


        return FieldSpec.builder(String.class, SQL_CREATE_TABLE)
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

    public ArrayList<FieldSpec> buildSqlIndexFields() {
        final ArrayList<FieldSpec> indexQueries = new ArrayList<FieldSpec>();

        int index = 1;

        for(final ColumnDefinition columnDef : tableDefinition.getColumns()) {
            if(columnDef.isIndexed() && !columnDef.isUnique()) {

                indexQueries.add(FieldSpec.builder(String.class, SQL_CREATE_INDEX + index)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", buildIndexQuery(tableDefinition.getName(), columnDef.getName()))
                        .build());

                index++;
            }
        }

        return indexQueries;
    }

    public String buildIndexQuery(final String tableName, final String columnName) {
        final StringBuilder sb = new StringBuilder();

        sb.append("CREATE INDEX " + INDEX_PREFIX + tableName.toLowerCase() + "_" + columnName.toLowerCase());
        sb.append(" ON ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(columnName);
        sb.append(")");

        return sb.toString();
    }
}