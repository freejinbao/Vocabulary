package com.marcoedu.plentyvocabulary.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcoedu.plentyvocabulary.R;
import com.marcoedu.plentyvocabulary.word.WordBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;
    private List<WordBean> mData;

    public WordListAdapter(Context context, List<WordBean> data, OnItemClickListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_word_list, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WordListAdapter.ViewHolder holder, int position) {
        if(holder == null) {
            return;
        }
        if(mData != null) {
            WordBean bean  = mData.get(position);
            holder.titleView.setText((position+1)+"."+bean.getWord());
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View rootView;
        public TextView titleView;
        private OnItemClickListener listener;

        public ViewHolder(@NonNull @NotNull View root, OnItemClickListener l) {
            super(root);
            rootView = root;
            titleView = rootView.findViewById(R.id.tv_title);
            rootView.setOnClickListener(this);
            listener = l;
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                listener.onItemClicked(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }
}
