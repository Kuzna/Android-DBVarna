package cz.kuzna.android.dbvarna.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import cz.kuzna.android.dbvarna.DatabaseManager;
import cz.kuzna.android.dbvarna.entity.BaseEntity;
import cz.kuzna.android.dbvarna.entity.IColumn;
import cz.kuzna.android.dbvarna.entity.IEntity;
import cz.kuzna.android.dbvarna.mapper.AbstractMapper;

/**
 * Abstract DAO
 *
 * @author Radek Kuznik
 */
public abstract class AbstractDao<T extends IEntity, V> {

    private static final String TAG = "AbstractDao";

    public static final String LEFT_JOIN = " LEFT JOIN ";
    public static final String ON = " ON ";
    public static final String AS = " AS ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";
    public static final String LIMIT = " LIMIT ";

    private String mTableName;

    /**
     * Abstract base DAO
     *
     * @param tableName
     */
    public AbstractDao(final String tableName) {
        mTableName = tableName;
    }

    public T insert(final T entity) {
        final ContentValues values = getMapper().mapTo(entity, true);

        final SQLiteDatabase database = openDatabase();

        try {
            long id = database.insert(getTableName(), null, values);
            entity.setId(id);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Insert into table " + mTableName + " failed", e);
        } finally {
            closeDatabase();
        }

        return entity;
    }

    public long update(final T entity) {
        final ContentValues values = getMapper().mapTo(entity, false);
        return update(values, IColumn.ID, entity.getId());
    }

    public long update(final T entity, final String column, final long value) {
        final ContentValues values = getMapper().mapTo(entity, false);
        return update(values, column, value);
    }

    public long update(final T entity, final String column, final String value) {
        final ContentValues values = getMapper().mapTo(entity, false);
        return update(values, column, value);
    }

