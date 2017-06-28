package br.com.filipe.viagem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.filipe.viagem.R;

/**
 * Created by rafael on 05/04/17.
 */

public class BoaViagemActivity extends Activity {

    private EditText idUsuario, idSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boaviagem);
        this.idUsuario = (EditText) findViewById(R.id.idusuario);
        this.idSenha = (EditText) findViewById(R.id.idsenha);
    }

    public void loginSistema(View view) {
        String usuarioInformado = this.idUsuario.getText().
                toString();
        String senhaInformada = this.idSenha.getText().toString();

        if(usuarioInformado.equals("usj")
                & senhaInformada.equals("123")){
            Intent intent = new Intent(this,
                    DashboardActivity.class);
            startActivity(intent);
        }
        else{
            Toast t = Toast.makeText(this,
                    R.string.usuarioInvalido, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
