package pl.codeaddict.rssreaderforreddit.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kostek on 26.03.17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CHANNELS = "channels";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CHANNEL_NAME = "channel_name";
    public static final String COLUMN_CHANNEL_URL = "channel_url";

    private static final String DATABASE_NAME = "channels.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CHANNELS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_CHANNEL_URL
            + " text not null," + COLUMN_CHANNEL_NAME
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        onCreate(db);
    }

}