package com.example.jishelpdesk;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EmpAdapter extends FirestoreRecyclerAdapter<EmpModel, EmpAdapter.MyViewHolder> {


    public EmpAdapter(@NonNull FirestoreRecyclerOptions<EmpModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final EmpAdapter.MyViewHolder holder, int position, @NonNull final EmpModel model) {
        System.out.println(model.getEmpid());
        holder.Eid.setText(""+model.getEmpid());
        holder.name.setText(""+model.getName());
        holder.number.setText(""+model.getNumber());
        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.assign.getContext(), "Complaint Assigned to "+model.getName()+" Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(holder.assign.getContext(),ComplaintAdminActivity.class);

                holder.assign.getContext().startActivity(intent);

            }
        });
        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.assign.getContext(),EmpProfile.class);
                holder.assign.getContext().startActivity(intent);

            }
        });
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
        TextView assign;
        TextView viewProfile;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Eid = itemView.findViewById(R.id.empid);
            name = itemView.findViewById(R.id.ename);
            number = itemView.findViewById(R.id.date);
            assign=itemView.findViewById(R.id.assign);
            viewProfile=itemView.findViewById(R.id.view_profile);


        }
    }
}


