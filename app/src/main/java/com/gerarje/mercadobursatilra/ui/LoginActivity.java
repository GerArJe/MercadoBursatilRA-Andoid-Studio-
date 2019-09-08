package com.gerarje.mercadobursatilra.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gerarje.mercadobursatilra.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "LoginActivity";
    private static final int SIGN_IN_GOOGLE_CODE = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;

    private SignInButton btnSignInGoogle;
    private ProgressBar progressbarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);
        progressbarLogin = findViewById(R.id.progressbarLogin);

        hideProgressBar();

        initialize();

        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_GOOGLE_CODE);
                showProgressBar();

            }
        });
    }

    //Inicializar la autenticación
    private void initialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged - signed_in" + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - signed_in" + firebaseUser.getEmail());
                }else {
                    Log.w(TAG, "onAuthStateChanged - signed_out");
                }
            }
        };

        //Inicialización de Google Account
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    //Inicio de sesión con Google
    private void signInGoogleFirebase(GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){
            AuthCredential authCredential =
                    GoogleAuthProvider.getCredential(googleSignInResult.getSignInAccount().getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Google Authentication Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Google Authentication Unsuccess", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(LoginActivity.this, "Google Sign In Unsuccess", Toast.LENGTH_SHORT).show();
        }
    }

    //mostar barra de progreso
    public void showProgressBar() {
        progressbarLogin.setVisibility(View.VISIBLE);
    }

    //ocultar barra de progreso
    public void hideProgressBar() {
        progressbarLogin.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_GOOGLE_CODE){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogleFirebase(googleSignInResult);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
