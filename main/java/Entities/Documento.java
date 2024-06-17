package Entities;

import db.EntityManagerHelper;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name ="documentos")
public class Documento {
    @Id
    @GeneratedValue
    private int idDocumento;

    @Column
     private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)//,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "usuario_id", nullable = false, referencedColumnName = "idUsuario")
     private Usuario usuario;

    @Transient
     private List<Alumno> contenido;

    @OneToMany(mappedBy = "documento")
    private List<Alumno> alumnosElegidos;

    @Transient
    private Tipo tipo;
    @Transient
    private String ruta;
    @Transient
     private int cantidadAlumnosPersistir =3;

    public Documento() {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setContenido(List<Alumno> contenido) {
        this.contenido = contenido;
    }

    public List<Alumno> getContenido() {
        return contenido;
    }

    public List<Alumno> getAlumnosElegidos() {
        return alumnosElegidos;
    }

    public void setAlumnosElegidos(List<Alumno> alumnosElegidos) {
        this.alumnosElegidos = alumnosElegidos;
    }

    public boolean cantidadCorrectaFilasElegidas(List<Alumno> alumnosElegidos){

        if (alumnosElegidos.size() <= cantidadAlumnosPersistir) {
            return true;
        } else  return false;
    }

    public void persistirBD(){

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(this);
        EntityManagerHelper.commit();
    }
}

