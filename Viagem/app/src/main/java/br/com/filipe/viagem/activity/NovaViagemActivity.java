package br.com.filipe.viagem.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

import br.com.filipe.viagem.R;
import br.com.filipe.viagem.dao.IDAO;
import br.com.filipe.viagem.dao.ViagemDAO;
import br.com.filipe.viagem.entity.Viagem;

/**
 * Created by rafael on 03/05/17.
 */

public class NovaViagemActivity extends Activity {

    private EditText destino, orcamento, qtdpessoas;
    private RadioButton lazer, negocio;
    private int dia, mes, ano;
    private Button dataSaida, dataChegada;
    private IDAO<Viagem> dao;
    private int idViagem = 0;

    public static final String EXTRA_ID_VIAGEM = "id_viagem";

    private DatePickerDialog.OnDateSetListener listenerSaida =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataSaida.setText(dia+"/"+(mes+1)+"/"+ano);
                }
            };
    private DatePickerDialog.OnDateSetListener listenerChegada =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataChegada.setText(dia+"/"+(mes+1)+"/"+ano);
                }
            };

    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_novaviagem);

        Calendar cal = Calendar.getInstance();
        ano = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);

        dataSaida = (Button) findViewById(R.id.iddatasaida);
        dataSaida.setText(dia+"/"+(mes+1)+"/"+ano);
        dataChegada = (Button) findViewById(R.id.iddatachegada);
        dataChegada.setText(dia+"/"+(mes+1)+"/"+ano);

        destino = (EditText) findViewById(R.id.iddestino);
        orcamento = (EditText) findViewById(R.id.idorcamento);
        qtdpessoas = (EditText) findViewById(R.id.idqtdpessoas);

        lazer = (RadioButton) findViewById(R.id.idlazer);
        negocio = (RadioButton) findViewById(R.id.idnegocios);

        dao = new ViagemDAO(getApplicationContext());

        Intent intent = getIntent();
        idViagem = intent.getIntExtra(EXTRA_ID_VIAGEM, 0);

        if (idViagem > 0){
            //tela de edicao
            Viagem viagem = dao.buscarPorId(idViagem);
            destino.setText(viagem.getDestino());
            orcamento.setText(String.valueOf(viagem.getOrcamento()));
            qtdpessoas.setText(String.valueOf(viagem.getQtdPessoa()));

            if(viagem.getTipoViagem() == 1) {
                lazer.setSelected(true);
            }else{
                negocio.setSelected(true);
            }
        }
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.iddatasaida == id){
            return new DatePickerDialog(this, listenerSaida,
                    ano, mes, dia);
        }
        if(R.id.iddatachegada == id){
            return new DatePickerDialog(this, listenerChegada,
                    ano, mes, dia);
        }
        return null;
    }

    public void salvarViagem(View view) {
        Viagem v = new Viagem();
        v.setDestino(destino.getText().toString());
        v.setOrcamento(Double.parseDouble(orcamento.getText().toString()));
        v.setQtdPessoa(Integer.parseInt(qtdpessoas.getText().toString()));
        if(lazer.isSelected()){
            v.setTipoViagem(1);
        }else{
            v.setTipoViagem(0);
        }
        v.setDataSaida(dataSaida.getText().toString());
        v.setDataChegada(dataChegada.getText().toString());

        Boolean resultado = false;
        if(idViagem > 0){
            v.setId(idViagem);
            resultado = dao.atualizar(v);
            idViagem = 0;
        }else{
            resultado = dao.salvar(v);
        }

        if(resultado){
            Toast.makeText(this, getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).
                    show();
            destino.setText("");
            orcamento.setText("");
            qtdpessoas.setText("");
            lazer.setSelected(true);
        }
        else{
            Toast.makeText(this, getString(R.string.cadastro_erro), Toast.LENGTH_LONG).
                    show();
        }
    }
}
