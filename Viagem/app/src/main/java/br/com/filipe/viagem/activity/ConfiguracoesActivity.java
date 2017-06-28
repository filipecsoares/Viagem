package br.com.filipe.viagem.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import br.com.filipe.viagem.R;

/**
 * Created by rafael on 24/05/17.
 */

public class ConfiguracoesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracoes);
    }
}
