package cz.kuzna.android.dbvarna.sample;

import cz.kuzna.android.dbvarna.annotation.Column;
import cz.kuzna.android.dbvarna.annotation.PrimaryKey;
import cz.kuzna.android.dbvarna.annotation.Table;

/**
 * @author Radek Kuznik
 */
@Table(name = "user_profile")
public class UserProfile {

    @PrimaryKey
    private long id;

    @Column
    private String name;

    @Column
    private int age;

    private double wallet;

    private boolean admin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
