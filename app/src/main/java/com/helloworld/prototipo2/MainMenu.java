package com.helloworld.prototipo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

public class MainMenu extends AppCompatActivity {

    String nomeMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String[] files= getFiles();

        createButtons(files);
    }


    public void viewMemoria(View View){
        Intent Intent = new Intent(this, ViewMemory.class);
        Intent.putExtra("Nome",nomeMemoria);
        startActivity(Intent);
    }

    public void novo(View View){
        startActivity(new Intent(this,CreateMem.class));
    }

    public String[] getFiles(){
        File f = new File(getFilesDir().getPath()); // MUDEI ISSO - Karina
        return  f.list();
    }

    public void createButtons(String[] files){
        for(String nome: files){
            Button myButton = new Button(this);
            myButton.setText(nome);
            myButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    nomeMemoria= nome;
                    viewMemoria(v);
                }
            });

            LinearLayout ll = (LinearLayout)findViewById(R.id.buttonlayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ll.addView(myButton, lp);
        }
    }

    public void ajuda(View View){
        Intent Intent = new Intent(this,Ajuda.class);
        Intent.putExtra("Nome","MainMenu");
        startActivity(Intent);
    }

}
