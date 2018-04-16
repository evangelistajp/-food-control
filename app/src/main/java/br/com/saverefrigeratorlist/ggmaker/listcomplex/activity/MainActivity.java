package br.com.saverefrigeratorlist.ggmaker.listcomplex.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;


import br.com.saverefrigeratorlist.ggmaker.listcomplex.dao.ProdutoDao;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.util.ProdutoAdapter;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.R;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.model.Produto;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.util.Validator;

public class MainActivity extends AppCompatActivity {
    private static final int ADD = 1;
    private static final int SOBRE =2;
    private static final int UPGRADE = 3;


    private ListView listView;
    private ProdutoDao produtoDAO = new ProdutoDao(this);
    private Produto produto;
    private Intent it;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("APP","onCreate MainActivity Antes " + produtoDAO.get());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                it = clickBotaoAdd(view);

            }
        });

        this.listView = (ListView) findViewById(R.id.lv_lista_produtos);
        this.listView.setOnItemClickListener( new OnClickList());

        ProdutoAdapter adapter = new ProdutoAdapter(produtoDAO.get(),this);
        this.listView.setAdapter(adapter);
        this.atualizaAdapter();
        Log.i("APP","onCreate MainActivity Depois " +produtoDAO.get());
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, it);
//
//        Log.i("APP","MainActivity onActivityResult resultado voltou ");
//        if (resultCode == RESULT_OK){
//            if (requestCode == ADD){
//                String codigo = intent.getStringExtra("COD");
//                String nome = intent.getStringExtra("NOME");
//                String preco = intent.getStringExtra("PRECO");
//                String data_inc = intent.getStringExtra("COMP");
//                String data_venc = intent.getStringExtra("VENC");
//                //TODO verificar isso
//                Log.i("APP", "MainActivity onActivityResult  codigo do produto " + codigo);
//                Log.i("APP", "MainActivity onActivityResult  nome do produto " + nome);
//                Log.i("APP", "MainActivity onActivityResult  preco do produto " + preco);
//                Log.i("APP", "MainActivity onActivityResult  data de Compra " + data_inc);
//                Log.i("APP", "MainActivity onActivityResult  data de vencimento " + data_venc);
//
//
//                try {
//                    Double dpreco = Double.parseDouble(preco);
//                    if (dpreco == null){
//                        dpreco = 0.0;
//                        Log.i("APP", "MainActivity onActivityResult  preco do produto NULL " + preco);
//                    }
//                    Date date_in = (Date) Validator.formataStringDate(data_inc);
//                    Date date_ven = (Date) Validator.formataStringDate(data_venc);
//
//
//
//                    produto = new Produto(codigo,nome,dpreco,date_in,date_ven);
//                } catch (ParseException e) {
//                    Log.e("APP", "MainActivity onActivityResult  ERRO na converção ");
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    Log.e("APP", "MainActivity onActivityResult  ERRO na converção ");
//                    e.printStackTrace();
//                }
//
//
//                Log.i("APP", "MainActivity onActivityResult Produto add :"+ produto.toString());
//                //lista.add(produto);
//			    this.produtoDAO.inserir(produto);
//                this.atualizaAdapter();
//
//                Toast.makeText(this, produto.getNome() +" adicionado com sucesso", Toast.LENGTH_SHORT).show();
//            }
//            if (requestCode == UPGRADE){
//
//
//            }
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, it);
        Log.i("APP","MainActivity onActivityResult resultado voltou ");
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD) {
                String produtoName = intent.getStringExtra("PRODUTO");

                Log.i("APP", "MainActivity onActivityResult Produto add :"+ produtoName);
                this.atualizaAdapter();

                Toast.makeText(this, produtoName +" adicionado com sucesso", Toast.LENGTH_LONG).show();
            }
        }

    }

    public Intent clickBotaoAdd(View view){
        Log.i("APP","MainActivity go ADDProdutoActivity" );
        Intent it = new Intent(this, Add_ProdutoActivity.class);
        startActivityForResult(it,ADD);
        return  it;

    }

    private void atualizaAdapter(){
        Log.i("APP","MainActivity atualizaAdapter() Atualiza lista " + produtoDAO.get());
        this.listView.setAdapter(new ProdutoAdapter(produtoDAO.get(), this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case ADD:
                Intent itAdd = new Intent(this, Add_ProdutoActivity.class);
                startActivityForResult(itAdd, ADD);

                break;
            case SOBRE:
                Intent itSobre = new Intent(this, Add_ProdutoActivity.class);
                startActivity(itSobre);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class OnClickList implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            MainActivity.this.produto = (Produto) parent.getAdapter().getItem(position);
            builder.setTitle(produto.getNome());
            try {
                builder.setMessage("Codígo: " + produto.getCodigo()+"\n"+
                        "Valor: " + produto.getPreco() +"\n"+
                        "Data de Compra: " + Validator.formataDateString(produto.getData_compra())+"\n"+
                        "Validade: " + Validator.formataDateString(produto.getData_validade()) +"\n" );
            } catch (Exception e) {

                e.printStackTrace();
            }

            builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "Produto Deletado!", Toast.LENGTH_SHORT).show();
                    MainActivity.this.produtoDAO.remover(MainActivity.this.produto);
                    ((BaseAdapter)MainActivity.this.listView.getAdapter()).notifyDataSetChanged();
                    MainActivity.this.atualizaAdapter();
                }
            });

            builder.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent it = new Intent(MainActivity.this, Upgrade_ProdutoActivity.class);
                    it.putExtra("PRODUTO", (Serializable) produto);
                    Log.i("APP","MainActivity Atualizar enviando o PRODUTO serializable " + produto.toString());
                    startActivity(it);
                }
            });
            builder.create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.atualizaAdapter();
        Log.i("APP","onResume MainActivity " +produtoDAO.get());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.atualizaAdapter();
        Log.i("APP","onRestart MainActivity " +produtoDAO.get());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("APP","onDestroy MainActivity " +produtoDAO.get());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("APP","onPause MainActivity " +produtoDAO.get());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("APP","onStop MainActivity " +produtoDAO.get());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("APP","onStart MainActivity " +produtoDAO.get());
    }
}
