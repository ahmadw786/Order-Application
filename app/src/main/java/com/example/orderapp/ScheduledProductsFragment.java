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

public class ScheduledProductsFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private DatabaseHelper db;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduled_products, container, false);
        db = new DatabaseHelper(requireContext());  // Ensure context is not null

        // Fetch scheduled products from the database
        List<Product> scheduledProducts = db.getProductsByStatus("scheduled");

        // Initialize adapter with the OnProductClickListener
        adapter = new ProductAdapter(scheduledProducts, requireContext(), this);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_scheduled);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        // When a scheduled product is clicked, move it to delivered status
        db.updateProductStatus(product.getId(), "delivered");
        updateProductList();
    }

    // Method to refresh the product list after updating the status
    private void updateProductList() {
        List<Product> scheduledProducts = db.getProductsByStatus("scheduled");
        adapter.updateList(scheduledProducts);  // Refresh the RecyclerView
    }
}
