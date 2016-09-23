package cz.kuzna.android.dbvarna.entity;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;

/**
 * Base entity
 *
 * @author Radek Kuznik
 */
public abstract class BaseEntity implements IEntity {

    @PrimaryKey
    @Column
    protected long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
