package com.example.cartappdemo.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartappdemo.Cart.adapter.CartAdapter;
import com.example.cartappdemo.Cart.model.ProductInCart;
import com.example.cartappdemo.Product.ProductActivity;
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
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    ImageView imgViewBack;
    CartAdapter cartAdapter;
    DatabaseReference cartRef;
    DatabaseReference userRef;
    FirebaseUser user;
    RecyclerView rcvCart;
    TextView tvTotal;
    long total;

    ArrayList<ProductInCart> productInCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        tvTotal = findViewById(R.id.tvTotal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
            cartRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/cart");
        }
        productInCarts = new ArrayList<>();
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productInCarts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductInCart productInCart = dataSnapshot.getValue(ProductInCart.class);
//                    long total = 0;
//                    total += productInCart.getPrice() * productInCart.getQuantity();
//                    tvTotal.setText(String.valueOf(total));
                    productInCarts.add(productInCart);
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        total = 0;
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total = Objects.requireNonNull(snapshot.getValue(User.class)).getTotalPrice();
                tvTotal.setText(String.valueOf(total));
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        cartAdapter = new CartAdapter(CartActivity.this, productInCarts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvCart = findViewById(R.id.rcvCart);
        rcvCart.setLayoutManager(linearLayoutManager);
        rcvCart.setAdapter(cartAdapter);


        imgViewBack = findViewById(R.id.imgViewBack);
        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(CartActivity.this, ProductActivity.class);
                startActivity(main);
            }
        });
    }
}