package com.example.fleets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class NovaMensagem extends AppCompatActivity {

    ConnectionClass connectionClass;
    TextView txt_mensagem;
    Button btt_enviarmensagem;
    ProgressBar pbbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_mensagem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Nova Mensagem");

        connectionClass = new ConnectionClass();
        txt_mensagem = (TextView) findViewById(R.id.txt_mensagem);
        btt_enviarmensagem = (Button) findViewById(R.id.btt_enviarmensagem);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btt_enviarmensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.execute("");

            }
        });
    }

    public class SendMessage extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String msg = txt_mensagem.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(NovaMensagem.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Toast.makeText(NovaMensagem.this, r, Toast.LENGTH_SHORT).show();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            if (msg.trim().equals(""))
                z = "Preencher o Campo Mensagem";

            else {
                boolean isSucess;
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Erro de Conexão com o Servidor";

                    } else {
                        String query = "insert into dbo.MENSAGEM (Mensagem) values ('" + msg + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        z = "Mensagem Enviada com sucesso";
                        isSucess = true;
                        txt_mensagem.setText("");
                    }
                } catch (Exception ex) {
                    isSucess = false;
                    z = "Erro ao Enviar Mensagem";
                }
            }
            return z;
        }
    }

    //seta de retorno para menu principal
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, TelaAvisos.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return true;
    }
}