package apps.instabugandroidchallenge.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import apps.instabugandroidchallenge.R;
import apps.instabugandroidchallenge.appQueues.AppQueues;
import apps.instabugandroidchallenge.databinding.ActivityMainBinding;
import apps.instabugandroidchallenge.operations.WebSiteDataOperations;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WordsAdapter wordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setData(this);

        setupRecyclerView();
        loadData();
    }

    private void setupRecyclerView() {
        wordsAdapter = new WordsAdapter();
        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.homeRecycler.setAdapter(wordsAdapter);
    }

    private void loadData() {
        binding.homeProgress.setVisibility(View.VISIBLE);
        binding.homeRecycler.setVisibility(View.GONE);
        WebSiteDataOperations.getParsedData(parsedWords -> {
            AppQueues.postToUiHandler(() -> {
                if (parsedWords.size() > 0) {
                    binding.homeProgress.setVisibility(View.GONE);
                    binding.homeRecycler.setVisibility(View.VISIBLE);
                    wordsAdapter.updateData(parsedWords);
                } else {
                    binding.homeNoDataTextview.setVisibility(View.VISIBLE);
                    binding.homeProgress.setVisibility(View.GONE);
                }
            });

        });
    }

    public void search(View view) {

    }

    public void sortData(View view) {

    }
}