package com.example.trabalhocontrolegastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarDespesaActivity extends AppCompatActivity {

    ImageView animationView;
    AnimationDrawable animationDrawable;
    EditText edTextCodigo, edTextNome, edTextCategoria, edTextDescricao,
            edTextValor, edTextData, edTextForma;
    Button btnSalvar, btnVoltar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Despesa despesa, despesaSelecionada;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_despesa);

        animationView = findViewById(R.id.id_image_view_animacao);
        animationDrawable = (AnimationDrawable) animationView.getDrawable();

        edTextCodigo = findViewById(R.id.id_edit_text_codigo_despesa);
        edTextNome = findViewById(R.id.id_edit_text_nome_despesa);
        edTextCategoria = findViewById(R.id.id_edit_text_categoria_despesa);
        edTextDescricao = findViewById(R.id.id_edit_text_descricao_despesa);
        edTextValor = findViewById(R.id.id_edit_text_valor_despesa);
        edTextData = findViewById(R.id.id_edit_text_data_despesa);
        edTextForma = findViewById(R.id.id_edit_text_forma_pagamento_despesa);

        btnVoltar = findViewById(R.id.id_btn_voltar_cd);
        btnSalvar = findViewById(R.id.id_btn_salvar_cd);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("despesa");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTextNome.getText().toString().equals("") ||
                        edTextCategoria.getText().toString().equals("") ||
                        edTextDescricao.getText().toString().equals("") ||
                        edTextValor.getText().toString().equals("") ||
                        edTextData.getText().toString().equals("") ||
                        edTextForma.getText().toString().equals("")){
                    Toast.makeText(CadastrarDespesaActivity.this, "Insira os dados", Toast.LENGTH_SHORT).show();
                    return;
                }
                salvar();
            }
        });

        it = CadastrarDespesaActivity.this.getIntent();
        if(it != null){
            despesaSelecionada = (Despesa) it.getSerializableExtra("despesa");
            if(despesaSelecionada != null){
                edTextCodigo.setText(
                        String.format("%d",despesaSelecionada.getCodigo_despesa())
                );
                edTextNome.setText(despesaSelecionada.getNome_despesa());
                edTextCategoria.setText(despesaSelecionada.getCategoria_despesa());
                edTextDescricao.setText(despesaSelecionada.getDescricao_despesa());
                edTextValor.setText(
                        String.format("%f",despesaSelecionada.getValor_despesa())
                );
                edTextData.setText(despesaSelecionada.getData_despesa());
                edTextForma.setText(despesaSelecionada.getFormaDePagamento_despesa());
                edTextCodigo.setEnabled(false);
                edTextNome.requestFocus();
            }else{
                edTextCodigo.setEnabled(true);
            }
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        animationDrawable.start();
    }

    private void salvar() {
        Integer codigo = Integer.valueOf(this.edTextCodigo.getText().toString());
        String nome = this.edTextNome.getText().toString();
        String categoria = this.edTextCategoria.getText().toString();
        String descricao = this.edTextDescricao.getText().toString();
        Double valor = Double.valueOf(this.edTextValor.getText().toString());
        String data = this.edTextData.getText().toString();
        String formaPagamento = this.edTextForma.getText().toString();

        despesa = new Despesa(codigo,nome,categoria,descricao,valor,data,formaPagamento);
        databaseReference
                .child(String.format("%d",despesa.getCodigo_despesa().intValue())
                ).setValue(despesa);
        limparTela();
        Toast.makeText(CadastrarDespesaActivity.this, "Despesa persistida no Banco de Dados.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void limparTela() {
        edTextCodigo.setText(null);
        edTextNome.setText(null);
        edTextCategoria.setText(null);
        edTextDescricao.setText(null);
        edTextValor.setText(null);
        edTextData.setText(null);
        edTextForma.setText(null);
        edTextCodigo.requestFocus();
    }
}