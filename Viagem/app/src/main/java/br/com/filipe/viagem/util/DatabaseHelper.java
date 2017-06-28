package br.com.filipe.viagem.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.filipe.viagem.dao.GastoDAO;
import br.com.filipe.viagem.dao.ViagemDAO;

/**
 * Created by filipe on 31/05/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "BancoViagem";
    private static int version = 5;


    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ViagemDAO.criarTabela());
        db.execSQL(GastoDAO.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int i, int i1) {

    }
}