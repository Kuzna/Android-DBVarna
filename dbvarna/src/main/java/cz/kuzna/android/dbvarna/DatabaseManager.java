package cz.kuzna.android.dbvarna;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Database manager
 *
 * @author Radek Kuznik
 */
public class DatabaseManager {

    private static final String TAG = "DatabaseManager";

    /** Constants */
    private static DatabaseManager mInstance;
    private static SQLiteOpenHelper mDatabaseHelper;

    /** Local variables */
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    /**
     * Initialize {@link SQLiteDatabase} by {@link SQLiteOpenHelper}.
     *
     * <p>Database is initialized only when instance is null, otherwise value in helper parameter is skipped.</p>
     *
     * @param helper
     */
    public static synchronized void  initializeInstance(final SQLiteOpenHelper helper) {
        if (mInstance == null) {
            mInstance = new DatabaseManager();
            mDatabaseHelper = helper;
            Log.d(TAG, "Database manager initialized");
        } else {
            Log.d(TAG, "Database manager is already initialized");
        }
    }

    /**
     * Return/create instance of DatabaseManager. Singleton pattern is used.
     *
     * <p>Before call this method make sure to initialize database by call {@link #initializeInstance}</p>
     *
     * @throws IllegalStateException is not initialized.
     * @return
     */
    public static synchronized DatabaseManager getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() + " is not initialized, call initializeInstance(..) method first.");
        }

        return mInstance;
    }

    /**
     * Create and/or open a database that will be used for reading and writing.
     *
     * <p>Once opened successfully, the database is cached, so you can
     * call this method every time you need to write to the database.
     * (Make sure to call {@link #closeDatabase} when you no longer need the database.)
     * Errors such as bad permissions or a full disk may cause this method
     * to fail, but future attempts may succeed if the problem is fixed.</p>
     *
     * @throws SQLiteException if the database cannot be opened for writing
     * @return a read/write database object valid until {@link #closeDatabase} is called
     */
    public synchronized SQLiteDatabase openDatabase() throws SQLiteException {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * Close database connection and decrease count of opened connections by 1.
     *
     * @throws SQLiteException if the database was already closed
     */
    public synchronized void closeDatabase() throws SQLiteException {
        if(mOpenCounter.decrementAndGet() == 0 && mDatabase != null) {
            // Closing database
            if(mDatabase.isOpen()) {
                mDatabase.close();
            }
        }
    }
}
