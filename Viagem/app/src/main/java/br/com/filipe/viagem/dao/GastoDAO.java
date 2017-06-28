package br.com.filipe.viagem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.filipe.viagem.entity.Gasto;
import br.com.filipe.viagem.util.DatabaseHelper;

/**
 * Created by filipe on 14/06/17.
 */

public class GastoDAO implements IGastoDAO {

    private SQLiteOpenHelper db;

    public static final String NOME_TABELA = "gasto";

    public GastoDAO(Context context){
        db = new DatabaseHelper(context);
    }

    public static String criarTabela(){
        return "CREATE TABLE gasto (" +
                "_id INTEGER PRIMARY KEY,"+
                "categoria TEXT, " +
                "valor DOUBLE, " +
                "data TEXT, " +
                "descricao TEXT, " +
                "local TEXT, " +
                "fk_viagem INTEGER, " +
                "FOREIGN KEY(fk_viagem) REFERENCES viagem(_id));";
    }

    @Override
    public boolean salvar(Gasto g) {
        ContentValues values = new ContentValues();
        values.put("categoria", g.getCategoria());
        values.put("valor", g.getValor());
        values.put("data", g.getData());
        values.put("descricao", g.getDescricao());
        values.put("local", g.getLocal());
        values.put("fk_viagem", g.getFkViagem());

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
    public boolean atualizar(Gasto g) {
        ContentValues values = new ContentValues();
        values.put("_id", g.getId());
        values.put("categoria", g.getCategoria());
        values.put("valor", g.getValor());
        values.put("data", g.getData());
        values.put("descricao", g.getDescricao());
        values.put("local", g.getLocal());
        values.put("fk_viagem", g.getFkViagem());

        String id = String.valueOf(g.getId());

        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado =
                banco.update(NOME_TABELA, values, "_id = ?",
                        new String[]{id});
        return resultado > 0;
    }

    @Override
    public List<Gasto> listar() {
        List<Gasto> retorno = new ArrayList<Gasto>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT _id, categoria, valor, data, descricao, local, fk_viagem FROM "+ NOME_TABELA;

        Cursor cursor = banco.rawQuery(sql, null);

        //cursor.moveToFirst();

        while(cursor.moveToNext()){
            Gasto g = new Gasto();
            g.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            g.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
            g.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            g.setData(cursor.getString(cursor.getColumnIndex("data")));
            g.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            g.setLocal(cursor.getString(cursor.getColumnIndex("local")));
            g.setFkViagem(cursor.getInt(cursor.getColumnIndex("fk_viagem")));
            retorno.add(g);
        }
        cursor.close();

        return retorno;
    }

    @Override
    public List<Gasto> listar(Integer fkViagem) {
        List<Gasto> retorno = new ArrayList<Gasto>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT _id, categoria, valor, data, descricao, local, fk_viagem FROM "+ NOME_TABELA+ " WHERE fk_viagem = ?";

        String idString = String.valueOf(fkViagem);
        Cursor cursor = banco.rawQuery(sql, new String[]{idString});

        //cursor.moveToFirst();

        while(cursor.moveToNext()){
            Gasto g = new Gasto();
            g.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            g.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
            g.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            g.setData(cursor.getString(cursor.getColumnIndex("data")));
            g.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            g.setLocal(cursor.getString(cursor.getColumnIndex("local")));
            g.setFkViagem(cursor.getInt(cursor.getColumnIndex("fk_viagem")));
            retorno.add(g);
        }
        cursor.close();

        return retorno;
    }

    @Override
    public Gasto buscarPorId(Integer id) {
        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT _id, categoria, valor, data, descricao, local, fk_viagem FROM "+ NOME_TABELA + " WHERE _id = ?";

        String idString = String.valueOf(id);

        Cursor cursor = banco.rawQuery(sql, new String[]{idString});

        if(cursor.moveToNext()){
            Gasto g = new Gasto();
            g.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            g.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
            g.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            g.setData(cursor.getString(cursor.getColumnIndex("data")));
            g.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            g.setLocal(cursor.getString(cursor.getColumnIndex("local")));
            g.setFkViagem(cursor.getInt(cursor.getColumnIndex("fk_viagem")));
            cursor.close();
            return g;
        }

        cursor.close();
        return null;
    }
}