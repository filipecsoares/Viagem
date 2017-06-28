package br.com.filipe.viagem.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.filipe.viagem.R;
import br.com.filipe.viagem.dao.GastoDAO;
import br.com.filipe.viagem.dao.IDAO;
import br.com.filipe.viagem.dao.IGastoDAO;
import br.com.filipe.viagem.entity.Gasto;

/**
 * Created by filipe on 10/05/17.
 */

public class GastoListActivity extends ListActivity {

    private List<Map<String, Object>> gastos;
    private AlertDialog menu;
    private AlertDialog caixaConfirmacao;
    private int gastoSelecionado;
    private IGastoDAO dao;
    private Integer fkViagem;

    private String dataAnterior = "";

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {
                    gastoSelecionado = position;
                    menu.show();
                }
            };

    public static final String EXTRA_ID_VIAGEM = "id_viagem";

    private DialogInterface.OnClickListener listenerMenu =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface,
                                    int item) {
                    switch (item){
                        case 0:
                            startActivity(
                                    new Intent(getApplicationContext(),
                                            NovoGastoActivity.class));
                            break;
                        case 1:
                            caixaConfirmacao.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            gastos.remove(gastoSelecionado);
                            getListView().invalidateViews();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            caixaConfirmacao.dismiss();
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_gasto);

        dao = new GastoDAO(getApplicationContext());
        ListView listView = getListView();

        Intent intent = getIntent();
        fkViagem = intent.getIntExtra(EXTRA_ID_VIAGEM, 0);

        gastos = listarGastos();
        String[] de = {"data", "descricao", "valor", "categoria"};
        int[] para = {R.id.iddata, R.id.iddescricao,
            R.id.idvalor, R.id.idcategoria};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                gastos,
                R.layout.layout_lista_gasto,
                de,
                para);

        adapter.setViewBinder(new GastoViewBinder());
        setListAdapter(adapter);
        listView.setOnItemClickListener(listener);

        this.menu = criarAlertDialog();
        this.caixaConfirmacao = criarConfirmacaoDialog();
    }

    private List<Map<String, Object>> listarGastos(){

        List<Map<String, Object>> gastos =
                new ArrayList<Map<String, Object>>();
        List<Gasto> listaGasto = dao.listar(fkViagem);

        for(Gasto g: listaGasto) {
            Map<String, Object> item =
                    new HashMap<String, Object>();

            item.put("_id", g.getId());
            item.put("categoria", g.getCategoria());
            item.put("valor", g.getValor());
            item.put("data", g.getData());
            item.put("descricao", g.getDescricao());
            item.put("local", g.getLocal());
            item.put("fk_viagem", g.getFkViagem());

            gastos.add(item);
        }

        return gastos;
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.remover)
        };
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, listenerMenu);

        return builder.create();
    }

    private AlertDialog criarConfirmacaoDialog(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao);
        builder.setPositiveButton(R.string.sim, listenerMenu);
        builder.setNegativeButton(R.string.nao, listenerMenu);
        return builder.create();
    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data,
                                    String texto) {

            if(view.getId() == R.id.iddata){
                if(!dataAnterior.equals(data)){
                    TextView textView = (TextView) view;
                    textView.setText(texto);
                    dataAnterior = texto;
                    view.setVisibility(View.VISIBLE);
                }
                else{
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if(view.getId() == R.id.idcategoria){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }

            return false;
        }
    }
}
