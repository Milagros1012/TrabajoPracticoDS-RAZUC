package Entities.EstrategiasLectura.PDF;
import Entities.Alumno;
import Entities.Documento;
import Entities.Tipo;

import java.util.List;

public class PDF implements Tipo {
    private AdapterPDF adapterPDF;

    public PDF(AdapterPDF adapterPDF) {
        this.adapterPDF = adapterPDF;
    }

    @Override
    public List<Alumno> leer(Documento documento) {
        return adapterPDF.leer(documento);
    }
}
