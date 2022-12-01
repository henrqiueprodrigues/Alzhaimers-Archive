package com.helloworld.prototipo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ajuda extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
        textView = findViewById(R.id.textView);
        setText();


    }

    public void back(View View){
        this.finish();
    }

    private String recuperaNomeArquivo() {
        Bundle extras = getIntent().getExtras();
        String value = "";

        if (extras != null) {
            value = extras.getString("Nome");
        }
        return value;
    }

    @SuppressLint("SetTextI18n")
    private void setText() {
        String tituloPagina = recuperaNomeArquivo();

        switch (tituloPagina) {
            case "MainMenu":
                textView.setText("Este é o menu principal, nele você pode criar uma nova memria,´ou escolher uma que já existe.\n" +
                        "Você tambem pode voltar para a tela inicial.\n" +
                        "Para criar uma nova memória, toque com o dedo no botão escrito “ADICIONAR MEMORIA”, a tela irá mudar para\n" +
                        "a página de criação.\n" +
                        "Para escolher alguma memória existente, toque com o dedo o botão com o nome da memória que\n" +
                        "quer visualizar.\n" +
                        "Se houver alguma palavra que não entenda, toque no botão “ajuda” novamente.");
                break;
            case "CriarMemoria":
                textView.setText("Esta é a página de criação, nele você poderá criar uma [página/tela] para visualizar depois,\n" +
                        "para isso é necessário escrever um título, escolher uma foto e colocar uma descrição.\n" +
                        "Para voltar para a página anterior toque no botão voltar.\n" +
                        "Toque em “salvar” para guardar a página. Quando tocar no botão “salvar” a tela irá mudar\n" +
                        "para o modo de visualização.");
                break;
            case "VisualizarMemoria":
                textView.setText("Esta é a página de visualização, você pode visualizar a memória, escolher fazer\n" +
                        "alterações ou deletar a memória");
                break;
            case "EditarMemoria":
                textView.setText("Esta é a página de edição, para editar apenas toque na parte que quer editar. E clique em\n" +
                        "“salvar” para salvar suas alterações.");
                break;
        }
    }
    public void menu(View View){
        Intent Intent = new Intent(this,MainMenu.class);
        Intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(Intent);
    }
}
