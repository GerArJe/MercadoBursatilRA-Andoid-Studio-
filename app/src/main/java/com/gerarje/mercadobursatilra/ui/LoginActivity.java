package com.gerarje.mercadobursatilra.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.gerarje.mercadobursatilra.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //objeto viewflippet
    private ViewFlipper vf_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //vector que lleva las imagenes del slide
        int images  [] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        //identificador por id vf logo
        vf_logo = findViewById(R.id.vf_logo);

       /* //para que se quede en loop
        for (int i =0 ; i< images.length; i++){
            flipperImages(images[i]);
        }*/

       //usando el foreach

        for(int image: images){
            flipperImages(image);
        }
    }

    public void flipperImages (int image){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        vf_logo.addView(imageView);
        vf_logo.setFlipInterval(4000); // 4 segundos en el intervalo que va a cambiar
        vf_logo.setAutoStart(true);


        //la transicion

        vf_logo.setInAnimation(this, android.R.anim.slide_in_left);
        vf_logo.setInAnimation(this, android.R.anim.slide_out_right);
    }

    public void goToMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
