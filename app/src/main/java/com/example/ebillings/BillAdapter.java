package com.example.ebillings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BillAdapter extends FirebaseRecyclerAdapter<Bill , BillAdapter.BillViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BillAdapter(@NonNull FirebaseRecyclerOptions<Bill> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BillViewHolder holder, int position, @NonNull Bill model) {
        holder.type.setText("Type of Bill: "+model.getType());
        holder.mode.setText("Mode of Payment: "+model.getMode());
        holder.amount.setText("Amount Payed: " + "Rs."+model.getAmount());
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_row, parent, false);

        return new BillViewHolder(view);
    }

    class BillViewHolder extends RecyclerView.ViewHolder{
        TextView type,mode,amount;
        public BillViewHolder(@NonNull View itemView){
            super(itemView);
            type =itemView.findViewById(R.id.type);
            mode = itemView.findViewById(R.id.mode);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
