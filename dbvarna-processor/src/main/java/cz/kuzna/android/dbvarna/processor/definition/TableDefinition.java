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
    private final boolean generateMapper;
    private final boolean generateDao;

    public TableDefinition(final String name, final ClassName className, final boolean generateMapper, final boolean generateDao) {
        this.name = name;
        this.className = className;
        this.generateMapper = generateMapper;
        this.generateDao = generateDao;
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

    public boolean isGenerateMapper() {
        return generateMapper;
    }

    public boolean isGenerateDao() {
        return generateDao;
    }
}