package com.example.trabajoindividual_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

public class miDB extends SQLiteOpenHelper {
    private static final String nombre_DB = "ReviewsPeliculasUsuarios";
    private Context pcontext;

    public miDB(@Nullable Context context, int version) {
        super(context, nombre_DB, null, version);
        pcontext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Pre:
        //Post: Se han creado todas las tablas de la base de datos correctamente y se han introducido los datos
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios ('NombreUsuario' VARCHAR(255) PRIMARY KEY NOT NULL, 'Nombre'" +
                " VARCHAR(255) NOT NULL, 'Apellido' VARCHAR(255) NOT NULL, 'Contrasena' VARCHAR(255) NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Peliculas ('Titulo' VARCHAR(255) PRIMARY KEY NOT NULL, 'Director'" +
                " VARCHAR(255) NOT NULL, 'Anio' INTEGER NOT NULL, 'Poster' BLOB NOT NULL, 'PuntuacionMedia' FLOAT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Reviews ('Usuario' VARCHAR(255) NOT NULL, 'Pelicula'" +
                " VARCHAR(255) NOT NULL, 'Review' LONGTEXT NOT NULL, 'Puntuacion' INTEGER NOT NULL," +
                "FOREIGN KEY ('Usuario') REFERENCES Usuarios ('NombreUsuario')," +
                "FOREIGN KEY ('Pelicula') REFERENCES Peliculas ('Titulo'))");

        // Creamos algunas pel??culas
        // The Batman
        ContentValues datos_batman = new ContentValues();
        datos_batman.put("Titulo", "The Batman");
        datos_batman.put("Director", "Matt Reeves");
        datos_batman.put("Anio", 2022);
        datos_batman.put("Poster", getByteArray(R.drawable.the_batman));
        datos_batman.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_batman);

