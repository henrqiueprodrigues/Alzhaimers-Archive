package com.helloworld.prototipo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void acessarMainMenu(View View){
        Intent Intent = new Intent(this, MainMenu.class);
        startActivity(Intent);
    }

}







