package Entities;

import Converters.LocalDateAttributeConverter;
import db.EntityManagerHelper;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="usuarios")
public class Usuario {
   @Id
    @GeneratedValue
    private long idUsuario;

    @Column
    private String nombre;
    @Column
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Documento> archivos;

    @Column
    private Boolean esAdministrador;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column
    private LocalDate ultimoCambio;

    //constructor
    public Usuario() {
        this.nombre = nombre;
        this.password = password;
        this.esAdministrador = false;
        this.archivos = new ArrayList<>();
    }
//metodos


    public long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(Boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }


    public LocalDate getUltimoCambio() {
        return ultimoCambio;
    }

    public void setUltimoCambio(LocalDate ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }
    public void agregarDocumento(Documento documento) {
        archivos.add(documento);
    }


    public void persistirBD(){
          EntityManagerHelper.beginTransaction();
          EntityManagerHelper.persist(this);
          EntityManagerHelper.commit();
      }


}
