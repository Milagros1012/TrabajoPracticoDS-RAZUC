package Entities.EstrategiasLectura.CSV;

import Entities.Alumno;
import Entities.Documento;
import Entities.Tipo;

import java.util.List;

public class CSV implements Tipo {
    private AdapterCSV adapterCSV;

    public CSV(AdapterCSV adapterCSV) {
        this.adapterCSV = adapterCSV;
    }


    @Override
    public List<Alumno> leer(Documento documento) {
        return adapterCSV.leer(documento);
    }
}
