package br.com.filipe.viagem.dao;

import java.util.List;

import br.com.filipe.viagem.entity.Gasto;

/**
 * Created by filipe on 14/06/17.
 */

public interface IGastoDAO extends IDAO<Gasto>{

    List<Gasto> listar(Integer fkViagem);

}
