
package com.example.orderapp;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NewProductsFragment extends Fragment {
    private DatabaseHelper db;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_products, container, false);
        db = new DatabaseHelper(requireContext());  // Use requireContext() instead of getContext()

        // Fetch new products from the database
        List<Product> newProducts = db.getProductsByStatus("new");

        // Initialize the adapter
        adapter = new ProductAdapter(newProducts, requireContext(), (ProductAdapter.OnProductClickListener) this) ;

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_new);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Add product button
        FloatingActionButton fab = view.findViewById(R.id.fab_add_product);
        fab.setOnClickListener(v -> showAddProductDialog());

        return view;
    }

    // Method to show the dialog for adding a new product
    private void showAddProductDialog() {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        EditText titleInput = dialogView.findViewById(R.id.edit_text_title);
        EditText priceInput = dialogView.findViewById(R.id.edit_text_price);
        EditText dateInput = dialogView.findViewById(R.id.edit_text_date);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView)
                .setTitle("Add New Product")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = titleInput.getText().toString();
                        String priceText = priceInput.getText().toString();
                        String date = dateInput.getText().toString();

                        // Validate input fields
                        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(priceText) || TextUtils.isEmpty(date)) {
                            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        double price;
                        try {
                            price = Double.parseDouble(priceText);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid price format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Insert product into database and update the RecyclerView
                        db.addProduct(title, price, date, "new");
                        updateProductList();
                        Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    // Method to refresh the product list after adding a new product
    private void updateProductList() {
        List<Product> newProducts = db.getProductsByStatus("new");
        adapter.updateList(newProducts);  // This method should be defined in ProductAdapter to update data
    }
}
