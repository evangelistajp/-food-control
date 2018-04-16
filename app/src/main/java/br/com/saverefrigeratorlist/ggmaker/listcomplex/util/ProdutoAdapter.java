package br.com.saverefrigeratorlist.ggmaker.listcomplex.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.saverefrigeratorlist.ggmaker.listcomplex.R;
import br.com.saverefrigeratorlist.ggmaker.listcomplex.model.Produto;

/**
 * Created by evang on 03/01/2018.
 */

public class ProdutoAdapter extends BaseAdapter {

    private List<Produto> lista = new ArrayList<Produto>();
    private Context context;

    public ProdutoAdapter(List<Produto> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Produto e = this.lista.get(position);
        //TODO verificar isso na primeira vez q o programa é executado
        if (convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.layout_produto_adapter, null);
            if (lista.size() == 0){
                Log.i("APP","ProdutoAdapter Tamanho da lista de estar ZERADA "+lista.size());
            }

        }else view = convertView;

        view.setMinimumHeight(150);


            Log.i("APP","ProdutoAdapter Tamanho da lista depois de Criar a convertView "+lista.size());
            TextView tvcod = (TextView) view.findViewById(R.id.tvCodigoProdutoCelula);
            TextView tvnome = (TextView) view.findViewById(R.id.tvNomeProdutoCelula);
            TextView tvdata = (TextView) view.findViewById(R.id.tvDataProdutoCelula);

            Log.i("APP", "ProdutoAdapter escrevendo no adapter " + e.getCodigo() +"  "+ e.getNome());
            if (e != null){
                tvnome.setText("Produto: " +e.getNome());
                tvcod.setText("Cod: " +e.getCodigo());
                Log.i("APP" , "ProdutoAdapter getData_validade " + e.getData_validade());
                if (e.getData_validade() != null){
                    String sData = null;
                    try {
                        sData = Validator.formataDateString(e.getData_validade());
                    } catch (Exception e1) {
                        Log.e("APP" , "ProdutoAdapter getData_validade ERRO " + e.getData_validade());
                        e1.printStackTrace();
                    }
                    Log.i("APP" , "ProdutoAdapter getData_validade " + e.getData_validade());
                    Log.i("APP" , "ProdutoAdapter sData " +sData);
                    tvdata.setText("DATA: " + sData);

                    if (Validator.verificavencimento(e.getData_validade())){
                        view.setBackgroundColor(Color.WHITE);
                        tvdata.setTextColor(Color.GREEN);
                    }else{
                        view.setBackgroundColor(Color.GRAY);
                        tvdata.setTextColor(Color.RED);
                    }
                }else{
                    tvdata.setText("DATA: NULL" );
                    view.setBackgroundColor(Color.RED);
                }

            }

        return view;
    }
}
