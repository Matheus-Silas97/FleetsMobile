package com.example.fleets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

    }

    //Botões Menu Principal
    public void rotas(View view) {
        Intent TelaMensagem = new Intent(this, com.example.fleets.TelaMensagem.class);
        startActivity(TelaMensagem);
    }

    public void avisos(View view) {
        Intent telaavisos = new Intent(this, TelaAvisos.class);
        startActivity(telaavisos);
    }

    public void sair(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        builder.setMessage("Você deseja sair do Aplicativo?");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);

            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Botão voltar padrão android com aviso de saída
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        builder.setMessage("Você deseja sair do Aplicativo?");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);

            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

}