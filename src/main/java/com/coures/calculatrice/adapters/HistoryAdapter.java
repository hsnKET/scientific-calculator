package com.coures.calculatrice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coures.calculatrice.R;
import com.coures.calculatrice.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


    private ArrayList<History> histories;
    private Context context;

    public HistoryAdapter(Context context,ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;

    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(histories.get(position));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView expressionTV,resultTv;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            expressionTV=itemView.findViewById(R.id.expressionTV);
            resultTv=itemView.findViewById(R.id.resultTv);
        }
        public void bind(History history){
            expressionTV.setText("   "+history.getExpression());
            resultTv.setText(" ="+history.getResult());
        }
    }
}
