package visuales;

import Entities.Usuario;
import Entities.Validador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Registrarse extends JFrame {
    private JTextField usuarioRegistro;
    private JButton registrarseButton;
    private JPasswordField contra;
    private JButton mostrarContraseñaRegistroButton;
    private JPanel panelRegistro;
    private JCheckBox esAdministradorCheckBox;

    public Registrarse() {
        super("Registrarse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setContentPane(panelRegistro);
        pack();


        mostrarContraseñaRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contra.setEchoChar((char) 0);

            }
        });


        Validador validador = new Validador();
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();
                usuario.setNombre(usuarioRegistro.getText());
                usuario.setPassword(contra.getText());
                usuario.setEsAdministrador(esAdministradorCheckBox.isSelected());
                usuario.setUltimoCambio(LocalDate.now());

                String validado = validador.contraseñaValidada(usuario);

                if (validado==null) {
                    usuario.persistirBD();
                    JOptionPane.showMessageDialog(Registrarse.this, "Se registró correctamente");
                    abrirVentanaPrincipal();
                }else {
                    switch (validado){
                        case "Longitud":
                            JOptionPane.showMessageDialog(Registrarse.this, "La contraseña debe tener entre 8 y 64 carácteres");
                            break;

                        case "CompararContraseñasDefecto":
                            JOptionPane.showMessageDialog(Registrarse.this, "La contraseña no puede ser una por defecto");
                            break;


                        case "CompararPeorContraseña":
                            JOptionPane.showMessageDialog(Registrarse.this, "La contrasñea es débil. Por favor, elija otra");
                            break;


                    }

                }


            }
        });
    }

    private void abrirVentanaPrincipal() {
        InicioSesion ventana = new InicioSesion();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

    }
}
