package com.example.cartappdemo.Cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cartappdemo.Cart.model.Cart;
import com.example.cartappdemo.Cart.model.ProductInCart;
import com.example.cartappdemo.R;
import com.example.cartappdemo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderCart> {
    Context context;
    ArrayList<ProductInCart> list;
    DatabaseReference cartRef;
    DatabaseReference useRef;
    FirebaseUser user;
    long totalPrice;


    public CartAdapter(Context context, ArrayList<ProductInCart> list) {
        this.context = context;
        this.list = list;
    }

    public CartAdapter(Context context, ArrayList<ProductInCart> list,long totalPrice) {
        this.context = context;
        this.list = list;
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_holder_item_in_cart, parent, false);
        return new ViewHolderCart(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolderCart holder, int position) {
        ProductInCart productInCart = list.get(position);
        Glide.with(context)
                .load(productInCart.getImage())
                .into(holder.imageView);
        holder.tvTitle.setText(productInCart.getName());
        holder.tvQuantity.setText(String.valueOf(productInCart.getQuantity()));
        String description = productInCart.getDescription();
        if (description.length() > 25) {
            description = description.substring(0, 25) + "...";
        }
        holder.tvShortDescription.setText(description);
        holder.tvPrice.setText(String.valueOf(productInCart.getPrice()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            useRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid()+"/totalPrice");
            cartRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/cart/");
        }

//        totalPrice = 0;
//        lỗi
        useRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPrice = snapshot.getValue(long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "tăng", Toast.LENGTH_SHORT).show();
                productInCart.setQuantity(productInCart.getQuantity() + 1);
                cartRef.child(productInCart.getId()).setValue(productInCart);
                totalPrice += productInCart.getPrice();
                useRef.setValue(totalPrice);
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "giảm", Toast.LENGTH_SHORT).show();
                if(productInCart.getQuantity()==1){
                    totalPrice -= productInCart.getPrice();
                    useRef.setValue(totalPrice);
                    cartRef.child(productInCart.getId()).removeValue();
                }else{
                    productInCart.setQuantity(productInCart.getQuantity() - 1);
                    cartRef.child(productInCart.getId()).setValue(productInCart);
                    totalPrice -= productInCart.getPrice();
                    useRef.setValue(totalPrice);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderCart extends RecyclerView.ViewHolder {
        TextView tvTitle, tvShortDescription, tvPrice, tvQuantity;
        TextView btnIncrease, btnDecrease;
        ImageView imageView;

        public ViewHolderCart(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvShortDescription = itemView.findViewById(R.id.tvShortDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            imageView = itemView.findViewById(R.id.imageView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
