package akilliyazilim.justhoy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME_HOY = "hoy";
    public static final int DATABASE_VERSION = 1;
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME_HOY, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        (ConversationsTable.getInstance(db)).createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ConversationsTable.getInstance(db).dropTable();
        onCreate(db);
    }

    public ConversationsTable getConversationTableGateway()
    {
        return ConversationsTable.getInstance(this.getWritableDatabase());
    }

    public SeperateMessageTable getSeperateMessageTableGateway(String table_name)
    {
        SeperateMessageTable seperate = new SeperateMessageTable(this.getWritableDatabase(),"messagetable" + table_name);
        seperate.createTable();
        return seperate;
    }

}