    public long update(final ContentValues values, String column, String value) {
        final SQLiteDatabase database = openDatabase();

        long count = 0;

        try {
            count = database.update(getTableName(), values, column + "='" + value + "'", null);

            if(count == 0) {
                Log.w(TAG, "Item wasnt updated");
            } else {
                Log.w(TAG, "Items updated: " + count);
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Update in table " + mTableName + " failed", e);
        } finally {
            closeDatabase();
        }

        return count;
    }

    public long update(final ContentValues values, String column, long value) {
        final SQLiteDatabase database = openDatabase();

        long count = 0;

        try {
            count = database.update(getTableName(), values, column + "=" + value, null);

            if(count == 0) {
                Log.w(TAG, "Item wasnt updated");
            } else {
                Log.w(TAG, "Items updated: " + count);
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Update in table " + mTableName + " failed", e);
        } finally {
            closeDatabase();
        }

        return count;
    }

    public long update(final ContentValues values, String whereClause, String[] whereArgs) {
        final SQLiteDatabase database = openDatabase();

        long count = 0;

        try {
            count = database.update(getTableName(), values, whereClause, whereArgs);

            if(count == 0) {
                Log.w(TAG, "Item wasnt updated");
            } else {
                Log.w(TAG, "Items updated: " + count);
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Update in table " + mTableName + " failed", e);
        } finally {
            closeDatabase();
        }

        return count;
    }

    public int delete(final String column, final String value) {
        int deleted = 0;

        try {
            final SQLiteDatabase database = openDatabase();
            deleted = database.delete(mTableName, column + "='" + value + "'", null);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return deleted;
    }

    public int delete(final String whereClause, final String[] whereArgs) {
        int deleted = 0;

        try {
            final SQLiteDatabase database = openDatabase();
            deleted = delete(database, mTableName, whereClause, whereArgs);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return deleted;
    }

    public int delete(final SQLiteDatabase database, final String table, final String whereClause, final String[] whereArgs) {
        int deleted = database.delete(table, whereClause, whereArgs);
        return deleted;
    }

    public int delete(final V id) {
        int deleted = 0;

        try {
            final SQLiteDatabase database = openDatabase();
            deleted = database.delete(mTableName, IColumn.ID + "=" + id, null);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return deleted;
    }

    public int deleteAll() {
        final SQLiteDatabase database = openDatabase();

        int count = 0;

        try {
            count = database.delete(mTableName, null, null);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return count;
    }

    public T findOneBy(final String column, final long value) {
        final AbstractMapper<T> mapper = getMapper();

        T ret = null;

        Cursor cursor = null;
        try {
            final SQLiteDatabase database = openDatabase();
            cursor = database.query(getTableName(), null, column + "=" + value, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ret = mapper.mapFrom(cursor);
                cursor.moveToNext();
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ret;
    }

    public T findOneBy(final String column, final String value) {
        final AbstractMapper<T> mapper = getMapper();

        T ret = null;

        Cursor cursor = null;
        try {
            final SQLiteDatabase database = openDatabase();
            cursor = database.query(getTableName(), null, column + "='" + value + "'", null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ret = mapper.mapFrom(cursor);
                cursor.moveToNext();
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ret;
    }

    public long count(final String selection) {
        try {
            final SQLiteDatabase database = openDatabase();
            return DatabaseUtils.queryNumEntries(database, getTableName(), selection);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return 0;
    }

    public boolean exists(final String column, final String value) {
        long count = 0;

        try {
            final SQLiteDatabase database = openDatabase();
            count = DatabaseUtils.queryNumEntries(database, getTableName(), column + "='" + value + "'");
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }


        return count > 0;
    }

    public T findOneBy(final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
        try {
            final SQLiteDatabase database = openDatabase();

            return findOneBy(database, getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return null;
    }

    public T findOneBy(final SQLiteDatabase database, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
        final AbstractMapper<T> mapper = getMapper();

        T ret = null;

        Cursor cursor = null;
        try {
            cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ret = mapper.mapFrom(cursor);
                cursor.moveToNext();
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ret;
    }

    public ArrayList<T> findAll() {
        return findAllBy(null, null, null, null, null, null);
    }

    public ArrayList<T> findAllBy(final String columnName, final String value) {
        try {
            final SQLiteDatabase database = openDatabase();

            return findAllBy(database, getTableName(), null, columnName + "='" + value + "'", null, null, null, null);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return null;
    }

    public ArrayList<T> findAllBy(final String columnName, final Long value) {
        try {
            final SQLiteDatabase database = openDatabase();

            return findAllBy(database, getTableName(), null, columnName + "=" + value + "", null, null, null, null);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return null;
    }

    public ArrayList<T> findAllBy(final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
        try {
            final SQLiteDatabase database = openDatabase();

            return findAllBy(database, getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }

        return null;
    }

    public ArrayList<T> findAllBy(final SQLiteDatabase database, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
        final ArrayList<T> list = new ArrayList<T>();

        final AbstractMapper<T> mapper = getMapper();

        Cursor cursor = null;
        try {
            cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                final T item = mapper.mapFrom(cursor);
                list.add(item);
                cursor.moveToNext();
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public void executeSql(final String sql) {
        try {
            final SQLiteDatabase database = openDatabase();
            database.execSQL(sql);
        } catch (final SQLiteException e) {
            Log.e(TAG, "Error during SQL operation ", e);
        } finally {
            closeDatabase();
        }
    }

    protected abstract AbstractMapper<T> getMapper();

    /**
     * Open database
     *
     * @return
     * @throws SQLiteException if the database cannot be opened for writing
     */
    protected SQLiteDatabase openDatabase() throws SQLiteException {
        return DatabaseManager.getInstance().openDatabase();
    }

    /**
     * Close database
     *
     * @throws SQLiteException if the database was already closed
     */
    protected void closeDatabase() throws SQLiteException {
        DatabaseManager.getInstance().closeDatabase();
    }

    /**
     * Return name of database table of the Object
     *
     * @return
     */
    protected String getTableName() {
        return mTableName;
    }

    /**
     * Map value prefixBefore + columnName to prefixAfter + columnName for all columns, separated by comma
     *
     * @param prefixBefore - prefix before mapping
     * @param prefixAfter  - map to prefix
     * @param columnNames  - column names
     * @return
     */
    protected static String addColumnWithAlias(final String prefixBefore, final String prefixAfter, final String... columnNames) {
        if (columnNames == null || columnNames.length == 0) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();

        for (final String columnName : columnNames) {
            if (sb.length() > 0) {
                sb.append(",");
            }

            sb.append(prefixBefore);
            sb.append(columnName);
            sb.append(AS);
            sb.append(prefixAfter);
            sb.append(columnName);
        }

        return sb.toString();
    }
}