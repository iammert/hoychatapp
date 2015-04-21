package akilliyazilim.justhoy.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import akilliyazilim.justhoy.listeners.ConversationListener;
import akilliyazilim.justhoy.model.ConversationInfo;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class ConversationsTable {

    public static ConversationsTable instance = null;

    private final String CREATE_TABLE_QUERY;
    private String TABLE_NAME_CONVERSATIONS="conversations";
    private final static String ALTER_TABLE = "ALTER TABLE";

    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_STRANGER_ID = "stranger_id";
    public static final String COLUMN_STRANGER_NAME = "stranger_name";
    public static final String COLUM_MESSAGES_ISUNREAD = "isunread";

    public static final int INDEX_COLUMN_NAME_ID = 0;
    public static final int INDEX_COLUMN_IMAGE_URL_ID = 1;
    public static final int INDEX_COLUMN_STRANGER_ID = 2;
    public static final int INDEX_COLUMN_STRANGER_NAME = 3;
    public static final int INDEX_COLUMN_UNREAD = 4;

    ConversationListener listener;

    private SQLiteDatabase db;

    private ConversationsTable(SQLiteDatabase db) {
        this.db = db;

        CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CONVERSATIONS + " ("
                + COLUMN_NAME_ID + " integer primary key, "
                + COLUMN_IMAGE_URL + " text not null, "
                + COLUMN_STRANGER_ID + " text not null, "
                + COLUMN_STRANGER_NAME + " text not null, "
                + COLUM_MESSAGES_ISUNREAD + " text not null, "
                + "UNIQUE ("+ COLUMN_STRANGER_ID +") ON CONFLICT REPLACE);";
    }

    public static ConversationsTable getInstance(SQLiteDatabase db)
    {
        if(instance == null)
            instance = new ConversationsTable(db);
        return instance;
    }

    public String getCreateTableQuery()
    {
        return CREATE_TABLE_QUERY;
    }

    public String getTableName()
    {
        return TABLE_NAME_CONVERSATIONS;
    }

    public void createTable() {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSATIONS);
    }

    public void closeDatabase() {
        db.close();
    }

    public long insertConversation(ConversationInfo conversation) {

        Log.v("TEST","Covnersation inserted");
        ContentValues cv = new ContentValues();
        cv.put(ConversationsTable.COLUMN_IMAGE_URL, conversation.getStranger_image_url());
        cv.put(ConversationsTable.COLUMN_STRANGER_ID, conversation.getStranger_id());
        cv.put(ConversationsTable.COLUMN_STRANGER_NAME, conversation.getStranger_name());
        cv.put(ConversationsTable.COLUM_MESSAGES_ISUNREAD, conversation.getIsUnread());
        if(!checkConversationExists(conversation.getStranger_id()))
        {
            long x = db.insert(TABLE_NAME_CONVERSATIONS, null, cv);
            notifyListener();
            return x;
        }
        else
            return -1;
    }


    public ArrayList<ConversationInfo> getAllConversations() {
        Cursor c = db.query(TABLE_NAME_CONVERSATIONS, null, COLUMN_NAME_ID, null, null, null, null);

        ArrayList<ConversationInfo> conversations = new ArrayList<ConversationInfo>();

        if(c.moveToFirst()) {
            do {
                ConversationInfo convers = new ConversationInfo();
                convers.setStranger_id(c.getString(INDEX_COLUMN_STRANGER_ID));
                convers.setStranger_image_url(c.getString(INDEX_COLUMN_IMAGE_URL_ID));
                convers.setStranger_name(c.getString(INDEX_COLUMN_STRANGER_NAME));
                convers.setIsUnread(c.getString(INDEX_COLUMN_UNREAD));
                conversations.add(convers);
            } while(c.moveToNext());
        }

        return conversations;
    }

    public int removeAllMessages()
    {
        int r = db.delete(TABLE_NAME_CONVERSATIONS, null, null);
        return r;
    }

    public boolean checkConversationExists(String stranger_id)
    {
        boolean exists = false;
        Cursor c = db.query(TABLE_NAME_CONVERSATIONS, null, COLUMN_NAME_ID, null, null, null, null);
        if(c.moveToFirst()) {
            do {
                String stranger_ = c.getString(INDEX_COLUMN_STRANGER_ID);
                if(stranger_.equals(stranger_id))
                    exists=true;
            } while(c.moveToNext());
        }

        return exists;
    }

    public void updateConversation(ConversationInfo conversationinfo)
    {
        ContentValues cv = new ContentValues();
        cv.put(ConversationsTable.COLUMN_IMAGE_URL, conversationinfo.getStranger_image_url());
        cv.put(ConversationsTable.COLUMN_STRANGER_ID, conversationinfo.getStranger_id());
        cv.put(ConversationsTable.COLUMN_STRANGER_NAME, conversationinfo.getStranger_name());
        cv.put(ConversationsTable.COLUM_MESSAGES_ISUNREAD, conversationinfo.getIsUnread());

        db.update(TABLE_NAME_CONVERSATIONS, cv, COLUMN_STRANGER_ID+" "+"="+conversationinfo.getStranger_id(), null);
        if(listener != null)
            notifyListener();
    }

    public void setConversationAddListener(ConversationListener listener)
    {
        this.listener = listener;
    }

    public void notifyListener()
    {
        if(listener!=null)
            listener.onConversationHappened();
    }
}
