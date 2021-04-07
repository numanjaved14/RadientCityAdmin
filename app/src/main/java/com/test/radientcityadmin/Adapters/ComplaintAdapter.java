package com.test.radientcityadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.test.radientcityadmin.Models.ComplaintDataModel;
import com.test.radientcityadmin.R;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.recyclerViewAdapterHolder> {

    private Context context;
    List<ComplaintDataModel> list;

    public ComplaintAdapter(Context ct) {
        // this.list = list;
        context = ct;
    }

    @NonNull
    @Override
    public recyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_complaint, parent, false);
        final recyclerViewAdapterHolder mViewHolder = new recyclerViewAdapterHolder(mView);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapterHolder holder, int position) {
        holder.announce_title.setText(list.get(position).getCategary());
        holder.announce_description.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (list!=null && list.size()!=0){
            size = list.size();

        }
        return size;
    }

    public class recyclerViewAdapterHolder extends RecyclerView.ViewHolder {

        TextView announce_title, announce_description;

        public recyclerViewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            announce_title = itemView.findViewById(R.id.announce_title);
            announce_description = itemView.findViewById(R.id.announce_description);

        }
    }

    public void setList(List<ComplaintDataModel> listData) {
        this.list = listData;
        notifyDataSetChanged();
    }
}

