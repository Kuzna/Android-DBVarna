package cz.kuzna.android.dbvarna.entity;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;

/**
 * Base entity
 *
 * @author Radek Kuznik
 */
public abstract class BaseEntity {

    @PrimaryKey
    @Column
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
