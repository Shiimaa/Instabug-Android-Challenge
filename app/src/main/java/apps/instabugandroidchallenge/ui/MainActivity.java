package apps.instabugandroidchallenge.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import apps.instabugandroidchallenge.R;
import apps.instabugandroidchallenge.appQueues.AppQueues;
import apps.instabugandroidchallenge.databinding.ActivityMainBinding;
import apps.instabugandroidchallenge.operations.WebSiteDataOperations;
import apps.instabugandroidchallenge.operations.network.NetworkOperations;
import apps.instabugandroidchallenge.utils.CommonMethods;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WordsAdapter wordsAdapter;
    private boolean isDescending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setData(this);

        setupRecyclerView();
        loadData();
        addListeners();
    }

    private void addListeners() {
        binding.homeSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSearchData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    WebSiteDataOperations.getDataDescending(parsedWords1 -> {
                        AppQueues.postToUiHandler(() -> {
                            if (parsedWords1.size() > 0) {
                                binding.homeProgress.setVisibility(View.GONE);
                                binding.homeRecycler.setVisibility(View.VISIBLE);
                                wordsAdapter.updateData(parsedWords1);
                            } else {
                                if (NetworkOperations.isNetworkError) {
                                    binding.homeNoDataTextview.setText(R.string.network_error);
                                    NetworkOperations.isNetworkError = false;
                                } else
                                    binding.homeNoDataTextview.setText(R.string.no_results);

                                binding.homeNoDataTextview.setVisibility(View.VISIBLE);
                                binding.homeProgress.setVisibility(View.GONE);
                            }
                        });
                    });
                }
            });

        });
    }

    public void search(View view) {
        binding.homeToolbar.setVisibility(View.GONE);
        binding.homeSearchContainer.setVisibility(View.VISIBLE);

        // open keyboard
        binding.homeSearchEditText.requestFocus();
        CommonMethods.openKeyboard(this);
    }

    private void loadSearchData(String searchText) {
        binding.homeProgress.setVisibility(View.VISIBLE);
        binding.homeRecycler.setVisibility(View.GONE);
        WebSiteDataOperations.searchOnText(searchText, parsedWords -> AppQueues.postToUiHandler(() -> {
            binding.homeProgress.setVisibility(View.GONE);
            if (parsedWords.size() > 0) {
                binding.homeRecycler.setVisibility(View.VISIBLE);
                binding.homeNoDataTextview.setVisibility(View.GONE);
                wordsAdapter.updateData(parsedWords);
            } else {
                binding.homeNoDataTextview.setText(R.string.no_results);
                binding.homeNoDataTextview.setVisibility(View.VISIBLE);
            }
        }));
    }

    public void sortData(View view) {
        if (isDescending) {
            isDescending = false;
            WebSiteDataOperations.getDataAscending(parsedWords ->
                    AppQueues.postToUiHandler(() -> wordsAdapter.updateData(parsedWords)));
        } else {
            isDescending = true;
            WebSiteDataOperations.getDataDescending(parsedWords ->
                    AppQueues.postToUiHandler(() -> wordsAdapter.updateData(parsedWords)));
        }
    }

    public void removeSearchText(View view) {
        binding.homeSearchEditText.setText("");
    }

    public void closeSearchBar(View view) {
        binding.homeToolbar.setVisibility(View.VISIBLE);
        binding.homeSearchContainer.setVisibility(View.GONE);
        binding.homeNoDataTextview.setVisibility(View.GONE);
        binding.homeNoDataTextview.setText(R.string.network_error);
        binding.homeRecycler.setVisibility(View.VISIBLE);
        binding.homeSearchEditText.setText("");

        // close keyboard
        CommonMethods.closeKeyboard(this);

        WebSiteDataOperations.getDataDescending(parsedWords ->
                AppQueues.postToUiHandler(() -> wordsAdapter.updateData(parsedWords)));
    }
}