        // Uncharted
        ContentValues datos_uncharted = new ContentValues();
        datos_uncharted.put("Titulo", "Uncharted");
        datos_uncharted.put("Director", "Ruben Fleischer");
        datos_uncharted.put("Anio", 2022);
        datos_uncharted.put("Poster", getByteArray(R.drawable.uncharted));
        datos_uncharted.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_uncharted);

        // Dune
        ContentValues datos_dune = new ContentValues();
        datos_dune.put("Titulo", "Dune");
        datos_dune.put("Director", "Denis Villeneuve");
        datos_dune.put("Anio", 2022);
        datos_dune.put("Poster", getByteArray(R.drawable.dune));
        datos_dune.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_dune);

        // King Richard
        ContentValues datos_richard = new ContentValues();
        datos_richard.put("Titulo", "King Richard");
        datos_richard.put("Director", "Reinaldo Marcus Green");
        datos_richard.put("Anio", 2021);
        datos_richard.put("Poster", getByteArray(R.drawable.king_richard));
        datos_richard.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_richard);

        // Clifford
        ContentValues datos_clifford = new ContentValues();
        datos_clifford.put("Titulo", "Clifford");
        datos_clifford.put("Director", "Walt Becker");
        datos_clifford.put("Anio", 2021);
        datos_clifford.put("Poster", getByteArray(R.drawable.clifford));
        datos_clifford.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_clifford);

        // Blade Runner
        ContentValues datos_bladerunner = new ContentValues();
        datos_bladerunner.put("Titulo", "Blade Runner");
        datos_bladerunner.put("Director", "Ridley Scott");
        datos_bladerunner.put("Anio", 1982);
        datos_bladerunner.put("Poster", getByteArray(R.drawable.blade));
        datos_bladerunner.put("PuntuacionMedia", 0);
        sqLiteDatabase.insert("Peliculas", null, datos_bladerunner);


        // Creamos algunos usuarios
        // Todas las contrase??as son 1234
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('isanmiguel', 'Iria', 'San Miguel', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('aitorjus', 'Aitor', 'Perez', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('iker0610', 'Iker', 'de la Iglesia', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('PLATASSON', 'Alex', 'Platas', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('christian', 'Christian', 'Berrocal', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('pepe125', 'Pepe', 'Perez', '81dc9bdb52d04dc20036dbd8313ed055')");
        sqLiteDatabase.execSQL("INSERT INTO Usuarios VALUES ('58ana_', 'Ana', 'Martinez', '81dc9bdb52d04dc20036dbd8313ed055')");

        // Creamos algunas reviews
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('isanmiguel', 'The Batman', 'Esta bien, aunque Bruce Wayne es un poco emo.', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('PLATASSON', 'The Batman', 'Muy buen Batman, un Bruce Wayne muy flojo.', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('iker0610', 'The Batman', 'Pese a ser la caracter??stica principal del villano a priori principal, " +
                "los acertijos quedan muy en tercer plano y son muy simplones. Alfred podr??a haber sido bastante mejor... P.D. Hasta los bots de fortnite " +
                "tienen mejor punter??a que los guardas del pinguino.', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('pepe125', 'Uncharted', 'Mala, gen??rica, sin alma, predecible, no destaca enabsolutamente nada, " +
                "es como una lista de clich??s uno detr??s de otro de lo que una pel??cula t??pica de aventuras debe tener.', 1)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('58ana_', 'Uncharted', 'A mis hijas de menos de 12 a??os les gust??. Mi opini??n es p??sima. No hay gui??n y se nota a los 5 minutos de empezar.', 1)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('christian', 'Dune', 'Profunda, impactante y bien trabajada.', 5)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('aitorjus', 'Dune', 'Literal el 98% de lo que dura la pel??cula es arena, y el % restante cr??ditos.', 1)");
        sqLiteDatabase.execSQL("INSERT INTO Reviews VALUES ('58ana_', 'Clifford', 'Para los ni??os est?? bien. Se van a divertir un mont??n con el perro rojo gigante.', 3)");

        // Actaulizamos la puntuaci??n media de las pel??culas a las que les hemos a??adido alguna pel??cula
        // The Batman
        String[] columnas = new String[]{"Puntuacion"};
        String[] param = new String[]{"The Batman"};
        Cursor cu = sqLiteDatabase.query("Reviews", columnas, "Pelicula=?", param, null, null, null);
        int totalStars = 0;
        int cont = cu.getCount();
        while (cu.moveToNext()) {
            totalStars += cu.getInt(0);
        }
        ContentValues datos = new ContentValues();
        String[] argumentos = new String[]{"The Batman"};
        datos.put("PuntuacionMedia", totalStars / cont);
        sqLiteDatabase.update("Peliculas", datos, "Titulo = ?", argumentos);

        // Uncharted
        columnas = new String[]{"Puntuacion"};
        param = new String[]{"Uncharted"};
        cu = sqLiteDatabase.query("Reviews", columnas, "Pelicula=?", param, null, null, null);
        totalStars = 0;
        cont = cu.getCount();
        while (cu.moveToNext()) {
            totalStars += cu.getInt(0);
        }
        datos = new ContentValues();
        argumentos = new String[]{"Uncharted"};
        datos.put("PuntuacionMedia", totalStars / cont);
        sqLiteDatabase.update("Peliculas", datos, "Titulo = ?", argumentos);

        // Dune
        columnas = new String[]{"Puntuacion"};
        param = new String[]{"Dune"};
        cu = sqLiteDatabase.query("Reviews", columnas, "Pelicula=?", param, null, null, null);
        totalStars = 0;
        cont = cu.getCount();
        while (cu.moveToNext()) {
            totalStars += cu.getInt(0);
        }
        datos = new ContentValues();
        argumentos = new String[]{"Dune"};
        datos.put("PuntuacionMedia", totalStars / cont);
        sqLiteDatabase.update("Peliculas", datos, "Titulo = ?", argumentos);

        // Clifford
        columnas = new String[]{"Puntuacion"};
        param = new String[]{"Clifford"};
        cu = sqLiteDatabase.query("Reviews", columnas, "Pelicula=?", param, null, null, null);
        totalStars = 0;
        cont = cu.getCount();
        while (cu.moveToNext()) {
            totalStars += cu.getInt(0);
        }
        datos = new ContentValues();
        argumentos = new String[]{"Clifford"};
        datos.put("PuntuacionMedia", totalStars / cont);
        sqLiteDatabase.update("Peliculas", datos, "Titulo = ?", argumentos);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Peliculas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Reviews");
        onCreate(sqLiteDatabase);
    }

    private byte[] getByteArray(int drawable) {
        /*
        Pre: El integer del drawable a convertir
        Post: Se ha transformado el drawable a un byte[]
        */

        // Conseguimos el Bitmap del drawable
        Bitmap bitmapimage = BitmapFactory.decodeResource(pcontext.getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // Devolvemos el byte[]
        return byteArrayOutputStream.toByteArray();
    }

    public JSONObject getReviewsDePelicula(String titulo) {
        /*
        Pre: El t??tulo de una pel??cula
        Post: Se devuelven las reviews de esa pel??cula en formato JSON
        */

        // Hacemos la petici??n
        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Usuario", "Review", "Puntuacion"};
        String[] param = new String[]{titulo};
        Cursor cu = db.query("Reviews", columnas, "Pelicula=?", param, null, null, null);

        String[] lUsers = new String[cu.getCount()];
        String[] lReviews = new String[cu.getCount()];
        float[] lRatings = new float[cu.getCount()];

        // Cargamos los datos en el JSON
        try {
            JSONObject json = new JSONObject();
            while (cu.moveToNext()) {
                lUsers[cu.getPosition()] = cu.getString(0);
                lReviews[cu.getPosition()] = cu.getString(1);
                lRatings[cu.getPosition()] = cu.getFloat(2);
            }
            json.put("lUsers", lUsers);
            json.put("lReviews", lReviews);
            json.put("lRatings", lRatings);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addUsuario(String nombreUsuario, String nombre, String apellido, String contrasena) {
        /*
        Pre: El username, el nombre del usuario, su apellido y la contrase??a
        Post: Se ha creado el usuario correctamente
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("NombreUsuario", nombreUsuario);
            datos.put("Nombre", nombre);
            datos.put("Apellido", apellido);
            datos.put("Contrasena", contrasena);
            db.insert("Usuarios", null, datos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean addPelicula(String titulo, String director, int anio, byte[] poster) {
        /*
        Pre: El titulo, el director de la pel??cula, el a??o en el que se estren?? y el poster
        Post: Se ha creado la pel??cula correctamente
        */

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("Titulo", titulo);
            datos.put("Director", director);
            datos.put("Anio", anio);
            datos.put("Poster", poster);
            datos.put("PuntuacionMedia", 0);
            db.insert("Peliculas", null, datos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean addReview(String nombreUsuario, String titulo, String review, float puntuacion) {
        /*
        Pre: El username del usuario que ha escrito la rese??a, el t??tulo de la pel??cula, la review y la puntuaci??n dada
        Post: Se ha creado la review correctamente
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("Pelicula", titulo);
            datos.put("Usuario", nombreUsuario);
            datos.put("Review", review);
            datos.put("Puntuacion", puntuacion);
            db.insert("Reviews", null, datos);
            db.close();
            actualizarPuntuacionPelicula(titulo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void actualizarPuntuacionPelicula(String pelicula) {
        /*
        Pre: El t??tulo de la pel??cula
        Post: Se ha actualizado la puntuaci??n de la pel??cula correctamente
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Puntuacion"};
        String[] param = new String[]{pelicula};
        Cursor cu = db.query("Reviews", columnas, "Pelicula=?", param, null, null, null);
        int totalStars = 0;
        int cont = cu.getCount();
        while (cu.moveToNext()) {
            totalStars += cu.getInt(0);
        }
        ContentValues datos = new ContentValues();
        String[] argumentos = new String[]{pelicula};
        datos.put("PuntuacionMedia", (float) totalStars / cont);
        db.update("Peliculas", datos, "Titulo = ?", argumentos);
        db.close();
    }

    public boolean tieneEsaContrasena(String usuario, String contrasena) {
        /*
        Pre: Un username y su contrase??a
        Post: Devuelve true si ese usuario tiene esa contrase??a, false en caso contrario
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Contrasena"};
        String[] param = new String[]{usuario};
        Cursor cu = db.query("Usuarios", columnas, "NombreUsuario=?", param, null, null, null);
        cu.moveToNext();
        String contrasenaCorrecta = cu.getString(0);
        if (contrasenaCorrecta.equals(contrasena)) {
            return true;
        }
        return false;

    }

    public boolean existePelicula(String titulo){
        /*
        Pre: El t??tulo de una pel??cula
        Post: Devuelve true si existe una pel??cula con ese t??tulo, false en caso contrario
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Titulo"};
        String[] param = new String[]{"Titulo"};
        Cursor cu = db.query("Peliculas", columnas, "Titulo=?", param, null, null, null);
        if (!cu.moveToNext()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean existeUsuario(String nombreUsuario) {
        /*
        Pre: Un username
        Post: Devuelve true si existe ese usuario, false en caso contrario
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"NombreUsuario"};
        String[] param = new String[]{nombreUsuario};
        Cursor cu = db.query("Usuarios", columnas, "NombreUsuario=?", param, null, null, null);
        if (!cu.moveToNext()) {
            return false;
        } else {
            return true;
        }
    }


    public JSONObject getDatosUsuario(String username) {
        /*
        Pre: Un username
        Post: Devuelve los datos del usuario con ese username en formato JSON
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Nombre", "Apellido"};
        String[] param = new String[]{username};
        Cursor cu = db.query("Usuarios", columnas, "NombreUsuario=?", param, null, null, null);
        try {
            JSONObject json = new JSONObject();
            cu.moveToNext();
            for (int i = 0; i < 2; i++) {
                json.put(cu.getColumnName(i), cu.getString(i));
            }
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getDatosPelicula(String titulo) {
        /*
        Pre: El t??tulo de una pel??cula
        Post: Devuelve los datos de la pel??cula con ese t??tulo en formato JSON
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Director", "Anio", "PuntuacionMedia"};
        String[] param = new String[]{titulo};
        Cursor cu = db.query("Peliculas", columnas, "Titulo=?", param, null, null, null);
        try {
            JSONObject json = new JSONObject();
            cu.moveToNext();
            json.put(cu.getColumnName(0), cu.getString(0));
            json.put(cu.getColumnName(1), cu.getString(1));
            json.put("Poster", getPosterDePeliculas(titulo));
            json.put(cu.getColumnName(2), cu.getString(2));
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateUsuarioUsername(String username, String usernameNuevo) {
        /*
        Pre: El username antiguo de un usuario y el nuevo
        Post: Devuelve true si se ha actualizado el nombre del usuario correctamente, false en caso contrario
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            String[] argumentos = new String[]{username};
            datos.put("NombreUsuario", usernameNuevo);
            db.update("Usuarios", datos, "NombreUsuario = ?", argumentos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUsuarioContrasena(String username, String contrasena) {
        /*
        Pre: El username de un usuario y su nueva contrase??a
        Post: Devuelve true si se ha actualizado correctamente, false en caso contrario
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            String[] argumentos = new String[]{username};
            datos.put("Contrasena", contrasena);
            db.update("Usuarios", datos, "NombreUsuario = ?", argumentos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUsuarioNombre(String username, String nombre) {
        /*
        Pre: El username de un usuario y su nuevo nombre
        Post: Devuelve true si se ha actualizado correctamente, false en caso contrario
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            String[] argumentos = new String[]{username};
            datos.put("Nombre", nombre);
            db.update("Usuarios", datos, "NombreUsuario = ?", argumentos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUsuarioApellido(String username, String apellido) {
        /*
        Pre: El username de un usuario y su nueva apellido
        Post: Devuelve true si se ha actualizado correctamente, false en caso contrario
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            String[] argumentos = new String[]{username};
            datos.put("Apellido", apellido);
            db.update("Usuarios", datos, "NombreUsuario = ?", argumentos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public JSONObject getInfoPeliculas() {
        /*
        Pre:
        Post: Devuelve toda la informaci??n de las pel??culas almacenadas en formato JSON
        */

        JSONObject json = new JSONObject();
        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Titulo", "PuntuacionMedia"};
        Cursor cu = db.query("Peliculas", columnas, null, null, null, null, null);

        String[] lTitulos = new String[cu.getCount()];
        float[] lPuntuacionMedia = new float[cu.getCount()];
        byte[][] lPosters = new byte[cu.getCount()][500];

        while (cu.moveToNext()) {
            lTitulos[cu.getPosition()] = cu.getString(0);
            lPuntuacionMedia[cu.getPosition()] = cu.getFloat(1);
            lPosters[cu.getPosition()] = getPosterDePeliculas(cu.getString(0));
        }
        try {
            json.put("lTitulos", lTitulos);
            json.put("lPosters", lPosters);
            json.put("lPuntuacionMedia", lPuntuacionMedia);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] getPosterDePeliculas(String titulo) {
        /*
        Pre: El t??tulo de una pel??cula
        Post: Devuelve el byte[] del poster de esa pel??cula
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Poster"};
        String[] param = new String[]{titulo};
        Cursor cu = db.query("Peliculas", columnas, "Titulo=?", param, null, null, null);
        if (cu instanceof SQLiteCursor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ((SQLiteCursor) cu).setWindow(new CursorWindow(null, 1024 * 1024 * 10));
        }
        cu.moveToNext();
        return cu.getBlob(0);
    }

    public String yaHaHechoReview(String usuario, String pelicula) {
        /*
        Pre: El username de un usuario y el t??tulo de la pel??cula a la que va a hacer la review
        Post: Si el usuario no ha hecho una review a esa pel??cula se devolver?? null, en caso contrario se devolver?? el texto de la review
        */

        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"Review"};
        String[] param = new String[]{usuario, pelicula};
        Cursor cu = db.query("Reviews", columnas, "Usuario=? AND Pelicula=?", param, null, null, null);
        if (!cu.moveToNext()) {
            return null;
        } else {
            return cu.getString(0);
        }
    }

    public boolean actualizarReview(String usuario, String pelicula, String review, float puntuacion) {
        /*
        Pre: El username de un usuario, el t??tulo de la pel??cula a la que a hecho la review, la propia review y la puntuaci??n que le ha dado
        Post: Devuelve true si se ha actualizado correctamente, false en caso contrario
        */

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues datos = new ContentValues();
            String[] argumentos = new String[]{usuario, pelicula};
            datos.put("Review", review);
            datos.put("Puntuacion", puntuacion);
            db.update("Reviews", datos, "Usuario=? AND Pelicula=?", argumentos);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
