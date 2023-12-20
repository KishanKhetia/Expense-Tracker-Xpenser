package com.example.expensetrackersystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.FriendExpense;

import java.util.List;

public class FriendExpenseAdapter extends RecyclerView.Adapter<FriendExpenseAdapter.ViewHolder> {
    private List<FriendExpense> friendExpenses;

    public FriendExpenseAdapter(List<FriendExpense> friendExpenses) {
        this.friendExpenses = friendExpenses;
    }

    public void setFriendExpenses(List<FriendExpense> friendExpenses) {
        this.friendExpenses = friendExpenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendExpense friendExpense = friendExpenses.get(position);
        holder.tvFriendName.setText(friendExpense.getFriendName());
        holder.tvExpenseAmount.setText(String.format("Ksh %.2f", friendExpense.getExpenseAmount()));
    }

    @Override
    public int getItemCount() {
        return friendExpenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFriendName;
        TextView tvExpenseAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFriendName = itemView.findViewById(R.id.tv_friend_name);
            tvExpenseAmount = itemView.findViewById(R.id.tv_expense_amount);
        }
    }
}

