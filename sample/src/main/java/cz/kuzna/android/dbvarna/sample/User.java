package cz.kuzna.android.dbvarna.sample;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.Index;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;
import cz.kuzna.android.dbvarna.annotation.Table;

/**
 * @author Radek Kuznik
 */
@Table(generateMapper = true)
public class User {

    @PrimaryKey
    private long id;

    @Index
    @Column
    private String name;

    @Column
    private int age;

    @Column(name = "birthPlace")
    private int birthPlace;

    @Index
    @Column
    private double wallet;

    @Index
    @Column
    private boolean admin;

    private String ignored;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    public int getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(int birthPlace) {
        this.birthPlace = birthPlace;
    }
}
