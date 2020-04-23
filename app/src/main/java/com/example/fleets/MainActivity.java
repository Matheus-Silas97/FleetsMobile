package com.example.fleets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    TextView txt_login, txt_senha;
    Button btt_acessar;
    ProgressBar pbbar;

    //Cria um atributo global pra poder passar no método RetornaMotorista.
    Motorista motorista = new Motorista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        txt_login = findViewById(R.id.txt_login);
        txt_senha = findViewById(R.id.txt_senha);
        btt_acessar = findViewById(R.id.btt_acessar);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btt_acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");
            }
        });
    }

    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String userid = txt_login.getText().toString();
        String password = txt_senha.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals(""))
                z = "Preencher os Campos Corretamente";

            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Erro de Conexão com o Servidor";

                    } else {
                        String query = "select * from dbo.vw_UsuarioMotorista where Login='" + userid + "' and Senha='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {

                            motorista.dataNascimento = rs.getDate("Nascimento");
                            motorista.nome = rs.getString("Nome");
                            motorista.RG = rs.getString("RG");
                            motorista.sexo = rs.getString("Sexo");
                            motorista.estadoCivil = rs.getString("EstadoCivil");
                            motorista.CPF = rs.getString("CPF");
                            motorista.CTPS = rs.getString("CTPS");
                            motorista.cargo = rs.getString("Cargo");
                            motorista.login = rs.getString("Login");
                            motorista.senha = rs.getString("Senha");

                            motorista.CNH = rs.getString("CNH");
                            motorista.habilitacao = rs.getString("Habilitacao");
                            motorista.tipoSanguinio = rs.getString("TipoSanguineo");
                            motorista.deficiencia = rs.getString("Deficiencia");
                            motorista.CodMotorista = rs.getInt("Cod_Motorista");

                            if (!motorista.cargo.equals("Motorista")) {

                                z = "Acesso Permitido Apenas para Motoristas!";
                                isSuccess = false;
                            } else if (motorista.cargo.equals("Motorista")) {

                                Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                                startActivity(intent);
                                z = "Login Com Sucesso! Bem Vindo " + motorista.nome;
                                isSuccess = true;
                            }
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Erro: " + ex;
                }
            }
            return z;
        }

        public Motorista RetornaMotorista() {
            return motorista;
        }
    }

    public void acessarSemCadastro(View view) {
        String userid = txt_login.getText().toString();
        String password = txt_senha.getText().toString();

        if (userid.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencher os campos corretamente", Toast.LENGTH_SHORT).show();
        } else if (userid.equals("adm") && password.equals("1234")) {
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Acesso Permitido para teste", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Acesso apenas para teste, ACESSO NEGADO", Toast.LENGTH_SHORT).show();
        }
    }
}