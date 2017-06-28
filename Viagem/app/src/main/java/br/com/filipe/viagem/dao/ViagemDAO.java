package br.com.filipe.viagem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.filipe.viagem.entity.Viagem;
import br.com.filipe.viagem.util.DatabaseHelper;

/**
 * Created by filipe on 14/06/17.
 */

public class ViagemDAO implements IDAO<Viagem> {

    private SQLiteOpenHelper db;

    public static final String NOME_TABELA = "viagem";

    public ViagemDAO(Context context){
        db = new DatabaseHelper(context);
    }

    public static String criarTabela(){
        return "CREATE TABLE viagem (" +
                "_id INTEGER PRIMARY KEY,"+
                "destino TEXT, " +
                "tipo_viagem INTEGER, " +
                "data_saida TEXT, " +
                "data_chegada TEXT, " +
                "orcamento DOUBLE, " +
                "qtdPessoa INTEGER);";
    }

    @Override
    public boolean salvar(Viagem v) {
        ContentValues values = new ContentValues();
        values.put("destino", v.getDestino());
        values.put("tipo_viagem", v.getTipoViagem());
        values.put("data_saida", v.getDataSaida());
        values.put("data_chegada", v.getDataChegada());
        values.put("orcamento", v.getOrcamento());
        values.put("qtdPessoa", v.getQtdPessoa());

        SQLiteDatabase banco = db.getWritableDatabase();
        long resultado = banco.insert(NOME_TABELA, null, values);
        return resultado > 0;
    }

    @Override
    public boolean excluir(Integer id) {
        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado =
                banco.delete(NOME_TABELA, "_id = ?",new String[]
                    {id.toString()});
        return resultado > 0;
    }

    @Override
    public boolean atualizar(Viagem v) {
        ContentValues values = new ContentValues();
        values.put("_id", v.getId());
        values.put("destino", v.getDestino());
        values.put("tipo_viagem", v.getTipoViagem());
        values.put("data_saida", v.getDataSaida());
        values.put("data_chegada", v.getDataChegada());
        values.put("orcamento", v.getOrcamento());
        values.put("qtdPessoa", v.getQtdPessoa());

        String id = String.valueOf(v.getId());

        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado =
                banco.update(NOME_TABELA, values, "_id = ?",
                        new String[]{id});
        return resultado > 0;
    }

    @Override
    public List<Viagem> listar() {
        List<Viagem> retorno = new ArrayList<Viagem>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT _id, destino, tipo_viagem, data_saida, data_chegada, orcamento, qtdPessoa FROM "+ NOME_TABELA;

        Cursor cursor = banco.rawQuery(sql, null);

        //cursor.moveToFirst();

        while(cursor.moveToNext()){
            Viagem v = new Viagem();
            v.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            v.setDestino(cursor.getString(cursor.getColumnIndex("destino")));
            v.setTipoViagem(cursor.getInt(cursor.getColumnIndex("tipo_viagem")));
            v.setDataSaida(cursor.getString(cursor.getColumnIndex("data_saida")));
            v.setDataChegada(cursor.getString(cursor.getColumnIndex("data_chegada")));
            v.setOrcamento(cursor.getDouble(cursor.getColumnIndex("orcamento")));
            v.setQtdPessoa(cursor.getInt(cursor.getColumnIndex("qtdPessoa")));
            retorno.add(v);
        }
        cursor.close();

        return retorno;
    }

    @Override
    public Viagem buscarPorId(Integer id) {
        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT _id, destino, tipo_viagem, data_saida, data_chegada, orcamento, qtdPessoa FROM "+ NOME_TABELA + " WHERE _id = ?";

        String idString = String.valueOf(id);

        Cursor cursor = banco.rawQuery(sql, new String[]{idString});

        if(cursor.moveToNext()){
            Viagem v = new Viagem();
            v.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            v.setDestino(cursor.getString(cursor.getColumnIndex("destino")));
            v.setTipoViagem(cursor.getInt(cursor.getColumnIndex("tipo_viagem")));
            v.setDataSaida(cursor.getString(cursor.getColumnIndex("data_saida")));
            v.setDataChegada(cursor.getString(cursor.getColumnIndex("data_chegada")));
            v.setOrcamento(cursor.getDouble(cursor.getColumnIndex("orcamento")));
            v.setQtdPessoa(cursor.getInt(cursor.getColumnIndex("qtdPessoa")));
            cursor.close();
            return v;
        }

        cursor.close();
        return null;
    }
}