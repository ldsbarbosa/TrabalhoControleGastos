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

public class CadastrarReceitaActivity extends AppCompatActivity {
    ImageView animationView;
    AnimationDrawable animationDrawable;
    EditText edTextCodigo, edTextNome, edTextCategoria, edTextDescricao,
            edTextValor, edTextData, edTextForma;
    Button btnSalvar, btnVoltar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Receita receita, receitaSelecionada;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_receita);

        animationView = findViewById(R.id.id_animacao_image_view);
        animationDrawable = (AnimationDrawable) animationView.getDrawable();

        edTextCodigo = findViewById(R.id.id_edit_text_codigo);
        edTextNome = findViewById(R.id.id_edit_text_nome);
        edTextCategoria = findViewById(R.id.id_edit_text_categoria);
        edTextDescricao = findViewById(R.id.id_edit_text_descricao);
        edTextValor = findViewById(R.id.id_edit_text_valor);
        edTextData = findViewById(R.id.id_edit_text_data);
        edTextForma = findViewById(R.id.id_edit_text_forma);
        btnSalvar = findViewById(R.id.id_btn_salvar);
        btnVoltar = findViewById(R.id.id_btn_voltar_cr);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("receita");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTextNome.getText().toString().equals("") ||
                        edTextCategoria.getText().toString().equals("") ||
                        edTextDescricao.getText().toString().equals("") ||
                        edTextValor.getText().toString().equals("") ||
                        edTextData.getText().toString().equals("") ||
                        edTextForma.getText().toString().equals("")){
                    Toast.makeText(CadastrarReceitaActivity.this, "Insira os dados", Toast.LENGTH_SHORT).show();
                    return;
                }
                salvar();
            }
        });

        it = CadastrarReceitaActivity.this.getIntent();
        if(it != null){
            receitaSelecionada = (Receita) it.getSerializableExtra("receita");
            if(receitaSelecionada != null){
                edTextCodigo.setText(
                        String.format("%d",receitaSelecionada.getCodigo())
                );
                edTextNome.setText(receitaSelecionada.getNome());
                edTextCategoria.setText(receitaSelecionada.getCategoria());
                edTextDescricao.setText(receitaSelecionada.getDescricao());
                edTextValor.setText(
                        String.format("%f",receitaSelecionada.getValor())
                );
                edTextData.setText(receitaSelecionada.getData());
                edTextForma.setText(receitaSelecionada.getFormaDeRecebimento());
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
        String formaRecebimento = this.edTextForma.getText().toString();

        receita = new Receita(codigo,nome,categoria,descricao,valor,data,formaRecebimento);
        databaseReference
                .child(String.format("%d",receita.getCodigo().intValue())
                ).setValue(receita);
        limparTela();
        Toast.makeText(CadastrarReceitaActivity.this, "Receita persistida no Banco de Dados.", Toast.LENGTH_SHORT).show();
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