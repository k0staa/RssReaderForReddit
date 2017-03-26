package pl.codeaddict.rssreaderforreddit.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.codeaddict.rssreaderforreddit.models.Channel;

/**
 * Created by kostek on 26.03.17.
 */

public class ChannelsDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CHANNEL_NAME, MySQLiteHelper.COLUMN_CHANNEL_URL};

    public ChannelsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Channel createChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CHANNEL_URL, channel.getChannelUrl());
        values.put(MySQLiteHelper.COLUMN_CHANNEL_NAME, channel.getChannelName());
        long insertId = database.insert(MySQLiteHelper.TABLE_CHANNELS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CHANNELS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Channel newChannel = cursorToChannel(cursor);
        cursor.close();
        return newChannel;
    }

    public void deleteChannel(Channel channel) {
        long id = channel.getId();
        database.delete(MySQLiteHelper.TABLE_CHANNELS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Channel> getAllChannel() {
        List<Channel> channels = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CHANNELS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Channel channel = cursorToChannel(cursor);
            channels.add(channel);
            cursor.moveToNext();
        }
        cursor.close();
        return channels;
    }

    private Channel cursorToChannel(Cursor cursor) {
        Channel channel = new Channel();
        channel.setId(cursor.getLong(0));
        channel.setChannelName(cursor.getString(1));
        channel.setChannelUrl(cursor.getString(2));
        return channel;
    }
}

