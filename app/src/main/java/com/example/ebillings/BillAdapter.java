package com.example.ebillings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebillings.Model.Bill;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BillAdapter extends FirebaseRecyclerAdapter<Bill, BillAdapter.BillViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context context;
    private String phone;
    public BillAdapter(@NonNull FirebaseRecyclerOptions<Bill> options, Context context, String phone) {
        super(options);
        this.context = context;
        this.phone = phone;
    }

    @Override
    protected void onBindViewHolder(@NonNull BillViewHolder holder, int position, @NonNull final Bill model) {
        if(model.getPhone().equals(phone)){
            holder.type.setText("Bill ID: " + model.getId());
            //holder.mode.setText("Mode of Payment: "+model.getMode());
            holder.mode.setText("Mode of Payment: Cash");
            holder.amount.setText("Amount Payed: " + "Rs."+model.getAmount());
            holder.date.setText("Purchase Date: " + model.getPurchase_Date());
        }
        else{
            holder.view.setVisibility(View.GONE);
            holder.view.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Bill ID: " + model.getId(),Toast.LENGTH_SHORT).show();
                Intent billDetails = new Intent(context,BillDetails.class);
                billDetails.putExtra("billID", model.getId());
                billDetails.putExtra("billDate", model.getPurchase_Date());
                context.startActivity(billDetails);
            }
        });
        //HomeActivity.progressDialog.dismiss();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_row, parent, false);

        return new BillViewHolder(view);
    }

    class BillViewHolder extends RecyclerView.ViewHolder{
        TextView type,mode,amount,date;
        View view;
        public BillViewHolder(@NonNull View itemView){
            super(itemView);
            type =itemView.findViewById(R.id.type);
            mode = itemView.findViewById(R.id.mode);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            view = itemView;
        }
    }
}
