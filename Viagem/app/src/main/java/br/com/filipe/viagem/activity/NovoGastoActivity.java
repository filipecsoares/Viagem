package br.com.filipe.viagem.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

import br.com.filipe.viagem.R;

/**
 * Created by filipe on 04/05/17.
 */

public class NovoGastoActivity extends Activity {

    private int ano, mes, dia;
    private Button dataGasto;
    private Spinner categoria;
    private DatePickerDialog.OnDateSetListener listener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataGasto.setText(dia+"/"+(mes+1)+"/"+ano);
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_novogasto);

        Calendar cal = Calendar.getInstance();
        ano = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);

        dataGasto = (Button) findViewById(R.id.iddata);
        dataGasto.setText(dia+"/"+(mes+1)+"/"+ano);

        categoria = (Spinner) findViewById(R.id.idcategoria);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.categoria_gasto,
                        android.R.layout.simple_spinner_item);
        categoria.setAdapter(adapter);
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.iddata == id){
            return new DatePickerDialog(this, listener,
                    ano, mes, dia);
        }
        return null;
    }
}