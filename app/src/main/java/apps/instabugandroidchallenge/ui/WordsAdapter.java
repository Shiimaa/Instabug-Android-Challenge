package apps.instabugandroidchallenge.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apps.instabugandroidchallenge.R;
import apps.instabugandroidchallenge.databinding.LayoutHomeItemBinding;
import apps.instabugandroidchallenge.model.Word;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {
    private List<Word> words;

    public void updateData(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutHomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_home_item, parent, false);
        return new WordsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsAdapter.WordsViewHolder holder, int position) {
        holder.binding.homeItemWordText.setText(words.get(position).getName());
        holder.binding.homeItemWordCount.setText(String.valueOf(words.get(position).getCount()));

        if (position == words.size() - 1)
            holder.binding.homeItemSeparator.setVisibility(View.GONE);
        else
            holder.binding.homeItemSeparator.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (words == null)
            return 0;

        return words.size();
    }

    public static class WordsViewHolder extends RecyclerView.ViewHolder {
        final LayoutHomeItemBinding binding;

        public WordsViewHolder(@NonNull LayoutHomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
