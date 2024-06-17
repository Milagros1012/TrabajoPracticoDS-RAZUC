package Entities.Validaciones.Contraseña;

import Entities.Usuario;
import Entities.Validacion;

import java.io.*;

public class CompararContraseñasDefecto implements Validacion {

    @Override
    public boolean validarContraseña(Usuario usuario) {
        return !compararContraseñaDefecto(usuario);
    }

    private boolean compararContraseñaDefecto(Usuario usuario){
            String contraseñaUsuario = usuario.getPassword(); // Convertir a minúsculas para hacer la comparación más flexible

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ContraseñasDefecto");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            if (inputStream == null) {

                return false;
            }

            String linea;

            while ((linea = br.readLine()) != null) {
                String contraseñaDefecto = linea.trim().toLowerCase();

                if (contraseñaUsuario.equals(contraseñaDefecto)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }
}
