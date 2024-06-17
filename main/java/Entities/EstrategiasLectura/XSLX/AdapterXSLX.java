package Entities.EstrategiasLectura.XSLX;

import Entities.Alumno;
import Entities.Documento;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdapterXSLX {

    public List<Alumno> leer(Documento documento) {
        List<Alumno> contenido = new ArrayList<>();

        File archivo = new File(documento.getRuta());

        try {
           InputStream is = new FileInputStream(archivo);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            Iterator<Row> filas = sheet.iterator();
            Iterator<Cell> columnas=null;

            // Saltar la primera fila (encabezados)
            if (filas.hasNext()) {
                filas.next();
            }

            Row filaActual=null;
            Cell columnaActual=null;

            while (filas.hasNext()) {
                filaActual=filas.next();
                columnas=filaActual.cellIterator();
                Alumno alumno = new Alumno();

                while (columnas.hasNext()) {
                Cell cell = columnas.next();

                int columnIndex = cell.getColumnIndex(); // Obtener el índice de la columna (opcional)

                switch (columnIndex) {
                    case 0: // Columna del nombre
                        alumno.setNombre(cell.getStringCellValue());
                        break;
                    case 1: // Columna del apellido
                        alumno.setApellido(cell.getStringCellValue());
                        break;
                    case 2: // Columna del nombre de la materia
                        alumno.setNombreMateria(cell.getStringCellValue());
                        break;
                    case 3: // Columna de la nota
                        alumno.setNota(cell.getNumericCellValue());
                        break;
                    default:
                        // Manejo de columnas adicionales si es necesario
                        break;
                }
            }

                contenido.add(alumno);
            }

            is.close();
            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
    }
}
