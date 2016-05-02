package cz.kuzna.android.dbvarna.processor.definition;

import cz.kuzna.android.dbvarna.processor.sql.SqlLiteType;

/**
 * @author Radek Kuznik
 */
public class ColumnDefinition {

    private final String name;
    private final boolean nullable;
    private final boolean primaryKey;
    private final boolean autoincrement;
    private final boolean indexed;
    private final boolean unique;
    private final int length;
    private final String defaultValue;
    private SqlLiteType type;

    public ColumnDefinition(String name, SqlLiteType type, String defaultValue, boolean nullable, int length, boolean primaryKey, boolean autoincrement, boolean indexed, boolean unique) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.nullable = nullable;
        this.length = length;
        this.primaryKey = primaryKey;
        this.autoincrement = autoincrement;
        this.indexed = indexed;
        this.unique = unique;
    }

    public String getName() {
        return name;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoincrement() {
        return autoincrement;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public boolean isUnique() {
        return unique;
    }

    public int getLength() {
        return length;
    }

    public SqlLiteType getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
                "name='" + name + '\'' +
                ", nullable=" + nullable +
                ", primaryKey=" + primaryKey +
                ", autoincrement=" + autoincrement +
                ", indexed=" + indexed +
                ", unique=" + unique +
                ", length=" + length +
                ", type=" + type +
                ", defaultValue=" + defaultValue +
                '}';
    }
}