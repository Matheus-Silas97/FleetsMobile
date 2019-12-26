package com.example.fleets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaMensagem extends AppCompatActivity {

    private ArrayList<ClassListItems> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_mensagem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Rotas");

        listView = (ListView) findViewById(R.id.listViewRotas);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassListItems>();

        SyncData orderData = new SyncData();
        orderData.execute("");

    }

    MainActivity motoristaLogado = new MainActivity();


    class SyncData extends AsyncTask<String, String, String> {
        String msg = "Erro";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(TelaMensagem.this, "Sincronizando",
                    "Carregando Rotas", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN();
                if (conn == null) {
                    success = false;
                } else {

                    String query = "SELECT * FROM vw_Viagem"; //WHERE Motorista='" + motoristaLogado.motorista.CodMotorista + "'";

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {
                        while (rs.next()) {
                            try {

                                itemArrayList.add(new ClassListItems(rs.getString("Cod_Viagem"), rs.getString("Placa"), rs.getString("Rua"), rs.getString("Numero"), rs.getString("CEP"), rs.getString("Estado"), rs.getString("Cidade")));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Rotas Encontradas";
                        success = true;
                    } else {
                        msg = "Nenhuma Rota no seu Itinerário";
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
            Toast.makeText(TelaMensagem.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, TelaMensagem.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }

            }
        }
    }

    public class MyAppAdapter extends BaseAdapter {
        public class ViewHolder {

            TextView textCod_Viagem;
            TextView textPlaca;
            TextView textRua;
            TextView textNumero;
            TextView textCEP;
            TextView textEstado;
            TextView textCidade;
        }

        public List<ClassListItems> parkingList;

        public Context context;
        ArrayList<ClassListItems> arraylist;

        private MyAppAdapter(List<ClassListItems> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<ClassListItems>();
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
                rowView = inflater.inflate(R.layout.list_content, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textCod_Viagem = (TextView) rowView.findViewById(R.id.Cod_Viagem);
                viewHolder.textPlaca = (TextView) rowView.findViewById(R.id.cod_Veiculo);
                viewHolder.textRua = (TextView) rowView.findViewById(R.id.Rua);
                viewHolder.textNumero = (TextView) rowView.findViewById(R.id.Numero);
                viewHolder.textCEP = (TextView) rowView.findViewById(R.id.CEP);
                viewHolder.textEstado = (TextView) rowView.findViewById(R.id.Estado);
                viewHolder.textCidade = (TextView) rowView.findViewById(R.id.Cidade);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textCod_Viagem.setText(parkingList.get(position).getCod_Viagem() + "");
            viewHolder.textPlaca.setText(parkingList.get(position).getPlaca() + "");
            viewHolder.textRua.setText(parkingList.get(position).getRua() + "");
            viewHolder.textNumero.setText(parkingList.get(position).getNumero() + "");
            viewHolder.textCEP.setText(parkingList.get(position).getCEP() + "");
            viewHolder.textEstado.setText(parkingList.get(position).getEstado() + "");
            viewHolder.textCidade.setText(parkingList.get(position).getCidade() + "");
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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuPrincipal.class));
        finishAffinity();
        return;
    }
}