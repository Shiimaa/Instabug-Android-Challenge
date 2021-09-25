package apps.instabugandroidchallenge.ui;

import org.junit.Test;

import java.util.List;

import apps.instabugandroidchallenge.model.Word;
import apps.instabugandroidchallenge.operations.WebSiteDataOperations;
import apps.instabugandroidchallenge.operations.db.DBOperations;

import static org.junit.Assert.assertEquals;

public class LoadDataTest {

    @Test
    public void loadDataWithEmptyString() {

        String body = "";
        List<Word> content = WebSiteDataOperations.parseWebsiteContent(body);
        assertEquals(content.size(), 0);
    }

    @Test
    public void loadDataWithAllSpecialChars() {
        String body = "+-.,;:&^%$#@*?><";
        List<Word> content = WebSiteDataOperations.parseWebsiteContent(body);
        assertEquals(content.size(), 0);
    }

    @Test
    public void loadDataWithBody() {
        String body = "test+application-. for normal body, this test for; check current: body&^%$#@*?><";
        List<Word> content = WebSiteDataOperations.parseWebsiteContent(body);
        assertEquals(content.size(), 8);
    }

}