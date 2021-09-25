package apps.instabugandroidchallenge.operations.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import apps.instabugandroidchallenge.model.Word;
import apps.instabugandroidchallenge.utils.App;

public class DBOperations {
    private static DBOperations instance;
    private static final Object mutex = new Object();
    private final DBHelper dbHelper;

    private DBOperations() {
        dbHelper = new DBHelper(App.context);
    }

    public static DBOperations getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null)
                    instance = new DBOperations();
            }
        }

        return instance;
    }

    public void addWord(Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_ID, word.getId());
        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME, word.getName());
        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT, word.getCount());

        db.insert(DBHelper.WEBSITE_CONTENT_TABLE_NAME, null, values);
    }

    public Word getWord(int id) {
        Word selectedWord = new Word();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.WEBSITE_CONTENT_TABLE_NAME + " where " + DBHelper.WEBSITE_CONTENT_COLUMN_ID + "=" + id + "", null);

        if (cursor.moveToFirst()) {
            selectedWord.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_ID)));
            selectedWord.setName(cursor.getString(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME)));
            selectedWord.setCount(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT)));
        }

        cursor.close();

        return selectedWord;
    }

    public List<Word> getAllWords() {
        List<Word> selectedWords = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.WEBSITE_CONTENT_TABLE_NAME + " ORDER BY " + DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT + " DESC"
                , null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_ID)));
                word.setName(cursor.getString(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME)));
                word.setCount(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT)));

                selectedWords.add(word);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return selectedWords;
    }

    public List<Word> searchOnText(String text) {
        List<Word> selectedWords = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.WEBSITE_CONTENT_TABLE_NAME + " WHERE "
                        + DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME + " LIKE '%" + text + "%'"
                        + " ORDER BY " + DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT + " DESC"
                , null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_ID)));
                word.setName(cursor.getString(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME)));
                word.setCount(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT)));

                selectedWords.add(word);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return selectedWords;
    }

    public List<Word> getAllWordsAscending() {
        List<Word> selectedWords = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.WEBSITE_CONTENT_TABLE_NAME + " ORDER BY " + DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT + " ASC"
                , null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_ID)));
                word.setName(cursor.getString(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME)));
                word.setCount(cursor.getInt(cursor.getColumnIndex(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT)));

                selectedWords.add(word);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return selectedWords;
    }

    public void updateWord(Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_ID, word.getId());
        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_NAME, word.getName());
        values.put(DBHelper.WEBSITE_CONTENT_COLUMN_WORD_COUNT, word.getCount());

        db.update(DBHelper.WEBSITE_CONTENT_TABLE_NAME, values, DBHelper.WEBSITE_CONTENT_COLUMN_ID + "= ?", new String[]{String.valueOf(word.getId())});
    }

    public void removeData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.WEBSITE_CONTENT_TABLE_NAME, null, null);
    }
}
