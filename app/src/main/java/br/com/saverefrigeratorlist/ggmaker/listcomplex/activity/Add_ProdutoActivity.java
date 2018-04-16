package br.com.saverefrigeratorlist.ggmaker.listcomplex.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devfigas.permission.MarshmallowActivity;
import com.devfigas.permission.MarshmallowPermission;

import br.com.saverefrigeratorlist.ggmaker.listcomplex.R;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.dao.ProdutoDao;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.model.Produto;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.util.Validator;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;

public class Add_ProdutoActivity extends MarshmallowActivity {
    public static final int REQUEST_CODE = 0;
    final int CODE_SCAN =1;

    private EditText nome, cod, preco;
    private TextView compra, vencimento;
    private Activity activity ;
    private ProdutoDao produtoDAO = new ProdutoDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__produto);
        Log.i("APP","onCreate Add_ProdutoActivity ");

        this.nome = (EditText) findViewById(R.id.edt_nome);
        this.cod = (EditText) findViewById(R.id.edt_codigo);
        this.compra =  (TextView) findViewById(R.id.edt_data_inc);
        this.vencimento = (TextView) findViewById(R.id.edt_data_venc);
        this.preco = (EditText) findViewById(R.id.edt_preco);

        this.compra.setText(Validator.DataAtual());

        activity = this;
    }

//    public void actionAdd(View v) {
//        Log.i("APP", "action add");
//        String nome = Add_ProdutoActivity.this.nome.getText().toString();
//        String cod = Add_ProdutoActivity.this.cod.getText().toString();
//        String preco = Add_ProdutoActivity.this.preco.getText().toString();
//        String compra = Add_ProdutoActivity.this.compra.getText().toString();
//        String vencimento = Add_ProdutoActivity.this.vencimento.getText().toString();
//
//        if ( Validator.validateNotNull(this.cod,"Preencha o campo Codigo")&&
//                Validator.validateNotNull(this.nome,"Preencha o campo nome")&&
//                    Validator.validateNotNull(this.preco, "Preencha o campo Preço")){
//            Intent it = new Intent();
//
//            it.putExtra("COD", cod);
//            it.putExtra("NOME", nome);
//            it.putExtra("PRECO", preco);
//            if (Validator.isDataValida(this.compra,compra,"Data de compra de produto Inválida") &&
//                    Validator.isDataValida(this.vencimento,vencimento, "Data de vencimento de produto Inválida")&&
//                        Validator.isDataCompraAndVencimento(this.compra, Validator.convertStringForDate(compra) ,Validator.convertStringForDate(vencimento)
//                                ,"Data de Compra inferior ao Vencimento")){
//                it.putExtra("COMP", compra);
//                it.putExtra("VENC", vencimento);
//                setResult(RESULT_OK, it);
//                finish();
//            }else {
//                Toast.makeText(Add_ProdutoActivity.this, "Erro na Data", Toast.LENGTH_SHORT).show();
//                Log.i("APP", "action add INFORME A DATA");
//            }
//
//        }
//
//    }

    public void actionAdd(View v) {
        Log.i("APP", "action add");
        String nome = Add_ProdutoActivity.this.nome.getText().toString();
        String cod = Add_ProdutoActivity.this.cod.getText().toString();
        String preco = Add_ProdutoActivity.this.preco.getText().toString();
        String compra = Add_ProdutoActivity.this.compra.getText().toString();
        String vencimento = Add_ProdutoActivity.this.vencimento.getText().toString();

        if ( Validator.validateNotNull(this.cod,"Preencha o campo Codigo")&&
                Validator.validateNotNull(this.nome,"Preencha o campo nome")&&
                Validator.validateNotNull(this.preco, "Preencha o campo Preço")){
           if (Validator.isDataValida(this.compra,compra,"Data de compra de produto Inválida") &&
                Validator.isDataValida(this.vencimento,vencimento, "Data de vencimento de produto Inválida")){
                Date dcompra = Validator.convertStringForDate(compra);
                Date dvencimento = Validator.convertStringForDate(vencimento);
                if (Validator.isDataCompraAndVencimento(this.compra,dcompra,dvencimento
                        ,"Data de Compra inferior ao Vencimento")){
                    Double dpreco = Validator.convertStringForDouble(preco);
                    Produto produto = new Produto(cod,nome,dpreco,dcompra,dvencimento);
                    Intent it = new Intent(Add_ProdutoActivity.this, MainActivity.class);
                    it.putExtra("PRODUTO", produto.getNome());
                    produtoDAO.inserir(produto);
                    setResult(RESULT_OK, it);
                    finish();
                }else{
                    Toast.makeText(this,"Data de Compra inferior ao Vencimento",Toast.LENGTH_SHORT).show();
                }
           }else {
                Toast.makeText(Add_ProdutoActivity.this, "Erro na Data", Toast.LENGTH_SHORT).show();
                Log.i("APP", "action add INFORME A DATA");
            }

        }

    }


    public void actionAddDataVencimento(View v) {
        Log.i("APP", "Escolher a DATA de Vencimento");

        AlertDialog.Builder builder = new AlertDialog.Builder(Add_ProdutoActivity.this);
        builder.setTitle("DATA");
        final DatePicker picker = new DatePicker(Add_ProdutoActivity.this);
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
                Add_ProdutoActivity.this.vencimento.setText(sData);
            }
        });
        builder.create().show();
    }

    public void Scan(View view) {
        setCallbackPermission(new MarshmallowPermission (this, Manifest.permission.CAMERA, CODE_SCAN,
                false, "Importante para Funcionamento do Leitor de código de Barras") {
            @Override
            public void granted(String permission) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan de Código de Barras");
                integrator.setCameraId(0);
                integrator.initiateScan();

            }

            @Override
            public void denied(String permission) {
                super.denied(permission);
                Log.i("APP", "Permissão negada pelo Usuario");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null){
                cod.setText(data.getStringExtra("SCAN_RESULT"));
            }else {
                alert("Cancelado");
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("APP","onResume Add_ProdutoActivity " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("APP","onRestart Add_ProdutoActivity " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("APP","onDestroy Add_ProdutoActivity " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("APP","onPause Add_ProdutoActivity " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("APP","onStop Add_ProdutoActivity " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("APP","onStart Add_ProdutoActivity " );
    }
}