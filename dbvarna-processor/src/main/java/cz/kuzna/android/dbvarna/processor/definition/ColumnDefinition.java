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
    private String setterMethod;
    private String getterMethod;
    private Class valueClass;

    public ColumnDefinition(String name, SqlLiteType type, Class valueClass, String defaultValue, boolean nullable, int length, boolean primaryKey,
                            boolean autoincrement, boolean indexed, boolean unique, String setterMethod, String getterMethod) {
        this.name = name;
        this.type = type;
        this.valueClass = valueClass;
        this.defaultValue = defaultValue;
        this.nullable = nullable;
        this.length = length;
        this.primaryKey = primaryKey;
        this.autoincrement = autoincrement;
        this.indexed = indexed;
        this.unique = unique;
        this.setterMethod = setterMethod;
        this.getterMethod = getterMethod;
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

    public String getSetterMethod() {
        return setterMethod;
    }

    public void setSetterMethod(String setterMethod) {
        this.setterMethod = setterMethod;
    }

    public String getGetterMethod() {
        return getterMethod;
    }

    public void setGetterMethod(String getterMethod) {
        this.getterMethod = getterMethod;
    }

    public Class getValueClass() {
        return valueClass;
    }

    public void setValueClass(Class valueClass) {
        this.valueClass = valueClass;
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
                ", defaultValue='" + defaultValue + '\'' +
                ", type=" + type +
                ", setterMethod='" + setterMethod + '\'' +
                ", getterMethod='" + getterMethod + '\'' +
                ", valueClass=" + valueClass +
                '}';
    }
}