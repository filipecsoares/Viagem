package br.com.filipe.viagem.dao;

import java.util.List;

/**
 * Created by filipe on 14/06/17.
 */

public interface IDAO<T> {

    boolean salvar(T p);

    boolean excluir(Integer id);

    boolean atualizar(T p);

    List<T> listar();

    T buscarPorId(Integer id);
}
