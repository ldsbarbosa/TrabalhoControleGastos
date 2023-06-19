package com.example.trabalhocontrolegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarDespesaActivity extends AppCompatActivity {

    Button btnVoltar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayAdapter<Despesa> adaptadorDaListaDeDespesas;
    ArrayList<Despesa> listaDeDespesas;
    Despesa despesaSelecionada, despesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesa);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("despesa");
        listView = findViewById(R.id.id_list_view_ld);
        listaDeDespesas = new ArrayList<>();
        btnVoltar = findViewById(R.id.id_btn_voltar_ld);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        carregarDados();
        configurarEvento();
    }

    private void configurarEvento() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                despesaSelecionada = listaDeDespesas.get(i);
            }
        });
    }

    private void carregarDados() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeDespesas.clear();
                for (DataSnapshot obj: snapshot.getChildren()){
                    despesa = obj.getValue(Despesa.class);
                    listaDeDespesas.add(despesa);
                }
                adaptadorDaListaDeDespesas = new ArrayAdapter<>(ListarDespesaActivity.this,
                        android.R.layout.simple_list_item_1,listaDeDespesas);
                listView.setAdapter(adaptadorDaListaDeDespesas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcao = item.getItemId();
        Intent it;
        switch(opcao){
            case R.id.id_item_editar:
                it = new Intent(ListarDespesaActivity.this,
                        CadastrarDespesaActivity.class);
                it.putExtra("despesa", despesaSelecionada);
                startActivity(it);
                break;
            case R.id.id_item_excluir:
                exibirDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exibirDialog() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(ListarDespesaActivity.this);
        msgBox.setTitle("Confirma Exclusão?");
        msgBox.setIcon(R.drawable.ic_excluir);
        msgBox.setMessage("Tem certeza que deseja excluir o item "+despesaSelecionada.getNome_despesa()+"?");

        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListarDespesaActivity.this, "A receita não foi excluida", Toast.LENGTH_SHORT).show();
            }
        });

        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child(
                        String.format("%d",despesaSelecionada.getCodigo_despesa().intValue()
                        )).removeValue();
                Toast.makeText(ListarDespesaActivity.this, "A receita foi excluida", Toast.LENGTH_SHORT).show();
            }
        });

        msgBox.show();
    }
}