package Entities;

import db.EntityManagerHelper;

import javax.persistence.*;
import java.awt.*;

@Entity
@Table(name ="alumnos")
public class Alumno {
    @Id
    @GeneratedValue
    private int idAlumno;

    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String nombreMateria;
    @Column
    private double nota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documento_id",referencedColumnName = "idDocumento")
    private Documento documento;

    //constructor
    public Alumno() {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreMateria = nombreMateria;
        this.nota = nota;
    }


//metodos

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public void persistirBD(){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(this);
        EntityManagerHelper.commit();
    }
}
