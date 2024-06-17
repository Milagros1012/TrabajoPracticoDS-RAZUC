package Entities.Validaciones.Contraseña;

import Entities.Usuario;
import Entities.Validacion;

import java.time.Duration;
import java.time.LocalDate;

public class Rotacion implements Validacion {

    private static final long PASSWORD_EXPIRY_DAYS = 90;

    @Override
    public boolean validarContraseña(Usuario usuario) {
        return cambioContraseña(usuario);
    }

    private boolean cambioContraseña(Usuario usuario) {
        LocalDate ultimoCambio = usuario.getUltimoCambio();
        LocalDate ahora = LocalDate.now();

        Duration duracion = Duration.between(ultimoCambio, ahora);
        long daysBetween = duracion.toDays();

        if (daysBetween >= PASSWORD_EXPIRY_DAYS) {
            return false;
        }else {
            return true;
        }
    }
}
