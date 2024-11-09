package com.example.orderapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliveredProductsFragment extends Fragment {
    private DatabaseHelper db;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivered_products, container, false);
        db = new DatabaseHelper(requireContext());  // Ensure context is not null

        // Fetch delivered products from the database
        List<Product> deliveredProducts = db.getProductsByStatus("delivered");

        // Initialize the adapter with the delivered products
        adapter = new ProductAdapter(deliveredProducts, requireContext(), (ProductAdapter.OnProductClickListener) this);  // Now only two arguments are passed

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_delivered);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
