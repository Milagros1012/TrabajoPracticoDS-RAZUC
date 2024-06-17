package Entities.EstrategiasLectura.CSV;
import Entities.Alumno;
import Entities.Documento;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AdapterCSV {
    public List<Alumno> leer(Documento documento) {
        List<Alumno> contenido = new ArrayList<>();
        try {
            Reader reader = new FileReader(documento.getRuta());
            CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
            CSVParser parser = new CSVParser(reader, format);


            for (CSVRecord record : parser) {
                // Suponiendo que las columnas son: nombre, apellido, nombreMateria, nota
                String nombre = record.get(0);
                String apellido = record.get(1);
                String nombreMateria = record.get(2);
                double nota = Double.parseDouble(record.get(3));

                Alumno alumno = new Alumno();
                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setNombreMateria(nombreMateria);
                alumno.setNota(nota);

                contenido.add(alumno);
            }

            parser.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
    }
}
