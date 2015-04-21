package akilliyazilim.justhoy.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import akilliyazilim.justhoy.listeners.MessagingListener;
import akilliyazilim.justhoy.model.MessageText;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class SeperateMessageTable {

    public static MessagingListener listener;

    private final String CREATE_TABLE_QUERY;
    private String TABLE_NAME_SEPERATE_MESSAGING=null;
    private final static String ALTER_TABLE = "ALTER TABLE";

    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_MESSAGE_TEXT = "message_text";
    public static final String COLUMN_STRANGER_ID = "stranger_id";
    public static final String COLUMN_MY_ID = "my_id";
    public static final String COLUMN_WHOIS = "whois";

    public static final int INDEX_COLUMN_NAME_ID = 0;
    public static final int INDEX_COLUMN_MESSAGE_ID = 1;
    public static final int INDEX_COLUMN_STRANGER_ID = 2;
    public static final int INDEX_COLUMN_MY_ID = 3;
    public static final int INDEX_COLUMN_WHOIS_ID = 4;

    private SQLiteDatabase db;
    private String table_name;

    public SeperateMessageTable(SQLiteDatabase db,String table_name) {
        this.db = db;
        this.table_name = table_name;

        TABLE_NAME_SEPERATE_MESSAGING = table_name;

        CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
                + COLUMN_NAME_ID + " integer primary key, "
                + COLUMN_MESSAGE_TEXT + " text not null, "
                + COLUMN_STRANGER_ID + " text not null, "
                + COLUMN_MY_ID + " text not null, "
                + COLUMN_WHOIS + " text not null);";
    }

    public String getCreateTableQuery()
    {
        return CREATE_TABLE_QUERY;
    }

    public String getTableName()
    {
        return TABLE_NAME_SEPERATE_MESSAGING;
    }

    public void createTable() {
        db.execSQL(CREATE_TABLE_QUERY);
        Log.v("SEPERATE TABLE CREATED", "SEPERATE TABLE CREATED Table name : " + table_name);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SEPERATE_MESSAGING);
    }

    public void closeDatabase() {
        db.close();
    }

    public long insertMessage(MessageText message,String whois_message) {

        Log.v("TEST","mesage Inserted ");
        ContentValues cv = new ContentValues();
        cv.put(SeperateMessageTable.COLUMN_MESSAGE_TEXT, message.getMessage_text());
        cv.put(SeperateMessageTable.COLUMN_STRANGER_ID, message.getStranger_id());
        cv.put(SeperateMessageTable.COLUMN_MY_ID, message.getMy_id());
        cv.put(SeperateMessageTable.COLUMN_WHOIS,whois_message);
        long x = db.insert(TABLE_NAME_SEPERATE_MESSAGING, null, cv);
        notifyListener();
        return x;
    }


    public ArrayList<MessageText> getAllMessagingMessages() {
        Cursor c = db.query(TABLE_NAME_SEPERATE_MESSAGING, null, COLUMN_NAME_ID, null, null, null, null);

        ArrayList<MessageText> messages = new ArrayList<MessageText>();

        if(c.moveToFirst()) {
            do {
                MessageText m = new MessageText();
                m.setMessage_text(c.getString(INDEX_COLUMN_MESSAGE_ID));
                m.setStranger_id(c.getString(INDEX_COLUMN_STRANGER_ID));
                m.setMy_id(c.getString(INDEX_COLUMN_MY_ID));
                m.setWhois(c.getString(INDEX_COLUMN_WHOIS_ID));
                messages.add(m);
            } while(c.moveToNext());
        }

        return messages;
    }

    public int removeAllMessages()
    {
        int r = db.delete(TABLE_NAME_SEPERATE_MESSAGING, null, null);
        return r;
    }

    public void setMessagingListener(MessagingListener listener)
    {
        this.listener = listener;
    }

    public static void notifyListener()
    {
        if(listener != null)
        {
            listener.onMessageInserted();
        }
    }

}
