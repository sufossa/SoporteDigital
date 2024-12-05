package com.soporte.digital.model.service;

import com.soporte.digital.model.dto.TipoDocumentoDTO;
import com.soporte.digital.util.ResourcesUtils;
import java.util.ArrayList;

public class TipoDocumentoFacade {

    public TipoDocumentoFacade() {
    }

    public ArrayList<TipoDocumentoDTO> ListarTipoDocumentosCliente() {
        return ResourcesUtils.listTipoDocumentosCliente();
    }

    public ArrayList<TipoDocumentoDTO> ListarTipoDocumentosColaborador() {
        return ResourcesUtils.listTipoDocumentosColaborador();
    }

}
