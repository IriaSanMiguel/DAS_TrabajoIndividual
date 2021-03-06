package com.example.trabajoindividual_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

public class MyAccount_Activity extends AppCompatActivity {
    miDB db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        db = new miDB(this, 1);

        //Obtenemos en nombre de usuario
        Intent i = getIntent();
        username = i.getStringExtra("username");
        JSONObject json = db.getDatosUsuario(username);
        if (json == null) { //Si ha ocurrido un error
            Toast aviso = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT);
            aviso.show();
        } else {
            try {
                // Obtenemos los elementos necesarios del layout
                TextView usernameTextView = (TextView) findViewById(R.id.textUsername);
                TextView contrasenaTextView = (TextView) findViewById(R.id.textContrasena);
                TextView nombreTextView = (TextView) findViewById(R.id.textNombre);
                TextView apellidoTextView = (TextView) findViewById(R.id.textApellido);

                EditText usernameEditText = (EditText) findViewById(R.id.editarUsername);
                EditText contrasenaEditText = (EditText) findViewById(R.id.editarContrasena);
                EditText nombreEditText = (EditText) findViewById(R.id.editarNombre);
                EditText apellidoEditText = (EditText) findViewById(R.id.editarApellido);

                Button boton = (Button) findViewById(R.id.buttonGuardar);

                ConstraintLayout.LayoutParams paramsUsernameTextView = (ConstraintLayout.LayoutParams) usernameTextView.getLayoutParams();
                ConstraintLayout.LayoutParams paramsContrasenaTextView = (ConstraintLayout.LayoutParams) contrasenaTextView.getLayoutParams();
                ConstraintLayout.LayoutParams paramsNombreTextView = (ConstraintLayout.LayoutParams) nombreTextView.getLayoutParams();
                ConstraintLayout.LayoutParams paramsApellidoTextView = (ConstraintLayout.LayoutParams) apellidoTextView.getLayoutParams();

                // Ocultamos los EditText y el bot??n
                usernameEditText.setVisibility(View.INVISIBLE);
                contrasenaEditText.setVisibility(View.INVISIBLE);
                nombreEditText.setVisibility(View.INVISIBLE);
                apellidoEditText.setVisibility(View.INVISIBLE);
                boton.setVisibility(View.INVISIBLE);

                if (savedInstanceState != null) { // Si se ha girado la pantalla
                    // Comprobamos la visibilidad de cada elemento con la pantalla en vertical y la cambiamos en orizontal si es necesario
                    boolean visibilityUsername = savedInstanceState.getBoolean("VisibilityUsername");
                    boolean visibilityContrasena = savedInstanceState.getBoolean("VisibilityContrasena");
                    boolean visibilityNombre = savedInstanceState.getBoolean("VisibilityNombre");
                    boolean visibilityApellido = savedInstanceState.getBoolean("VisibilityApellido");
                    boolean salir = false;
                    if (visibilityUsername) {
                        usernameEditText.setVisibility(View.VISIBLE);
                        boton.setVisibility(View.VISIBLE);
                        salir = true;
                    }
                    if (visibilityContrasena) {
                        contrasenaEditText.setVisibility(View.VISIBLE);
                        boton.setVisibility(View.VISIBLE);
                        salir = true;
                    }
                    if (visibilityNombre) {
                        nombreEditText.setVisibility(View.VISIBLE);
                        boton.setVisibility(View.VISIBLE);
                        salir = true;
                    }
                    if (visibilityApellido) {
                        apellidoEditText.setVisibility(View.VISIBLE);
                        boton.setVisibility(View.VISIBLE);
                        salir = true;
                    }
                    if (salir) {
                        // Ajustamos los m??rgenes
                        paramsUsernameTextView.leftMargin = 200;
                        usernameTextView.setLayoutParams(paramsUsernameTextView);
                        paramsContrasenaTextView.leftMargin = 200;
                        contrasenaTextView.setLayoutParams(paramsContrasenaTextView);
                        paramsNombreTextView.leftMargin = 200;
                        nombreTextView.setLayoutParams(paramsNombreTextView);
                        paramsApellidoTextView.leftMargin = 200;
                        apellidoTextView.setLayoutParams(paramsApellidoTextView);
                        return;
                    }
                } else {
                    // Definimos los textos de los TextViews
                    usernameTextView.setText(getString(R.string.usernameActual) + ": " + username);
                    contrasenaTextView.setText(getString(R.string.contrasenaActual));
                    nombreTextView.setText(getString(R.string.nombreActual) + ": " + json.getString("Nombre"));
                    apellidoTextView.setText(getString(R.string.apellidoActual) + ": " + json.getString("Apellido"));
                    // Cambiamos el margen izquierdo de los TextViews
                    paramsUsernameTextView.leftMargin = 400;
                    paramsContrasenaTextView.leftMargin = 400;
                    paramsNombreTextView.leftMargin = 400;
                    paramsApellidoTextView.leftMargin = 400;
                    // Aplicamos los cambios
                    usernameTextView.setLayoutParams(paramsUsernameTextView);
                    contrasenaTextView.setLayoutParams(paramsContrasenaTextView);
                    nombreTextView.setLayoutParams(paramsNombreTextView);
                    apellidoTextView.setLayoutParams(paramsApellidoTextView);
                }
            } catch (Exception e) {
                Toast aviso = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT);
                aviso.show();
            }
        }
        // Cargar preferencias
        cargarPreferencias();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // Guardamos la visibilidad de los elementos en la view
        TextView usernameTextView = (TextView) findViewById(R.id.textUsername);
        TextView contrasenaTextView = (TextView) findViewById(R.id.textContrasena);
        TextView nombreTextView = (TextView) findViewById(R.id.textNombre);
        TextView apellidoTextView = (TextView) findViewById(R.id.textApellido);

        if (usernameTextView.getText().toString().equals(getString(R.string.cambiarUsername))) { // Si se estaba editando el username
            savedInstanceState.putBoolean("VisibilityUsername", true);
        }
        if (contrasenaTextView.getText().toString().equals(getString(R.string.cambiarUsername))) { // Si se estaba editando la contrase??a
            savedInstanceState.putBoolean("VisibilityContrasena", true);
        }
        if (nombreTextView.getText().toString().equals(getString(R.string.cambiarUsername))) { // Si se estaba editando el nombre
            savedInstanceState.putBoolean("VisibilityNombre", true);
        }
        if (apellidoTextView.getText().toString().equals(getString(R.string.cambiarUsername))) { // Si se estaba editando el apellido
            savedInstanceState.putBoolean("VisibilityApellido", true);
        }
        super.onRestoreInstanceState(savedInstanceState);

    }

    /*############################################################################################################################
    ######################################################## PREFERENCIAS ########################################################
    ##############################################################################################################################*/

    private void cargarPreferencias() {
        /*
        Pre:
        Post: Se han cargado las preferencias
        */

        // Obtenemos las preferencias
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma = prefs.getString("Idioma", "es");
        Boolean yaCargadas = prefs.getBoolean("PrefsCargadas", false);

        if (!yaCargadas) { // Si no se hab??an cargado antes
            Locale locale;
            switch (idioma) { // Cambiamos el idioma
                case "es": {
                    locale = new Locale("es");
                    break;
                }
                case "en": {
                    locale = new Locale("en");
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + idioma);
            }

            // Actualizamos las preferencias
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("PrefsCargadas", true);
            editor.apply();

            // Cambiamos el idioma
            Locale.setDefault(locale);
            Configuration conf = getBaseContext().getResources().getConfiguration();
            conf.setLocale(locale);
            conf.setLayoutDirection(locale);
            Context context = getBaseContext().createConfigurationContext(conf);
            getBaseContext().getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
            Intent i = new Intent(this, MyAccount_Activity.class);
            i.putExtra("username", username);
            finish();
            startActivity(i);
        }
    }

    @Override
    public void onDestroy() {
        /*
        Pre: Se ha cerrado la actividad
        Post: Se han actualizado las preferencias
        */

        // Cuando se cierre la actividad indicamos que las preferencias no est??n cargadas
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("PrefsCargadas", false);
        editor.apply();
    }

    /*############################################################################################################################
    ######################################################## FUNCIONALES #########################################################
    ##############################################################################################################################*/

    public void onClickGuardar(View v) {
        /*
        Pre: Se ha pulsado el bot??n guardar
        Post: Se actualizar??n los datos de los campos escritos
        */

        EditText usernameEditText = (EditText) findViewById(R.id.editarUsername);
        EditText contrasenaEditText = (EditText) findViewById(R.id.editarContrasena);
        EditText nombreEditText = (EditText) findViewById(R.id.editarNombre);
        EditText apellidoEditText = (EditText) findViewById(R.id.editarApellido);

        Boolean hayAlgoEscrito = false;
        Boolean error = false;

        // Se comprueban uno a uno los campos y si hay algo escrito en cualquiera se actualiza
        if (!usernameEditText.getText().toString().equals("")) {
            if (db.existeUsuario(usernameEditText.getText().toString())) { // Si ya existe un usuario con ese username
                usernameEditText.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                Toast aviso = Toast.makeText(this, getString(R.string.usuarioyaenuso), Toast.LENGTH_SHORT);
                aviso.show();
                return;
            }
            hayAlgoEscrito = true;
            error = !db.updateUsuarioUsername(username, usernameEditText.getText().toString());
            username = usernameEditText.getText().toString();
        }
        if (!contrasenaEditText.getText().toString().equals("")) {
            String contrasena = encriptarContrasena(contrasenaEditText.getText().toString());
            hayAlgoEscrito = true;
            error = !db.updateUsuarioContrasena(username, contrasena);
        }
        if (!nombreEditText.getText().toString().equals("")) {
            hayAlgoEscrito = true;
            error = !db.updateUsuarioNombre(username, nombreEditText.getText().toString());
        }
        if (!apellidoEditText.getText().toString().equals("")) {
            hayAlgoEscrito = true;
            error = !db.updateUsuarioApellido(username, apellidoEditText.getText().toString());
        }
        if (error) { // Si no se ha actualizado algo bien
            Toast aviso = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT);
            aviso.show();
            return;
        }
        if (!hayAlgoEscrito) { // Si no hay nada escrito
            Toast aviso = Toast.makeText(this, getString(R.string.rellenaralguncampo), Toast.LENGTH_SHORT);
            aviso.show();
            return;
        }
        Toast aviso = Toast.makeText(this, getString(R.string.datosactualizados), Toast.LENGTH_SHORT);
        aviso.show();
        Intent i = new Intent(this, this.getClass());
        i.putExtra("username", username);
        finish();
        startActivity(i);
    }


    private String encriptarContrasena(String contrasena) {
        /*
        Pre: Una contrase??a
        Post: Se devuelve la contrase??a encriptada
        */
        try {
            // Encriptamos la contrase??a
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(contrasena.getBytes(StandardCharsets.UTF_8));

            // Pasamos a bytes
            byte[] bytes = messageDigest.digest();

            // Pasamos a formato hexadecimal
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Devolvemos la contrase??a ya encritada
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    // Los siguientes m??todos se ejecutan cuando se pulsa en un TextView y hacen visible el edittext correspondiente
    // para que el usuario pueda cambiar sus datos

    public void onClickUsername(View v) {
        EditText usernameEditText = (EditText) findViewById(R.id.editarUsername);
        TextView usernameTextView = (TextView) findViewById(R.id.textUsername);
        ConstraintLayout.LayoutParams paramsUsernameTextView = (ConstraintLayout.LayoutParams) usernameTextView.getLayoutParams();
        paramsUsernameTextView.leftMargin = 100;
        usernameTextView.setLayoutParams(paramsUsernameTextView);
        usernameTextView.setText(getString(R.string.cambiarUsername));
        Button boton = (Button) findViewById(R.id.buttonGuardar);
        boton.setVisibility(View.VISIBLE);
        usernameEditText.setVisibility(View.VISIBLE);

    }

    public void onClickContrasena(View v) {
        EditText contrasenaEditText = (EditText) findViewById(R.id.editarContrasena);
        TextView contrasenaTextView = (TextView) findViewById(R.id.textContrasena);
        ConstraintLayout.LayoutParams paramsContrasenaTextView = (ConstraintLayout.LayoutParams) contrasenaTextView.getLayoutParams();
        paramsContrasenaTextView.leftMargin = 100;
        contrasenaTextView.setLayoutParams(paramsContrasenaTextView);
        contrasenaTextView.setText(getString(R.string.cambiarContrase??a));
        Button boton = (Button) findViewById(R.id.buttonGuardar);
        boton.setVisibility(View.VISIBLE);
        contrasenaEditText.setVisibility(View.VISIBLE);
    }

    public void onClickNombre(View v) {
        EditText nombreEditText = (EditText) findViewById(R.id.editarNombre);
        TextView nombreTextView = (TextView) findViewById(R.id.textNombre);
        ConstraintLayout.LayoutParams paramsNombreTextView = (ConstraintLayout.LayoutParams) nombreTextView.getLayoutParams();
        paramsNombreTextView.leftMargin = 100;
        nombreTextView.setLayoutParams(paramsNombreTextView);
        nombreTextView.setText(getString(R.string.cambiarNombre));
        Button boton = (Button) findViewById(R.id.buttonGuardar);
        boton.setVisibility(View.VISIBLE);
        nombreEditText.setVisibility(View.VISIBLE);
    }

    public void onClickApellido(View v) {
        EditText apellidoEditText = (EditText) findViewById(R.id.editarApellido);
        TextView apellidoTextView = (TextView) findViewById(R.id.textApellido);
        ConstraintLayout.LayoutParams paramsApellidoTextView = (ConstraintLayout.LayoutParams) apellidoTextView.getLayoutParams();
        paramsApellidoTextView.leftMargin = 100;
        apellidoTextView.setLayoutParams(paramsApellidoTextView);
        apellidoTextView.setText(getString(R.string.cambiarApellido));
        Button boton = (Button) findViewById(R.id.buttonGuardar);
        boton.setVisibility(View.VISIBLE);
        apellidoEditText.setVisibility(View.VISIBLE);
    }
}