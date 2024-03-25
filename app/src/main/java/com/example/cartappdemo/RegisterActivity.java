package com.example.cartappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cartappdemo.Cart.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    AppCompatButton btnRegister;
    TextView btnLogin;
    EditText edtName, edtEmail, edtPassword;

    FirebaseAuth auth;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(RegisterActivity.this, edtEmail.getText().toString() + edtPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString()) && !TextUtils.isEmpty(edtName.getText().toString())) {
                    register();
                }
            }
        });

    }

    public void register() {
        Toast.makeText(getApplicationContext(), "Vào", Toast.LENGTH_SHORT).show();
        auth.createUserWithEmailAndPassword(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Đăng ký thành công, người dùng đã được tạo
                // Có thể lấy thông tin người dùng đã đăng ký từ task.getResult().getUser()
                FirebaseUser user = task.getResult().getUser();
                String emailCreate = user.getEmail();
                String Uid = user.getUid();
                Date now = new Date();
//                Cart cart = new Cart();
                User userCreate = new User(Uid, edtName.getText().toString(), emailCreate, "", now.toString(),0);
                usersRef.child(Uid).setValue(userCreate);
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                // Tiếp theo, bạn có thể thực hiện các hành động khác sau khi đăng ký thành công
            } else {
                // Đăng ký thất bại, xảy ra lỗi
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                Exception exception = task.getException();
                // Xử lý lỗi tại đây
            }
        });
    }
}