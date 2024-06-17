package Entities.Validaciones.Contraseña;

import Entities.Usuario;
import Entities.Validacion;

import java.text.Normalizer;

public class Complejidad implements Validacion {
    @Override
    public boolean validarContraseña(Usuario usuario) {
        eliminarMultiplesEspacios(usuario);
        normalizarUnicode(usuario);
        return true;
    }

    private void eliminarMultiplesEspacios(Usuario usuario){
        String contraseña = usuario.getPassword();
        contraseña = contraseña.replaceAll("\\s+", " ").trim();
        usuario.setPassword(contraseña);
    }

    private void normalizarUnicode(Usuario usuario){
        String contraseña = usuario.getPassword();
        contraseña = Normalizer.normalize(contraseña, Normalizer.Form.NFD);
        contraseña = contraseña.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        usuario.setPassword(contraseña);

    }
}
