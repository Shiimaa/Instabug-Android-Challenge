package apps.instabugandroidchallenge.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import apps.instabugandroidchallenge.appQueues.AppQueues;
import apps.instabugandroidchallenge.interfaces.OnDataLoaded;
import apps.instabugandroidchallenge.model.Word;
import apps.instabugandroidchallenge.operations.db.DBOperations;
import apps.instabugandroidchallenge.operations.network.NetworkOperations;
import apps.instabugandroidchallenge.utils.CommonMethods;

public class WebSiteDataOperations {

    public static void getParsedData(OnDataLoaded onDataLoaded) {
        if (onDataLoaded == null)
            throw new RuntimeException("OnDataLoaded is Null!");

        AppQueues.postToNetworkHandler(() -> {
            if (CommonMethods.checkConnection()) {
                String websiteContent = NetworkOperations.getWebSiteContent("https://instabug.com/");
                List<Word> words = parseWebsiteContent(websiteContent);
                AppQueues.postToDbHandler(() -> saveDataToDb(words));
                onDataLoaded.onDataLoaded(words);
            } else {
                AppQueues.postToDbHandler(() -> onDataLoaded.onDataLoaded(DBOperations.getInstance().getAllWords()));
            }
        });

    }

    public static void getDataAscending(OnDataLoaded onDataLoaded) {
        if (onDataLoaded == null)
            throw new RuntimeException("OnDataLoaded is Null!");
        AppQueues.postToDbHandler(() -> onDataLoaded.onDataLoaded(DBOperations.getInstance().getAllWordsAscending()));

    }

    public static void getDataDescending(OnDataLoaded onDataLoaded) {
        if (onDataLoaded == null)
            throw new RuntimeException("OnDataLoaded is Null!");

        AppQueues.postToDbHandler(() -> onDataLoaded.onDataLoaded(DBOperations.getInstance().getAllWords()));

    }

    public static void searchOnText(String text, OnDataLoaded onDataLoaded) {
        if (onDataLoaded == null)
            throw new RuntimeException("OnDataLoaded is Null!");
        AppQueues.postToDbHandler(() -> onDataLoaded.onDataLoaded(DBOperations.getInstance().searchOnText(text)));

    }

    public static List<Word> parseWebsiteContent(String body) {

        List<Word> words = new ArrayList<>();

        // remove all special characters from string
        body = body.replaceAll("(?<!\\w)\\W+|\\W+(?!\\w)", " ");

        // create hashMap to store each word occurrence
        HashMap<String, Integer> wordsOccurrence = new HashMap<>();

        // split body string to words
        String[] contentWords = body.split(" ");

        // calculate occurrence of each word
        for (String word : contentWords) {
            if (word.isEmpty())
                continue;

            if (wordsOccurrence.containsKey(word)) {
                int count = wordsOccurrence.get(word);
                count++;
                wordsOccurrence.put(word, count);
            } else {
                wordsOccurrence.put(word, 1);
            }
        }

        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(wordsOccurrence.entrySet());

        // Sort the list
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        //loop on map and retrieve all words
        for (Map.Entry<String, Integer> wordOccurrence : list) {
            Word word = new Word();
            word.setName(wordOccurrence.getKey());
            word.setCount(wordOccurrence.getValue());
            words.add(word);
        }

        return words;
    }

    private static void saveDataToDb(List<Word> words) {
        if (DBOperations.getInstance().getAllWords().size() > 0)
            DBOperations.getInstance().removeData();

        for (Word word : words) {
            DBOperations.getInstance().addWord(word);
        }
    }

}
