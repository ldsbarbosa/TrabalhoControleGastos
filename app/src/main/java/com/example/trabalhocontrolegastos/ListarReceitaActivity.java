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

public class ListarReceitaActivity extends AppCompatActivity {
    Button btnVoltar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayAdapter<Receita> adaptadorDaListaDeReceitas;
    ArrayList<Receita> listaDeReceitas;
    Receita receitaSelecionada, receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_receita);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("receita");
        listView = findViewById(R.id.id_list_view_lr);
        listaDeReceitas = new ArrayList<>();
        btnVoltar = findViewById(R.id.id_btn_voltar_lr);
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
                receitaSelecionada = listaDeReceitas.get(i);
            }
        });
    }

    private void carregarDados() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeReceitas.clear();
                for (DataSnapshot obj: snapshot.getChildren()){
                    receita = obj.getValue(Receita.class);
                    listaDeReceitas.add(receita);
                }
                adaptadorDaListaDeReceitas = new ArrayAdapter<>(ListarReceitaActivity.this,
                        android.R.layout.simple_list_item_1,listaDeReceitas);
                listView.setAdapter(adaptadorDaListaDeReceitas);
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
                it = new Intent(ListarReceitaActivity.this,
                        CadastrarReceitaActivity.class);
                it.putExtra("receita", receitaSelecionada);
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
        AlertDialog.Builder msgBox = new AlertDialog.Builder(ListarReceitaActivity.this);
        msgBox.setTitle("Confirma Exclusão?");
        msgBox.setIcon(R.drawable.ic_excluir);
        msgBox.setMessage("Tem certeza que deseja excluir o item "+receitaSelecionada.getNome()+"?");

        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListarReceitaActivity.this, "A receita não foi excluida", Toast.LENGTH_SHORT).show();
            }
        });

        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child(
                        String.format("%d",receitaSelecionada.getCodigo().intValue()
                        )).removeValue();
                Toast.makeText(ListarReceitaActivity.this, "A receita foi excluida", Toast.LENGTH_SHORT).show();
            }
        });

        msgBox.show();
    }
}