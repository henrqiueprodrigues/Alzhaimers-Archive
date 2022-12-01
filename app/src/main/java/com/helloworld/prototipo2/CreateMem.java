package com.helloworld.prototipo2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.helloworld.prototipo2.objects.Memorias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CreateMem extends AppCompatActivity {

    private EditText textDescricao, textTitulo;
    private String descricao, titulo;
    private ImageView imageView;
    private TextView addImgText;
    private Memorias memoria;
    private Gson gson= new Gson();
    private Bitmap selectedImageBitmap= null;
    private Boolean editar= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mem);

        textDescricao = findViewById(R.id.inputTextoMemoria);
        textTitulo = findViewById(R.id.MemoriaTitulo);
        addImgText= findViewById(R.id.TextoAddImg);
        imageView = findViewById(R.id.imageView);
        memoria= new Memorias();
        String nome= recuperaNomeArquivo();
        if(!nome.equals("")){
            editar= true;
            read();

            loadImageFromStorage(memoria.getImagem());
            textTitulo.setText(memoria.getNome());
            textDescricao.setText(memoria.getDescricao());
            textTitulo.setFocusable(false);

        }


        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

    }

    public void ajuda(View View){
        String text ="";
        if (!editar) {
            text = "CriarMemoria";
        }else{
            text = "EditarMemoria";
        }
        Intent Intent = new Intent(this,Ajuda.class);
        Intent.putExtra("Nome",text);
        startActivity(Intent);
    }

    public void save(View View){

        descricao = textDescricao.getText().toString();
        titulo= textTitulo.getText().toString();
        if(titulo.equals(null)||titulo.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateMem.this);
            builder.setCancelable(true);
            builder.setTitle("Por favor confirme");
            builder.setMessage("A memória necessita de um titulo, por favor insira um.");
            builder.setPositiveButton("Confirmar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            String dir = saveToInternalStorage(selectedImageBitmap);

            if (memoria.getImagem() == null) memoria.setImagem(dir);

            memoria.setNome(titulo);
            memoria.setDescricao(descricao);

            try {
                FileOutputStream fOut = openFileOutput(titulo, Context.MODE_PRIVATE);
                String json = gson.toJson(memoria);
                fOut.write(json.getBytes());
                fOut.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent Intent = new Intent(this, MainMenu.class);
            startActivity(Intent);
        }
    }

    public void back(View View){
        this.finish();
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {

                        Uri selectedImageUri = data.getData();

                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        ImageView img=(ImageView)findViewById(R.id.imageView);
                        img.setImageBitmap(selectedImageBitmap);
                        addImgText.setVisibility(View.GONE);
                    }
                }
            });

    private String saveToInternalStorage(Bitmap bitmapImage){

        if (bitmapImage== null){
            return null;
        }

        ContextWrapper cw = new ContextWrapper(getApplicationContext());


        titulo= textTitulo.getText().toString();

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath=new File(directory,titulo +".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.toString();
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

            Toast.makeText(CreateMem.this, "file read ", Toast.LENGTH_SHORT).show();
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

    public void menu(View View){
        Intent Intent = new Intent(this,MainMenu.class);
        Intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(Intent);
    }
}