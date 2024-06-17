package visuales;

import Entities.*;
import Entities.EstrategiasLectura.PDF.*;
import Entities.EstrategiasLectura.CSV.*;
import Entities.EstrategiasLectura.XSLX.*;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VisualizarArchivo extends JFrame {

    private JPanel panelArch;
    private JButton subirDocumentoButton;
    private JButton seleccionarButton;
    private JTextField nombreDocumento;
    private JButton guardarFilasButton;
    private JComboBox selectTipo;
    private JLabel NombreUsuario;
    private JLabel Usuario;
    private JList seleccionarFilas;
    private JTable mostrarContenido;
    private DefaultTableModel tableModel;

    private List<Alumno> alumnos;
    private  Documento documento = new Documento();

    private static String selectedFilePath;

    public VisualizarArchivo(Usuario usuario) {

        super("Visualizar Archivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setContentPane(panelArch);

        selectTipo.addItem("PDF");
        selectTipo.addItem("CSV");
        selectTipo.addItem("XLSX");

        NombreUsuario.setText(usuario.getNombre());


        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Filtrar solo archivos PDF, XLSX y CSV
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(null, "pdf", "xls", "xlsx", "csv"));

                int result = fileChooser.showOpenDialog(panelArch);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFilePath = selectedFile.getAbsolutePath();
                    seleccionarButton.setText(selectedFilePath);

                    nombreDocumento.setText(selectedFile.getName());

                    int lastDotIndex = selectedFilePath.lastIndexOf('.');
                    String extension = selectedFilePath.substring(lastDotIndex + 1);

                    switch (extension) {
                        case "pdf":
                            selectTipo.setSelectedItem("PDF");
                                    //setSelectedIndex(0);
                            break;
                        case "csv":
                            selectTipo.setSelectedItem("CSV");
                                    //.setSelectedIndex(1);
                            break;
                        case "xlsx":
                            selectTipo.setSelectedItem("XLSX");
                                    //.setSelectedIndex(2);
                            break;
                    }

                }
            }
        });

        subirDocumentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                documento.setNombre(nombreDocumento.getText());
                documento.setUsuario(usuario);
                documento.setRuta(selectedFilePath);

                //setee el tipo de forma correcta
                String tipo = selectTipo.getSelectedItem().toString();

                switch (tipo) {
                    case "PDF":
                        AdapterPDF adapterPDF = new AdapterPDF();
                        Tipo pdf = new PDF(adapterPDF);
                        documento.setTipo(pdf);
                        break;

                        case "XLSX":
                        AdapterXSLX adapterXSLX =new AdapterXSLX();
                        Tipo xlsx = new XSLX(adapterXSLX);
                        documento.setTipo(xlsx);

                        break;

                        case "CSV":
                        AdapterCSV adapterCSV = new AdapterCSV();
                        Tipo csv = new CSV(adapterCSV);
                        documento.setTipo(csv);
                        break;
                }

                //setea el contenido
                alumnos = documento.getTipo().leer(documento);
                documento.setContenido(alumnos);

                usuario.agregarDocumento(documento);

                tableModel = new DefaultTableModel(new String[]{"Nombre", "Apellido", "Nombre Materia", "Nota"}, 10);

                mostrarContenido.setModel(tableModel);

                tableModel.setRowCount(0); // Limpiar la tabla existente
                for (Alumno alumno : alumnos) {
                    Object[] fila = {alumno.getNombre(), alumno.getApellido(), alumno.getNombreMateria(), alumno.getNota()};
                    tableModel.addRow(fila);
                }
            }
        });

        DefaultListModel<Object> listModel = new DefaultListModel<>();
        seleccionarFilas.setModel(listModel);

        List<Alumno> alumnosSeleccionados = new ArrayList<>();
        alumnosSeleccionados.clear();

        mostrarContenido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = mostrarContenido.rowAtPoint(e.getPoint()); // Obtener la fila bajo el clic

                Alumno selectedAlumno = alumnos.get(row);
                if (row != -1 && !alumnosSeleccionados.contains(selectedAlumno)) {
                    alumnosSeleccionados.add(selectedAlumno);// Agregar el alumno a la lista de seleccionados

                    String mostrar = selectedAlumno.getNombre() + " "+selectedAlumno.getApellido();

                    listModel.addElement(mostrar); // Añadir el nombre a la lista del JList

                    mostrarContenido.setSelectionBackground(Color.GRAY);
                    // Refrescar la tabla para aplicar el cambio visualmente
                    mostrarContenido.repaint();
                }
            }
        });



        guardarFilasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (documento != null && alumnosSeleccionados != null) {
                    if (documento.cantidadCorrectaFilasElegidas(alumnosSeleccionados)) {
                        documento.setAlumnosElegidos(alumnosSeleccionados);


                        documento.setUsuario(usuario);
                        documento.persistirBD();

                        for(Alumno alumno : alumnosSeleccionados) {
                            alumno.setDocumento(documento);
                            alumno.persistirBD();
                        }


                    } else {
                        JOptionPane.showMessageDialog(panelArch, "Debe eliminar una fila para persistir.(Puede hacerlo dandole doble click a la fila que desee eliminar)", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }


            }
        });
        seleccionarFilas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    int index = seleccionarFilas.locationToIndex(e.getPoint());
                    if (index != -1) {
                        listModel.remove(index);
                        alumnosSeleccionados.remove(index);
                    }
                }

            }
        });
    };
}








