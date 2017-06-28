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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.filipe.viagem.R;
import br.com.filipe.viagem.dao.IDAO;
import br.com.filipe.viagem.dao.ViagemDAO;
import br.com.filipe.viagem.entity.Viagem;

/**
 * Created by filipe on 10/05/17.
 */

public class ViagemListActivity extends ListActivity {

    private List<Map<String, Object>> viagens;
    private AlertDialog menu;
    private AlertDialog caixaConfirmacao;
    private int viagemSelecionada;
    private IDAO<Viagem> dao;

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {
                    viagemSelecionada = position;
                    menu.show();
                }
            };

    private DialogInterface.OnClickListener listenerMenu =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface,
                        int item) {
                    Integer idViagem = (Integer) viagens.get(viagemSelecionada).
                            get("_id");
                    Intent intent;
                    switch (item){
                        case 0:
                            intent = new Intent(getApplicationContext(),
                                    NovaViagemActivity.class);
                            intent.putExtra(NovaViagemActivity.EXTRA_ID_VIAGEM, idViagem);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(),
                                    NovoGastoActivity.class);
                            intent.putExtra(NovoGastoActivity.EXTRA_ID_VIAGEM, idViagem);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(),
                                    GastoListActivity.class);
                            intent.putExtra(GastoListActivity.EXTRA_ID_VIAGEM, idViagem);
                            startActivity(intent);
                            startActivity(
                                    new Intent(getApplicationContext(),
                                            GastoListActivity.class));
                            break;
                        case 3:
                            caixaConfirmacao.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = dao.excluir(idViagem);
                            if(result) {
                                viagens.remove(viagemSelecionada);
                                Toast.makeText(getApplicationContext(), getString(R.string.excluido_sucesso), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), getString(R.string.erro_excluir), Toast.LENGTH_LONG).show();
                            }
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

        dao = new ViagemDAO(getApplicationContext());
        ListView listView = getListView();

        viagens = listarViagens();

        // trocar qtdPessoa por valor total da viagem
        String[] de = {"imagem", "destino", "data_saida", "qtdPessoa" };
        int[] para = {R.id.idtipoviagem, R.id.iddestino,
            R.id.iddata, R.id.idvalor};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                viagens,
                R.layout.layout_lista_viagem,
                de,
                para);

        setListAdapter(adapter);
        listView.setOnItemClickListener(listener);

        this.menu = criarAlertDialog();
        this.caixaConfirmacao = criarConfirmacaoDialog();
    }

    private List<Map<String, Object>> listarViagens(){

        List<Map<String, Object>> viagens
                = new ArrayList<Map<String, Object>>();
        List<Viagem> listaViagem = dao.listar();

        for(Viagem v: listaViagem) {
            Map<String, Object> item =
                    new HashMap<String, Object>();

            item.put("_id", v.getId());
            item.put("destino", v.getDestino());
            item.put("tipo_viagem", v.getTipoViagem());
            item.put("data_saida", v.getDataSaida());
            item.put("data_chegada", v.getDataChegada());
            item.put("orcamento", v.getOrcamento());
            item.put("qtdPessoa", v.getQtdPessoa());
            item.put("imagem", R.drawable.lazer);

            viagens.add(item);
        }

        return viagens;
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
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
}
