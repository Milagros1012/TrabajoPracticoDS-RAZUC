package Entities.EstrategiasLectura.PDF;
import Entities.Alumno;
import Entities.Documento;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.util.*;


public class AdapterPDF {

    public List<Alumno> leer(Documento documento) {
        List<Alumno> contenido = new ArrayList<>();
        try {
            File file = new File(documento.getRuta());
            PDDocument pdfDocument = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();

            String texto = stripper.getText(pdfDocument);

            String[] lineasSeparadas = texto.split("\\r?\\n");

            boolean primeraLinea = true;
            for (String linea : lineasSeparadas) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split(",");
                if (campos.length >= 4) {
                    String nombre = campos[0];
                    String apellido = campos[1];
                    String nombreMateria = campos[2];
                    double nota;
                    nota = Double.parseDouble(campos[3]);

                    Alumno alumno = new Alumno();

                    alumno.setNombre(nombre);
                    alumno.setApellido(apellido);
                    alumno.setNombreMateria(nombreMateria);
                    alumno.setNota(nota);

                    contenido.add(alumno);
                }
            }
            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
    }
}