package cz.kuzna.android.dbvarna.processor.definition;

import com.squareup.javapoet.ClassName;

import java.util.ArrayList;

/**
 * @author Radek Kuznik
 */
public class TableDefinition {

    private final String name;
    private final ClassName className;
    private final ArrayList<ColumnDefinition> columns = new ArrayList<>(5);

    public TableDefinition(final String name, ClassName className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public ClassName getClassName() {
        return className;
    }

    public ArrayList<ColumnDefinition> getColumns() {
        return columns;
    }
}