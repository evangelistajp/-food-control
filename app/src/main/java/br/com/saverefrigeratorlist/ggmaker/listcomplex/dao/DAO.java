package br.com.saverefrigeratorlist.ggmaker.listcomplex.dao;

import java.util.List;

/**
 * Created by evang on 04/01/2018.
 */

public interface DAO<T> {

    public void inserir(T novo);

    public void atualizar(T obj);

    public void remover(int id);

    public void remover(T obj);

    public T get(int id);

    public List<T> get();
}