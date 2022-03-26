package com.marcoedu.plentyvocabulary.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcoedu.plentyvocabulary.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeanAdapter extends RecyclerView.Adapter<MeanAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;
    private List<DayBean> mData;

    public MeanAdapter(Context context, List<DayBean> data, OnItemClickListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_day_list, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MeanAdapter.ViewHolder holder, int position) {
        if(holder == null) {
            return;
        }
        if(mData != null) {
            DayBean bean  = mData.get(position);
            holder.titleView.setText(bean.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View rootView;
        public TextView titleView;
        public TextView enterListView;
        public TextView startLearnView;
        private OnItemClickListener listener;

        public ViewHolder(@NonNull @NotNull View root, OnItemClickListener l) {
            super(root);
            rootView = root;
            titleView = rootView.findViewById(R.id.tv_title);
            enterListView = rootView.findViewById(R.id.tv_enter_list);
            startLearnView = rootView.findViewById(R.id.tv_start_learn);

            enterListView.setOnClickListener(this);
            startLearnView.setOnClickListener(this);
            listener = l;
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                if (v.getId() == R.id.tv_enter_list) {
                    listener.onEnterListClicked(v, getAdapterPosition());
                } else if (v.getId() == R.id.tv_start_learn) {
                    listener.onStartLearnClicked(v, getAdapterPosition());
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onEnterListClicked(View view, int position);
        void onStartLearnClicked(View view, int position);
    }
}
