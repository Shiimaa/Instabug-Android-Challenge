package apps.instabugandroidchallenge.operations.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "DB.db";
    static final int DATABASE_VERSION = 1;
    static final String WEBSITE_CONTENT_TABLE_NAME = "websiteContent";
    static final String WEBSITE_CONTENT_COLUMN_ID = "id";
    static final String WEBSITE_CONTENT_COLUMN_WORD_NAME = "name";
    static final String WEBSITE_CONTENT_COLUMN_WORD_COUNT = "count";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + WEBSITE_CONTENT_TABLE_NAME +
                        "(" + WEBSITE_CONTENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + WEBSITE_CONTENT_COLUMN_WORD_NAME + " TEXT,"
                        + WEBSITE_CONTENT_COLUMN_WORD_COUNT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WEBSITE_CONTENT_TABLE_NAME);
        onCreate(db);
    }
}
