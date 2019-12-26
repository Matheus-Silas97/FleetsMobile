package com.example.fleets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class rotasDetalhes extends AppCompatActivity {

    TextView detID, detVei, detOri, detDest, detObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas_detalhes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Viagem");     //Titulo para ser exibido na  Action Bar em frente à seta

        detID = (TextView) findViewById(R.id.detID);
        detVei = (TextView) findViewById(R.id.detVei);
        detOri = (TextView) findViewById(R.id.detOri);
        detDest = (TextView) findViewById(R.id.detDest);
        detObs = (TextView) findViewById(R.id.detObs);
    }

    //seta de retorno para menu principal
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, TelaMensagem.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return true;
    }
}