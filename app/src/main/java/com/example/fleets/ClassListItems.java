package com.example.fleets;

public class ClassListItems {

    public String Cod_Viagem;
    public String Placa;
    public String Rua;
    public String Numero;
    public String CEP;
    public String Estado;
    public String Cidade;

    public ClassListItems(String Cod_Viagem, String Placa,
                          String Rua, String Numero,
                          String CEP, String Estado, String Cidade) {

        this.Cod_Viagem = Cod_Viagem;
        this.Placa = Placa;
        this.Rua = Rua;
        this.Numero = Numero;
        this.CEP = CEP;
        this.Estado = Estado;
        this.Cidade = Cidade;
    }


    public String getCod_Viagem() {
        return Cod_Viagem;
    }

    public String getPlaca() {
        return Placa;
    }

    public String getRua() {
        return Rua;
    }

    public String getNumero() {
        return Numero;
    }

    public String getCEP() {
        return CEP;
    }

    public String getEstado() {
        return Estado;
    }

    public String getCidade() {
        return Cidade;
    }
}




