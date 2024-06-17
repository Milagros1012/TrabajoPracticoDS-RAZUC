package org.example;

import org.apache.pdfbox.text.PDFTextStripper;
import visuales.InicioSesion;
import javax.swing.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InicioSesion inicioSesion = new InicioSesion();
                inicioSesion.initGUI();;
            }
        });

    }


    }
