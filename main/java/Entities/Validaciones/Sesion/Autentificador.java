package Entities.Validaciones.Sesion;

import Entities.Usuario;
import Entities.ValidacionSesion;
import db.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class Autentificador implements ValidacionSesion {


    @Override
    public boolean usuarioContraseñaValidado(Usuario usuario) {
        return verificarUsuarioContraseña(usuario);
    }

    private  boolean verificarUsuarioContraseña(Usuario usuario) {
        String nombreUsuario = usuario.getNombre();
        String contraseña = usuario.getPassword();


        EntityManager em = EntityManagerHelper.getEntityManager();
        String jpql = "SELECT u FROM Usuario u WHERE u.nombre = :nombreUsuario AND u.password = :contraseña";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("nombreUsuario", nombreUsuario);
        query.setParameter("contraseña", contraseña);

        try {
            Usuario usuarioBD = query.getSingleResult(); // Intenta obtener un resultado único
            return true;
        } catch (NoResultException e) {
            // Si no se encuentra ningún resultado, devuelve false
            return false;
        } finally {
            em.close();
        }
    }




}
