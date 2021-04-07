package com.test.radientcityadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.radientcityadmin.Models.ComplaintDataModel;
import com.test.radientcityadmin.Models.ServiceDataModel;
import com.test.radientcityadmin.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.recyclerViewAdapterHolder> {

    private Context context;
    List<ServiceDataModel> list;

    public ServiceAdapter(Context ct) {
        // this.list = list;
        context = ct;
    }

    @NonNull
    @Override
    public recyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_service, parent, false);
        final recyclerViewAdapterHolder mViewHolder = new recyclerViewAdapterHolder(mView);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapterHolder holder, int position) {
        holder.announce_cat.setText(list.get(position).getCategary());
        holder.tv_description.setText(list.get(position).getDescription());
        holder.tv_servicedate.setText(list.get(position).getServiceDate());
        holder.tv_servicetime.setText(list.get(position).getServiceTime());

    }

    private void setServiceStatus(String parentId, String firebaseId, String status) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Service").child(parentId).child(firebaseId).child("serviceStatus");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.setValue(status);
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

        TextView announce_cat, tv_description,tv_servicedate,tv_servicetime;
        Button btn_accept,btn_reject;

        public recyclerViewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            announce_cat = itemView.findViewById(R.id.announce_cat);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_servicedate = itemView.findViewById(R.id.tv_servicedate);
            tv_servicetime = itemView.findViewById(R.id.tv_servicetime);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_reject = itemView.findViewById(R.id.btn_reject);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setServiceStatus(list.get(getAdapterPosition()).getParentId(),list.get(getAdapterPosition()).getFirebaseId(),"accepted");

                }
            });
            btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setServiceStatus(list.get(getAdapterPosition()).getParentId(),list.get(getAdapterPosition()).getFirebaseId(),"rejected");

                }
            });

        }
    }

    public void setList(List<ServiceDataModel> listData) {
        this.list = listData;
        notifyDataSetChanged();
    }
}

