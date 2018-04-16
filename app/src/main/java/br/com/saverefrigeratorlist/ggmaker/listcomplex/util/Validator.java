package br.com.saverefrigeratorlist.ggmaker.listcomplex.util;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by evang on 03/01/2018.
 */

public class Validator {


    public static boolean validateNotNull(View pView, String pMessage) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            // em qualquer outra condição é gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }

    public static boolean isDataValida(View pView, String date, String pMessage) {
        try {
            TextView txView = (TextView) pView;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            if (txView != null) {
                return true;
            } else {
                txView.setError("Por favor Insira a Data");
                txView.setFocusable(true);
                txView.requestFocus();
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isDataCompraAndVencimento(View pView, Date compra, Date vencimento, String pMessage) {
        TextView txView = (TextView) pView;

        if (compra.before(vencimento) || compra.equals(vencimento)) {
            return true;
        } else {
            txView.setError(pMessage);
            txView.setFocusable(true);
            txView.requestFocus();
            return  false;
        }
    }

    public static Date formataStringDate(String data) throws Exception {
        if (data == null || data.equals(""))
            return null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String formataDateString(Date data) throws Exception {
        if (data == null || data.equals(""))
            return null;
        String sdate = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        sdate = formatter.format(data);
        return sdate;
    }

    //Converte a Data que vem do Banco de dados no formato do sistema de String para DATE
    public static Date convertStringForDate(String sdata){
        Date data = null;
        try {
            sdata = new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH)
                            .parse(sdata));
            Log.i("APP", "Validator GET() SDATA depois da converção " + sdata);
        } catch (ParseException e) {
            Log.e("APP","Validator GET() ERRO NA CONVERSAO DE SDATA EEE MMM d HH:mm:ss zzz yyyy para String dd/MM/yyyy " +sdata + " para " + data);
            e.printStackTrace();
        }
        try {
            //TODO erro na hora de converter a data
            data = formataStringDate(sdata);
        } catch (ParseException e1) {
            Log.e("APP","Validator GET() ERRO NA CONVERSAO DE DATA " +sdata + " para " + data);
            e1.printStackTrace();
        } catch (Exception e) {
            Log.e("APP","Validator GET() ERRO NA CONVERSAO DE DATA " +sdata + " para " + data);
            e.printStackTrace();
        }
        return data;
    }

    //Pega a data Atual do sistema e converte para String
    public static String DataAtual(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.format( new Date( System.currentTimeMillis() ) );

        return  sdf.format( new Date( System.currentTimeMillis() ) );

    }


    public static String convertStringForString(String sdata) throws Exception {
        String sdataformatada;
        Date data = convertStringForDate(sdata);
        return  sdataformatada = formataDateString(data);
    }


    public static boolean verificavencimento(Date vencimento){
        boolean data;
        Calendar c = Calendar.getInstance();
        Date dataAtual = c.getTime();

        if (dataAtual.before(vencimento)){
            data = true;
        }
        else if (dataAtual.after(vencimento))
            data = false;
        else
            data = true;
        return data;
    }

    public static Double convertStringForDouble(String preco){

        Double dpreco = Double.parseDouble(preco);
        if (dpreco == null){
            Log.i("APP", "MainActivity onActivityResult  preco do produto NULL " + preco);
            return dpreco = 0.0;
        }
        return dpreco;
    }

}
