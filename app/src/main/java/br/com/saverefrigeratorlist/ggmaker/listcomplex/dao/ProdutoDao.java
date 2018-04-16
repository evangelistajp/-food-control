package br.com.saverefrigeratorlist.ggmaker.listcomplex.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.saverefrigeratorlist.ggmaker.listcomplex.model.Produto;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.util.Validator;

/**
 * Created by evang on 04/01/2018.
 */

public class ProdutoDao implements DAO<Produto>  {

    private BancoHelper banco;
    private static final String TABELA = "produto";
    private Date dataCompra , dataVencimento;

    public ProdutoDao(Context context) {
        this.banco =  new BancoHelper(context);
    }

    @Override
    public void inserir(Produto novo) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", novo.getCodigo());
        cv.put("nome", novo.getNome());
        cv.put("preco", novo.getPreco());
        cv.put("data_compra", String.valueOf(novo.getData_compra()));
        cv.put("data_vencimento", String.valueOf(novo.getData_validade()));

        Log.i("APP" , "ProdutoDAO Inserir Produto "+cv.toString());

        Log.i("APP", "ProdutoDAO: " +novo.toString());
        this.banco.getWritableDatabase().insert(TABELA,null,cv);

    }

    @Override
    public void atualizar(Produto obj) {
        ContentValues cv = new ContentValues();

        cv.put("codigo", obj.getCodigo());
        cv.put("nome", obj.getNome());
        cv.put("preco", obj.getPreco());
        cv.put("data_compra", String.valueOf(obj.getData_compra()));
        cv.put("data_vencimento", String.valueOf(obj.getData_validade()));
        Log.i("APP" , "ProdutoDAO Atualizar produto "+cv.toString());
        Log.i("APP" , "ProdutoDAO Atualizar produto id da clasula where "+obj.getId());
        this.banco.getWritableDatabase().update(TABELA, cv, "_id = ?",new String[]{""+obj.getId()});

    }

    @Override
    public void remover(int id) {
        String[] where = {Integer.toString(id)};
        this.banco.getWritableDatabase().delete(TABELA, "_id = ?", where);
    }

    @Override
    public void remover(Produto obj) {
        this.remover(obj.getId());
    }

    @Override
    public Produto get(int id) {
        Log.i("APP", "PRODUTODAO GET(ID) ");
        String[] where = {Integer.toString(id)};
        String[] colunas = {"_id", "codigo","nome","preco","data_compra","data_vencimento" };


        Cursor c = this.banco.getReadableDatabase().query(TABELA,colunas,"_id = ?", where,null, null, null, null);

        if(c!=null){
            c.moveToFirst();
            if (c.getCount() > 0) {
                Produto e = new Produto();

                e.setId(c.getInt(c.getColumnIndex(colunas[0])));
                e.setCodigo(c.getString(1));
                e.setNome(c.getString(2));
                e.setPreco(Double.parseDouble(c.getString(3)));
                String sDataCompra = c.getString((c.getColumnIndex("data_compra")));
                String sDataVencimento = c.getString((c.getColumnIndex("data_vencimento")));
                Log.i("APP", "PRODUTODAO GET(ID) DATA STRING " + sDataCompra);
                Log.i("APP", "PRODUTODAO GET(ID) DATA STRING " + sDataVencimento);
                dataCompra = Validator.convertStringForDate(sDataCompra);
                dataVencimento= Validator.convertStringForDate(sDataVencimento);
                Log.i("APP", "PRODUTODAO GET(ID) DATA DATE COMPRA " + dataCompra);
                Log.i("APP", "PRODUTODAO GET(ID) DATA DATE VENCIMENTO " + dataVencimento);
                e.setData_compra(dataCompra);
                e.setData_validade(dataVencimento);

                Log.i("APP", "ProdutoDAO GET(ID)  produto "+ e);
                // finaliza o SQLiteDatabase
                this.banco.close();
                return e;
            } else {
                // caso não retornar nenhum usuario do cursor, o retorno da função será nula
                Log.i("APP", "ProdutoDAO GET(ID)  Nenhum produto encontrado!");
                return null;
            }
        }
        // finaliza o SQLiteDatabase
        this.banco.close();
        return null;
    }

    @Override
    public List<Produto> get() {

        String[] colunas = {"_id", "codigo","nome","preco","data_compra","data_vencimento"};
        List<Produto> lista = new ArrayList<Produto>();

        Cursor c = this.banco.getReadableDatabase().query(TABELA, colunas, null, null, null, null, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            do{

                Produto e = new Produto();
                e.setId(c.getInt(c.getColumnIndex(colunas[0])));
                e.setCodigo(c.getString(1));
                e.setNome(c.getString(2));
                e.setPreco(Double.parseDouble(c.getString(3)));
                String sDataCompra = c.getString((c.getColumnIndex("data_compra")));
                String sDataVencimento = c.getString((c.getColumnIndex("data_vencimento")));
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataVencimento);
                dataCompra = Validator.convertStringForDate(sDataCompra);
                dataVencimento= Validator.convertStringForDate(sDataVencimento);
                Log.i("APP", "PRODUTODAO GET() DATA DATE COMPRA " + dataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA DATE VENCIMENTO " + dataVencimento);
                e.setData_compra(dataCompra);
                e.setData_validade(dataVencimento);


                lista.add(e);
                Log.i("APP", "ProdutoDAO GET()  produto "+ e);
            }while (c.moveToNext());
        }
        Log.i("APP", "ProdutoDAO  retorna a tamanho da lista de Produto " + lista.size());
        // finaliza o SQLiteDatabase
        this.banco.close();
        return lista;
    }

    public List<Produto> buscaProdutoNomeLike(String nome) {
        String[] where = {nome};
        String[] colunas = {"_id", "codigo","nome","preco","data_compra","data_vencimento"};
        List<Produto> lista = new ArrayList<Produto>();

        Cursor c = this.banco.getReadableDatabase().query(TABELA, colunas, "nome like '%?%'", where, null, null, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            do{

                Produto e = new Produto();
                e.setId(c.getInt(c.getColumnIndex(colunas[0])));
                e.setCodigo(c.getString(1));
                e.setNome(c.getString(2));
                e.setPreco(Double.parseDouble(c.getString(3)));
                String sDataCompra = c.getString((c.getColumnIndex("data_compra")));
                String sDataVencimento = c.getString((c.getColumnIndex("data_vencimento")));
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataVencimento);
                dataCompra = Validator.convertStringForDate(sDataCompra);
                dataVencimento= Validator.convertStringForDate(sDataVencimento);
                Log.i("APP", "PRODUTODAO GET() DATA DATE COMPRA " + dataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA DATE VENCIMENTO " + dataVencimento);
                e.setData_compra(dataCompra);
                e.setData_validade(dataVencimento);


                lista.add(e);
                Log.i("APP", "ProdutoDAO GET()  produto "+ e);
            }while (c.moveToNext());
        }
        Log.i("APP", "ProdutoDAO  retorna a tamanho da lista de Produto " + lista.size());
        // finaliza o SQLiteDatabase
        this.banco.close();

        return  lista;
    }

    public List<Produto> buscaProdutoCodigoLike(String codigo) {
        String[] where = {codigo};
        String[] colunas = {"_id", "codigo","nome","preco","data_compra","data_vencimento"};
        List<Produto> lista = new ArrayList<Produto>();

        Cursor c = this.banco.getReadableDatabase().query(TABELA, colunas, "codigo like '%?%'", where, null, null, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            do{

                Produto e = new Produto();
                e.setId(c.getInt(c.getColumnIndex(colunas[0])));
                e.setCodigo(c.getString(1));
                e.setNome(c.getString(2));
                e.setPreco(Double.parseDouble(c.getString(3)));
                String sDataCompra = c.getString((c.getColumnIndex("data_compra")));
                String sDataVencimento = c.getString((c.getColumnIndex("data_vencimento")));
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataVencimento);
                dataCompra = Validator.convertStringForDate(sDataCompra);
                dataVencimento= Validator.convertStringForDate(sDataVencimento);
                Log.i("APP", "PRODUTODAO GET() DATA DATE COMPRA " + dataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA DATE VENCIMENTO " + dataVencimento);
                e.setData_compra(dataCompra);
                e.setData_validade(dataVencimento);


                lista.add(e);
                Log.i("APP", "ProdutoDAO GET()  produto "+ e);
            }while (c.moveToNext());
        }
        Log.i("APP", "ProdutoDAO  retorna a tamanho da lista de Produto " + lista.size());
        // finaliza o SQLiteDatabase
        this.banco.close();

        return  lista;
    }

    public List<Produto> buscaProdutoCodigoAndNomeLike(String nome, String codigo) {
        String[] where = {nome, codigo};
        String[] colunas = {"_id", "codigo","nome","preco","data_compra","data_vencimento"};
        List<Produto> lista = new ArrayList<Produto>();

        Cursor c = this.banco.getReadableDatabase().query(TABELA, colunas, "nome like '%?%' OR  codigo like '%?%'" , where, null, null, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            do{

                Produto e = new Produto();
                e.setId(c.getInt(c.getColumnIndex(colunas[0])));
                e.setCodigo(c.getString(1));
                e.setNome(c.getString(2));
                e.setPreco(Double.parseDouble(c.getString(3)));
                String sDataCompra = c.getString((c.getColumnIndex("data_compra")));
                String sDataVencimento = c.getString((c.getColumnIndex("data_vencimento")));
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA STRING " + sDataVencimento);
                dataCompra = Validator.convertStringForDate(sDataCompra);
                dataVencimento= Validator.convertStringForDate(sDataVencimento);
                Log.i("APP", "PRODUTODAO GET() DATA DATE COMPRA " + dataCompra);
                Log.i("APP", "PRODUTODAO GET() DATA DATE VENCIMENTO " + dataVencimento);
                e.setData_compra(dataCompra);
                e.setData_validade(dataVencimento);


                lista.add(e);
                Log.i("APP", "ProdutoDAO GET()  produto "+ e);
            }while (c.moveToNext());
        }
        Log.i("APP", "ProdutoDAO  retorna a tamanho da lista de Produto " + lista.size());
        // finaliza o SQLiteDatabase
        this.banco.close();

        return  lista;
    }

}
