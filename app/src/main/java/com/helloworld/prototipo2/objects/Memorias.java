package com.helloworld.prototipo2.objects;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

public class Memorias {
    private String nome;
    private String imagem; // A imagem virou o path dela no diretório
    private String descricao;

    public Memorias(){

    }

    public Memorias(String nome, String imagem, String descricao) {
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }//Mudei aqui

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }// Mudei aqui

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    }
