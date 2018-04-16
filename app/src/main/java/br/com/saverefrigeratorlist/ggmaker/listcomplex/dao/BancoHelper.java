package br.com.saverefrigeratorlist.ggmaker.listcomplex.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by evang on 04/01/2018.
 */

public class BancoHelper extends SQLiteOpenHelper {

    private static final String BANCO = "produtos.db";
    private static final int VERSAO = 1;

    public BancoHelper(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table produto(" +
                "_id integer primary key autoincrement not null, " +
                " codigo string,"+
                " nome string," +
                " preco double,"+
                " data_compra datetime,"+
                " data_vencimento datetime"+
                ");";

        db.execSQL(sql);
        Log.i("APP", "Tabela produto criada.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table produto;");
        onCreate(db);

    }
}
