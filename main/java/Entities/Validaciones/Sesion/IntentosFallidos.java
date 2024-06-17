package Entities.Validaciones.Sesion;

import Entities.Usuario;
import Entities.ValidacionSesion;

public class IntentosFallidos implements ValidacionSesion {
    private int cantIntentosFallidos = 0;
    private long ultimoIntento = 0;
    private  long tiempoDeEspera;


    @Override
    public boolean usuarioContraseñaValidado(Usuario usuario) {
        return incrementarTiempo(usuario);
    }

    private boolean incrementarTiempo(Usuario usuario){
        long tiempoActual = System.currentTimeMillis();


        if (tiempoActual - ultimoIntento < getTiempoDeEspera()) {
            return false; // Si el tiempo de espera no ha pasado, negar acceso
        }

        boolean esValido;
        Autentificador autentificador = new Autentificador();

        if(autentificador.usuarioContraseñaValidado(usuario)){
            esValido = true;
        }else{
            esValido = false;
        }

        // Si el usuario es válido, resetear los intentos fallidos
        if (esValido) {
            cantIntentosFallidos = 0;
        } else {

            cantIntentosFallidos++;
            ultimoIntento = tiempoActual;
        }

        return esValido;
    }

    private long getTiempoDeEspera() {
        this.tiempoDeEspera=cantIntentosFallidos * 5000;
        return tiempoDeEspera;
    }

    public long tiempoRestanteEspera() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoRestante = getTiempoDeEspera() - (tiempoActual - ultimoIntento);
        return tiempoRestante > 0 ? tiempoRestante : 0;
    }

}
