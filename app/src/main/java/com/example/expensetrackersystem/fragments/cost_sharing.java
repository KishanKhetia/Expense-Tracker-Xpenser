package com.example.expensetrackersystem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandlerFriends;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.FriendExpenseAdapter;
import com.example.expensetrackersystem.model.FriendExpense;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cost_sharing  extends Fragment {

    private EditText etFriendName, etExpenseAmount;
    private Button btnAddExpense;
    private RecyclerView rvFriendExpenses;
    private TextView tvTotalExpenses;

    private List<FriendExpense> friendExpenseList;
    private FriendExpenseAdapter friendExpenseAdapter;

    private double totalExpenses = 0.0;

    public cost_sharing () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost_sharing, container, false);

        etFriendName = view.findViewById(R.id.et_friend_name);
        etExpenseAmount = view.findViewById(R.id.et_expense_amount);
        btnAddExpense = view.findViewById(R.id.btn_add_friend_expense);
        rvFriendExpenses = view.findViewById(R.id.rv_friend_expenses);
        tvTotalExpenses = view.findViewById(R.id.tv_total_expenses);

        friendExpenseList = new ArrayList<>();
        friendExpenseAdapter = new FriendExpenseAdapter(friendExpenseList);

        rvFriendExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFriendExpenses.setAdapter(friendExpenseAdapter);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriendExpense();
            }
        });

        loadFriendExpensesFromDatabase();

        return view;
    }

    private void addFriendExpense() {
        // Get the friend name and expense amount from EditText fields
        String friendName = etFriendName.getText().toString();
        String expenseAmountStr = etExpenseAmount.getText().toString();

        if (!friendName.isEmpty() && !expenseAmountStr.isEmpty()) {
            double expenseAmount = Double.parseDouble(expenseAmountStr);

            // Create a new FriendExpense object
            FriendExpense newFriendExpense = new FriendExpense(friendName, expenseAmount);

            // Add the new FriendExpense object to the list
            friendExpenseList.add(newFriendExpense);

            // Update the RecyclerView with the new data
            friendExpenseAdapter.notifyDataSetChanged();

            // Update the total expenses
            updateTotalExpensesTextView();

            // Clear the EditText fields
            etFriendName.setText("");
            etExpenseAmount.setText("");

            saveFriendExpenseToDatabase(newFriendExpense);

        } else {
            // Show a toast or error message for invalid input
            Toast.makeText(getActivity(), "Please enter valid values", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFriendExpensesFromDatabase() {
        // Assuming you have already initialized the DatabaseHandlerFriends object
        DatabaseHandlerFriends dbHandler = new DatabaseHandlerFriends(getActivity());

        // Get the list of friend expenses from the database
        List<FriendExpense> savedFriendExpenses = dbHandler.getAllFriendExpenses();

        if (!savedFriendExpenses.isEmpty()) {
            // Add the saved friend expenses to the list
            friendExpenseList.addAll(savedFriendExpenses);

            // Update the RecyclerView with the saved data
            friendExpenseAdapter.notifyDataSetChanged();

            // Calculate the total expenses from the saved data
            totalExpenses = 0.0;
            for (FriendExpense friendExpense : savedFriendExpenses) {
                totalExpenses += friendExpense.getExpenseAmount();
            }

            // Update the total expenses view
            tvTotalExpenses.setText(String.format(Locale.getDefault(), "Total Expenses: %.2f", totalExpenses));
        }
    }

    private void saveFriendExpenseToDatabase(FriendExpense friendExpense) {
        // Assuming you have already initialized the DatabaseHandlerFriends object
        DatabaseHandlerFriends dbHandler = new DatabaseHandlerFriends(getActivity());

        // Call the addData() method to insert the friend expense into the database
        boolean isInserted = dbHandler.addData(friendExpense.getFriendName(), friendExpense.getExpenseAmount());

        if (isInserted) {
            // Show a toast or message indicating that the expense has been added to the database successfully
            Toast.makeText(getActivity(), "Expense added to database", Toast.LENGTH_SHORT).show();
        } else {
            // Show a toast or message indicating that there was an error adding the expense to the database
            Toast.makeText(getActivity(), "Failed to add expense to database", Toast.LENGTH_SHORT).show();
        }
    }

//    private void updateTotalExpenses() {
//        double totalExpenses = calculateTotalExpenses();
//        String totalExpensesText = getString(R.string.total_expenses_format, totalExpenses);
//        tvTotalExpenses.setText(totalExpensesText);
//    }


    private double calculateTotalExpenses() {
        double totalExpenses = 0;
        for (FriendExpense friendExpense : friendExpenseList) {
            totalExpenses += friendExpense.getExpenseAmount();
        }
        return totalExpenses;
    }

    private void updateTotalExpensesTextView() {
        double totalExpenses = calculateTotalExpenses();
        tvTotalExpenses.setText(getString(R.string.total_expenses_format, totalExpenses));
    }
}