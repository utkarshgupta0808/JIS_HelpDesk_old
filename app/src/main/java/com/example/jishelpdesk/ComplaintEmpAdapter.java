package com.example.jishelpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ComplaintEmpAdapter extends FirestoreRecyclerAdapter<ComplaintModel, ComplaintEmpAdapter.MyViewHolder> {



    public ComplaintEmpAdapter(@NonNull FirestoreRecyclerOptions<ComplaintModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ComplaintEmpAdapter.MyViewHolder holder, int position, @NonNull final ComplaintModel model) {

        holder.tokenId.setText(""+model.getTokenId());
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.viewDetails.getContext(), ComplaintInfoEmpActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("tokenId", model.getTokenId());
                bundle.putString("name", model.getName());
                bundle.putString("date", model.getDate());
                bundle.putString("complaint", model.getComplaint());
                bundle.putString("address", model.getAddress());
                bundle.putString("number1", model.getMobile());
                intent.putExtras(bundle);
                holder.viewDetails.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ComplaintEmpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_complaint_emp, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tokenId;
        TextView name;
        TextView date;
        TextView viewDetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tokenId = itemView.findViewById(R.id.tokenid);
            name = itemView.findViewById(R.id.cname);
            date = itemView.findViewById(R.id.date);
            viewDetails=itemView.findViewById(R.id.view_details);
        }
    }
}
