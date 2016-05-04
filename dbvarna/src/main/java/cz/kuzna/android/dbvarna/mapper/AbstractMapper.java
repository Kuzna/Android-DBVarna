package cz.kuzna.android.dbvarna.mapper;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Abstract mapper
 *
 * @author Radek Kuznik
 */
public abstract class AbstractMapper<T> {

    public T mapFrom(final Cursor cursor) {
        return mapFrom(cursor, "");
    }

    public abstract T mapFrom(final Cursor cursor, final String prefix);

    public abstract ContentValues mapTo(final T entity, final boolean insert);
}
