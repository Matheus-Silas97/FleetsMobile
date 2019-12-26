package com.example.fleets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TelaAvisos extends AppCompatActivity {

    private ArrayList<ClassListItems2> itemArrayList;
    private TelaAvisos.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_avisos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativa o botão
        getSupportActionBar().setTitle("Avisos");     //Titulo para ser exibido na  Action Bar em frente à seta

        listView = (ListView) findViewById(R.id.listViewAvisos);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassListItems2>();

        SyncData orderData = new SyncData();
        orderData.execute("");
    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Erro";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(TelaAvisos.this, "Carregando",
                    "Carregando Mensagens", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN();
                if (conn == null) {
                    success = false;
                } else {
                    String query = "SELECT Mensagem FROM dbo.MENSAGEM";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ClassListItems2(rs.getString("Mensagem")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Mensagens Recebidas";
                        success = true;
                    } else {
                        msg = "Nenhuma Mensagem no Momento";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            Toast.makeText(TelaAvisos.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, TelaAvisos.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }
    }

    public class MyAppAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView msgRecebida;
        }

        public List<ClassListItems2> parkingList;

        public Context context;
        ArrayList<ClassListItems2> arraylist;

        private MyAppAdapter(List<ClassListItems2> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<ClassListItems2>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_contentavisos, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.msgRecebida = (TextView) rowView.findViewById(R.id.txt);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.msgRecebida.setText(parkingList.get(position).getmsgRecebida() + "");

            return rowView;
        }
    }
//Fim ListView

    //seta de retorno para menu principal
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MenuPrincipal.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return true;
    }

    //botão flutuante direcional para a tela de nova mensagem
    public void novamensagem(View view) {
        Intent novamensagem = new Intent(this, NovaMensagem.class);
        startActivity(novamensagem);
    }
}