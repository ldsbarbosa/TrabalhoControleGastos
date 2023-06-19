package com.example.trabalhocontrolegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PrincipalActivity extends AppCompatActivity {
    double balancoAtual = 0,balancoReceita = 0,balancoDespesa = 0;
    TextView dateTimeDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    TextView txViewContadorReceita, txViewContadorDespesa, txViewBalanco, txViewData;
    Despesa despesa;
    ArrayList<Despesa> listaDeDespesas;
    Receita receita;
    ArrayList<Receita> listaDeReceitas;
    FirebaseDatabase database;
    DatabaseReference referenciaReceita, referenciaDespesa;
    // Criando atributos necessários (Widgets e Intent)
    Button btn_cadastrar_receita, btn_cadastrar_despesa, btn_listar_receita, btn_listar_despesa;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Instanciação dos Widgets
        btn_cadastrar_receita = findViewById(R.id.id_btn_cadastrar_receita);
        btn_cadastrar_despesa = findViewById(R.id.id_btn_cadastrar_despesa);
        btn_listar_receita = findViewById(R.id.id_btn_listar_receita);
        btn_listar_despesa = findViewById(R.id.id_btn_listar_despesa);
        txViewContadorReceita = findViewById(R.id.id_text_view_contador_receita);
        txViewContadorDespesa = findViewById(R.id.id_text_view_contador_despesa);
        txViewBalanco = findViewById(R.id.id_text_view_balanco_atual);

        // Exibindo data atual
        txViewData = findViewById(R.id.id_text_view_data_atual);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        txViewData.setText("Data: "+date);

        listaDeReceitas = new ArrayList<>();
        listaDeDespesas = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        referenciaReceita = database.getReference("receita");
        referenciaDespesa = database.getReference("despesa");

        // Eventos de clique
        btn_cadastrar_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(PrincipalActivity.this, CadastrarReceitaActivity.class);
                startActivity(it);
            }
        });
        btn_cadastrar_despesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(PrincipalActivity.this, CadastrarDespesaActivity.class);
                startActivity(it);
            }
        });
        btn_listar_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(PrincipalActivity.this, ListarReceitaActivity.class);
                startActivity(it);
            }
        });
        btn_listar_despesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(PrincipalActivity.this, ListarDespesaActivity.class);
                startActivity(it);
            }
        });
        carregarDados();
    }
    private void carregarDados() {
        referenciaReceita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeReceitas.clear();
                balancoReceita = 0;
                for (DataSnapshot obj: snapshot.getChildren()){
                    receita = obj.getValue(Receita.class);
                    balancoReceita += receita.getValor().doubleValue();
                    listaDeReceitas.add(receita);
                }
                txViewContadorReceita.setText(String.format("%d",listaDeReceitas.size()));
                balancoAtual = balancoReceita - balancoDespesa;
                txViewBalanco.setText("Balanço: "+String.format("%.2f",balancoAtual));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        referenciaDespesa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeDespesas.clear();
                balancoDespesa = 0;
                for (DataSnapshot obj: snapshot.getChildren()){
                    despesa = obj.getValue(Despesa.class);
                    balancoDespesa += despesa.getValor_despesa().doubleValue();
                    listaDeDespesas.add(despesa);
                }
                txViewContadorDespesa.setText(String.format("%d",listaDeDespesas.size()));
                balancoAtual = balancoReceita - balancoDespesa;
                txViewBalanco.setText("Balanço: "+String.format("%.2f",balancoAtual));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}