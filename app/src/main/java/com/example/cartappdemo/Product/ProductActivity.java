package com.example.cartappdemo.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartappdemo.Cart.CartActivity;
import com.example.cartappdemo.Cart.model.Cart;
import com.example.cartappdemo.Cart.model.ProductInCart;
import com.example.cartappdemo.LoginActivity;
import com.example.cartappdemo.Product.model.Product;
import com.example.cartappdemo.Product.adapter.ProductAdapter;
import com.example.cartappdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    RecyclerView rcvproduct;
    ArrayList<Product> products;
    ArrayList<ProductInCart> productsInCarts;
    DatabaseReference database;
    ProductAdapter productAdapter;
    ImageButton imgBtnCart,imgbtnLogout;
    FirebaseUser currentUser;
    DatabaseReference databaseRef;
    TextView textView;

//    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        products = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("product");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Product prd = dataSnapshot.getValue(Product.class);
                    products.add(prd);
                }
                productAdapter.notifyDataSetChanged(); // render lai data sau khi thay doi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcvproduct = findViewById(R.id.rcvPrd);
        productAdapter = new ProductAdapter(ProductActivity.this,products);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductActivity.this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        rcvproduct.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductActivity.this,2);
        rcvproduct.setLayoutManager(gridLayoutManager);
        rcvproduct.setAdapter(productAdapter);

        imgBtnCart = findViewById(R.id.imgBtnCart);
        imgBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(cart);
            }
        });

        imgbtnLogout = findViewById(R.id.imgbtnLogout);
        imgbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(ProductActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
}