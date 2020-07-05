package com.example.jishelpdesk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EmpAdapter extends FirestoreRecyclerAdapter<EmpModel, EmpAdapter.MyViewHolder> {


    public EmpAdapter(@NonNull FirestoreRecyclerOptions<EmpModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EmpAdapter.MyViewHolder holder, int position, @NonNull EmpModel model) {
        holder.Eid.setText(model.getEmpid());
        holder.name.setText(model.getName());
        holder.number.setText(model.getNumber());
    }



    @NonNull
    @Override
    public EmpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_emp, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Eid;
        TextView name;
        TextView number;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Eid = itemView.findViewById(R.id.tokenid);
            name = itemView.findViewById(R.id.cname);
            number = itemView.findViewById(R.id.date);



        }
    }
}


