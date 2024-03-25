package com.example.cartappdemo.Product.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cartappdemo.Cart.CartActivity;
import com.example.cartappdemo.Cart.model.ProductInCart;
import com.example.cartappdemo.Product.model.Product;
import com.example.cartappdemo.R;
import com.example.cartappdemo.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolderProduct> {
    Context context;
    ArrayList<Product> list;
    FirebaseUser currentUser;
    DatabaseReference databaseRef;
    DatabaseReference userRef;
    long totalPriceCurent;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_holder_product, parent, false);
        return new ViewHolderProduct(v);
//        View v = LayoutInflater.from(context).inflate(R.layout.view_holder_product,parent,false);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolderProduct holder, int position) {
        //Đổ dữ liệu vào item
        Product prd = list.get(position);
        ArrayList<ProductInCart> productsInCarts;
        productsInCarts = new ArrayList<>();
        Glide.with(context)
                .load(prd.getImage())
                .into(holder.imgProduct);
        String description = prd.getDescription();
        if (description.length() > 25) {
            description = description.substring(0, 25) + "...";
        }
        String title = prd.getName();
        if (title.length() > 10) {
            title = title.substring(0, 10) + "...";
        }
        holder.tvTitle.setText(title);
        holder.tvPrice.setText(String.valueOf(prd.getPrice()));
        holder.tvDescription.setText(description);

        //xu ly thêm vao gio hang
        FirebaseAuth.getInstance().signOut();
//        userRef = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid());
//        databaseRef = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/cart");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid());
            databaseRef = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getUid() + "/cart");
        }

        //Lay du lieu cho cart
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    ProductInCart productInCart = data.getValue(ProductInCart.class);
                    productsInCarts.add(productInCart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPriceCurent = Objects.requireNonNull(snapshot.getValue(User.class)).getTotalPrice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Su kien them san pham
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inCart = false;
                if (!productsInCarts.isEmpty()) {
//                    Toast.makeText(context.getApplicationContext(), "vào", Toast.LENGTH_SHORT).show();
                    for (ProductInCart prdInCart : productsInCarts) {
                        if (prdInCart.getId().equals(prd.getId())) {
                            inCart = true;
                            prdInCart.setQuantity(prdInCart.getQuantity() + 1);
                            databaseRef.child(prd.getId()).setValue(prdInCart);
                            userRef.child("totalPrice").setValue(totalPriceCurent + prdInCart.getPrice());
                            showSnackbar(v, "Thêm vào "+ prdInCart.getName()+" giỏ hàng", Snackbar.LENGTH_SHORT);
                        }
                    }

                    if (!inCart) {
                        // Neu san pham chua co trong gio hang (cart khong rong)
                        ProductInCart prdInCarttmp = new ProductInCart(prd.getId(), prd.getName(), prd.getImage(), prd.getDescription(), prd.getPrice(), 1);
                        productsInCarts.add(prdInCarttmp);
                        databaseRef.child(prd.getId()).setValue(prdInCarttmp);
                        userRef.child("totalPrice").setValue(totalPriceCurent + prdInCarttmp.getPrice());
                        showSnackbar(v, "Thêm vào "+ prdInCarttmp.getName()+" giỏ hàng", Snackbar.LENGTH_SHORT);

                    }
                } else {
                    //Cart chua co san pham nao
                    ProductInCart prdInCarttmp = new ProductInCart(prd.getId(), prd.getName(), prd.getImage(), prd.getDescription(), prd.getPrice(), 1);
//                    Toast.makeText(context.getApplicationContext(), prdInCarttmp.toString(), Toast.LENGTH_SHORT).show();
                    productsInCarts.add(prdInCarttmp);
                    databaseRef.child(prd.getId()).setValue(prdInCarttmp);
                    userRef.child("totalPrice").setValue(totalPriceCurent + prdInCarttmp.getPrice());
                    showSnackbar(v, "Thêm vào "+ prdInCarttmp.getName()+" giỏ hàng", Snackbar.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderProduct extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView tvTitle, tvPrice, tvDescription;
        Button btnAddToCart;

        public ViewHolderProduct(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).setAction("Cart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                context.startActivity(intent);
            }
        }).show();
    }
}
