package com.example.trabalhocontrolegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Criando atributos necessários (Firebase, Widgets e Intent)
    private FirebaseAuth mAuth;
    TextInputLayout edTxEmail, edTxSenha;
    Button btnLogin, btnCadastrar;
    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciação dos Widgets
        edTxEmail = findViewById(R.id.id_edit_text_email);
        edTxSenha = findViewById(R.id.id_edit_text_password);
        btnLogin = findViewById(R.id.id_btn_login);
        btnCadastrar = findViewById(R.id.id_btn_cadastrar);

        // Pegando instancia do Firebase
        mAuth = FirebaseAuth.getInstance();

        //Eventos de clique
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticar();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){ }
    }

    private void cadastrar(){
        String usuario, senha;
        usuario = edTxEmail.getEditText().getText().toString();
        senha = edTxSenha.getEditText().getText().toString();
        mAuth.createUserWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Cadastrado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "A operação de cadastro fracassou.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void autenticar() {
        String usuario, senha;
        usuario = edTxEmail.getEditText().getText().toString();
        senha = edTxSenha.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            it = new Intent(MainActivity.this, PrincipalActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Deu errado.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}