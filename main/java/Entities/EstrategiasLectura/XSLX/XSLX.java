package Entities.EstrategiasLectura.XSLX;
import Entities.Alumno;
import Entities.Documento;
import Entities.Tipo;

import java.util.List;

public class XSLX implements Tipo {
    private AdapterXSLX adapterXSLX;

    public XSLX(AdapterXSLX adapterXSLX) {
        this.adapterXSLX = adapterXSLX;
    }

    @Override
    public List<Alumno> leer(Documento documento) {
        return adapterXSLX.leer(documento);
    }
}
