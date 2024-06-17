package Entities;

import Entities.Validaciones.Contraseña.CompararContraseñasDefecto;
import Entities.Validaciones.Contraseña.CompararPeorContraseña;
import Entities.Validaciones.Contraseña.Complejidad;
import Entities.Validaciones.Contraseña.Longitud;
import Entities.Validaciones.Sesion.Autentificador;
import Entities.Validaciones.Sesion.IntentosFallidos;
import db.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Validador {
    private List<Validacion> validaciones;
    private List<ValidacionSesion> validacionSesion;
    private IntentosFallidos intentosFallidos; //no encontre a nivel codigo otra manera de llamarlo que no sea desde Validador


    public Validador() {
        validacionSesion = new ArrayList<>();
        Autentificador autentificador = new Autentificador();
        intentosFallidos = new IntentosFallidos();

        validacionSesion.add(autentificador);
        validacionSesion.add(intentosFallidos);


        validaciones = new ArrayList<>();
        Longitud longitud = new Longitud();
        Complejidad complejidad = new Complejidad();
        CompararContraseñasDefecto defecto = new CompararContraseñasDefecto();
        CompararPeorContraseña peor = new CompararPeorContraseña();

        validaciones.add(longitud);
        validaciones.add(complejidad);
        validaciones.add(defecto);
        validaciones.add(peor);
    }

    //metodos
    public List<ValidacionSesion> getValidacionSesion() {
        return validacionSesion;
    }

    public void setValidacionSesion(List<ValidacionSesion> validacionSesion) {

        this.validacionSesion = validacionSesion;
    }

    public List<Validacion> getValidaciones() {
        return validaciones;
    }


    public void setValidaciones(List<Validacion> validaciones) {
        this.validaciones = validaciones;
    }

    public boolean usuarioContraseñaValidado(Usuario usuario) {
        boolean valido = false;

        for (ValidacionSesion validacionSesion : this.validacionSesion) {
            valido = validacionSesion.usuarioContraseñaValidado(usuario);
            if (valido) {
                break;
            }
        }
        return valido;
    }

    public long getTiempoRestanteEspera() {
        return intentosFallidos.tiempoRestanteEspera();
    }

    public String contraseñaValidada(Usuario usuario) {
        for (Validacion validacion : this.validaciones) {
            if (!validacion.validarContraseña(usuario)) {
                String claseFallo = validacion.getClass().getSimpleName();
                return claseFallo; // Si alguna validación falla, devolvera cual
            }
        }
        return null;

    }

    public Usuario obtenerUsuario(Usuario usuario) {

        if (usuarioContraseñaValidado(usuario)) {

            String nombreUsuario = usuario.getNombre();
            String contraseña = usuario.getPassword();

            EntityManager em = EntityManagerHelper.getEntityManager();
            String jpql = "SELECT u FROM Usuario u WHERE u.nombre = :nombreUsuario AND u.password = :contraseña";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("nombreUsuario", nombreUsuario);
            query.setParameter("contraseña", contraseña);

                Usuario usuarioBD = query.getSingleResult(); // Intenta obtener un resultado único
            return usuarioBD;
        }else{
            return null;
        }
    }
}

