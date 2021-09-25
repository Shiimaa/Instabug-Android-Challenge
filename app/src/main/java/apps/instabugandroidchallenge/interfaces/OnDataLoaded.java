package apps.instabugandroidchallenge.interfaces;

import java.util.List;

import apps.instabugandroidchallenge.model.Word;

public interface OnDataLoaded {
    public void onDataLoaded(List<Word> parsedWords);
}
