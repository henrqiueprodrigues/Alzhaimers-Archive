package com.helloworld.prototipo2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.helloworld.prototipo2.objects.Memorias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ViewMemory extends AppCompatActivity {
    private ImageView imageView;
    private Gson gson= new Gson();
    private Memorias memoria= new Memorias();
    AlertDialog dialog;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory);
        read();

        imageView= findViewById(R.id.imageArquivo);
        TextView textDescricao= findViewById(R.id.textDescricao);
        TextView textTitulo= findViewById(R.id.textTitulo);

        loadImageFromStorage(memoria.getImagem());

        textTitulo.setText(memoria.getNome());
        textDescricao.setText(memoria.getDescricao());
    }

    public void back(View View) {
        this.finish();
    }

    public void read() {
        String temp = "";
        try {

            String nome= recuperaNomeArquivo();
            FileInputStream fin = openFileInput(nome);
            int c;

            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            memoria= gson.fromJson(temp, Memorias.class);

            Toast.makeText(ViewMemory.this, "file read ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImageFromStorage(String path)
    {
        if (path != null){
            try {
                File f= new File(path);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageView.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String recuperaNomeArquivo(){
        Bundle extras = getIntent().getExtras();
        String value= "";

        if (extras != null) {
             value = extras.getString("Nome");
        }
        return value;
    }

    public void deletar(android.view.View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewMemory.this);
        builder.setCancelable(true);
        builder.setTitle("Por favor confirme");
        builder.setMessage("Você deseja deletar essa memoria?");
        builder.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletarArquivo();

                    }
                });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();



    }

    public void deletarArquivo(){
        String nome= recuperaNomeArquivo();
        deleteFile(nome);
        startActivity(new Intent(this,MainMenu.class));
    }

    public void editarMemoria(android.view.View view){
        String nome= recuperaNomeArquivo();
        Intent Intent = new Intent(this, CreateMem.class);
        Intent.putExtra("Nome",nome);
        startActivity(Intent);
    }

    public void ajuda(View View){
        Intent Intent = new Intent(this,Ajuda.class);
        Intent.putExtra("Nome","VisualizarMemoria");
        startActivity(Intent);
    }
    public void menu(View View){
        Intent Intent = new Intent(this,MainMenu.class);
        Intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(Intent);
    }
}