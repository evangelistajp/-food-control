package br.com.saverefrigeratorlist.ggmaker.listcomplex.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.saverefrigeratorlist.ggmaker.listcomplex.R;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.dao.ProdutoDao;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.model.Produto;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.util.Validator;

public class Upgrade_ProdutoActivity extends AppCompatActivity {

    private EditText nome, cod, preco;
    private TextView compra, vencimento;
    private Produto produto = new Produto();
    private ProdutoDao produtoDAO = new ProdutoDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("APP","UPGRADE PRODUTO");
        setContentView(R.layout.activity_upgrade__produto);

        this.nome = (EditText) findViewById(R.id.edt_upgrade_nome);
        this.cod = (EditText) findViewById(R.id.edt_upgrade_codigo);
        this.compra =  (TextView) findViewById(R.id.edt_upgrade_data_inc);
        this.vencimento = (TextView) findViewById(R.id.edt_upgrade_data_venc);
        this.preco = (EditText) findViewById(R.id.edt_upgrade_preco);

        Intent intent = getIntent();


        this.produto = (Produto) intent.getSerializableExtra("PRODUTO");

        //TODO verificar isso
       Log.i("APP", "UPgrade_ProdutoActivity onActivityResult  PRODUTO " + produto.toString());

        this.nome.setText(produto.getNome());
        this.cod.setText(produto.getCodigo());
        this.preco.setText(produto.getPreco().toString());
        try {
            String sdata_inc_form = Validator.formataDateString(produto.getData_compra());
            String sdata_venc_form = Validator.formataDateString(produto.getData_validade());
            this.compra.setText(sdata_inc_form);
            this.vencimento.setText(sdata_venc_form);
        } catch (Exception e) {
            Log.e("APP", "ERRO UPGRADE na converção de datas " + produto.getData_compra() + " or" + produto.getData_validade());
            e.printStackTrace();
        }
    }

    public void actionAddDataVencimento(View v) {
        Log.i("APP", "Escolher a DATA de Vencimento");

        AlertDialog.Builder builder = new AlertDialog.Builder(Upgrade_ProdutoActivity.this);
        builder.setTitle("DATA");
        final DatePicker picker = new DatePicker(Upgrade_ProdutoActivity.this);
        picker.setCalendarViewShown(false);
        builder.setView(picker);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                int   day  = picker.getDayOfMonth();
                int   month= picker.getMonth() + 1;
                int   year = picker.getYear();
                String sData = day+"/"+month+"/"+year;
                Log.i("APP", "data escolhida " +sData);
                Upgrade_ProdutoActivity.this.vencimento.setText(sData);
            }
        });
        builder.create().show();
    }

    public void actionAddDataCompra(View v) {
        Log.i("APP", "Escolher a DATA de Vencimento");

        AlertDialog.Builder builder = new AlertDialog.Builder(Upgrade_ProdutoActivity.this);
        builder.setTitle("DATA");
        final DatePicker picker = new DatePicker(Upgrade_ProdutoActivity.this);
        picker.setCalendarViewShown(false);
        builder.setView(picker);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                int   day  = picker.getDayOfMonth();
                int   month= picker.getMonth() + 1;
                int   year = picker.getYear();
                String sData = day+"/"+month+"/"+year;
                Log.i("APP", "data escolhida " +sData);
                Upgrade_ProdutoActivity.this.compra.setText(sData);
            }
        });
        builder.create().show();
    }

    public void actionUpgrade(View v) {
        Log.i("APP", "UPgrade_ProdutoActivity action upgrade");

        if ( Validator.validateNotNull(this.nome,"Preencha o campo Nome") &&
                Validator.validateNotNull(this.cod,"Preencha o campo Codigo")&&
                    Validator.validateNotNull(this.preco, "Preencha o campo Preço")){

            produto.setNome(this.nome.getText().toString());
            produto.setCodigo(this.cod.getText().toString());
            produto.setPreco(Double.parseDouble(this.preco.getText().toString()));

            try {
                produto.setData_compra(Validator.formataStringDate(this.compra.getText().toString()));
                produto.setData_validade(Validator.formataStringDate(this.vencimento.getText().toString()));
            } catch (Exception e) {
                Log.e("APP", "UPgrade_ProdutoActivity ERRO na converção de DATAS " +this.compra.getText().toString() +" or " + this.vencimento.getText().toString());
                e.printStackTrace();
            }
            Log.i("APP", "UPgrade_ProdutoActivity action upgrade " + produto.toString());
            if (Validator.isDataCompraAndVencimento(this.compra,
                    produto.getData_compra(),produto.getData_validade(),
                       "Data de Compra inferior ao Vencimento")){

                produtoDAO.atualizar(produto);
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                finish();
            }else{
                Log.i("APP", "UPgrade_ProdutoActivity ERRO data de Compra acima do Vencimento");
                Toast.makeText(Upgrade_ProdutoActivity.this,"Data de Compra Prosterios ao vencimento", Toast.LENGTH_LONG).show();
            }

        }


    }
}
