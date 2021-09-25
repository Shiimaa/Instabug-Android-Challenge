package apps.instabugandroidchallenge.ui;

import org.junit.Test;

import java.util.List;

import apps.instabugandroidchallenge.model.Word;
import apps.instabugandroidchallenge.operations.db.DBOperations;

import static org.junit.Assert.assertEquals;

public class LoadSearchDataTest {

    @Test
    public void getAllData() {
        List<Word> content = DBOperations.getInstance().getAllWords();
        assertEquals(content.size(), 401);
    }

    @Test
    public void searchBySpecialChars() {
        String body = "+";
        List<Word> content = DBOperations.getInstance().searchOnText(body);
        assertEquals(content.size(), 0);
    }

    @Test
    public void searchByExistingWord() {
        String body = "Inst";
        List<Word> content = DBOperations.getInstance().searchOnText(body);
        assertEquals(content.size(), 3);
    }

    @Test
    public void searchByNotExistingWord() {
        String body = "tfh";
        List<Word> content = DBOperations.getInstance().searchOnText(body);
        assertEquals(content.size(), 0);
    }

}