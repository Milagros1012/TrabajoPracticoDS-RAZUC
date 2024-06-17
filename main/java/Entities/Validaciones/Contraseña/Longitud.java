package Entities.Validaciones.Contraseña;

import Entities.Usuario;
import Entities.Validacion;

public class Longitud implements Validacion {
    private int longitudMin=8;
    private int longitudMax=64;

    @Override
    public boolean validarContraseña(Usuario usuario) {
        return largoCorrecto(usuario);
    }

    private boolean largoCorrecto(Usuario usuario){
        usuario.getPassword();
        if(usuario.getPassword().length()<longitudMin){
            return false;
        }else if(usuario.getPassword().length()>longitudMax){
            return false;
        }else {
            return true;
        }
    }

}
