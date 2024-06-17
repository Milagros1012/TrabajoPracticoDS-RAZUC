package visuales;

import Entities.Usuario;
import Entities.Validador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesion extends JFrame {
    private JTextField Txtusuario;
    private JPasswordField txtPasword;
    private JButton iniciarSesionButton;
    private JPanel panel;
    private JButton mostarContraseñaButton;
    private JButton Registrarse;

    public InicioSesion() {
        super("Inicio Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);


        Validador validador = new Validador();
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();
                usuario.setNombre(Txtusuario.getText());
                usuario.setPassword(txtPasword.getText());

                 boolean validado = validador.usuarioContraseñaValidado(usuario);

                    if (validado) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {

                                abrirVentanaPrincipal( validador.obtenerUsuario(usuario));
                            }
                        });
                    }else {long tiempoEspera = validador.getTiempoRestanteEspera();
                        if (tiempoEspera > 0) {
                            JOptionPane.showMessageDialog(InicioSesion.this, "Usuario o contraseña incorrectos.Por favor espere " + tiempoEspera / 1000 + " segundos antes de intentar de nuevo.", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                        }
                    }

            }
        });


        mostarContraseñaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPasword.setEchoChar((char) 0);

            }
        });

        Registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        abrirVentanaRegistro();
                    }
                });

            }
        });
    }

    private void abrirVentanaRegistro() {
        Registrarse registrarse = new Registrarse();
        registrarse.setVisible(true);
        registrarse.setLocationRelativeTo(null);
    }

    private void abrirVentanaPrincipal(Usuario usuario) {
        VisualizarArchivo ventana = new VisualizarArchivo(usuario);
        ventana.setSize(800, 700);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

    }

    public void initGUI() {
        setVisible(true);
    }

}
