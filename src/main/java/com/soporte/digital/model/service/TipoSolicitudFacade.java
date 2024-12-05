package com.soporte.digital.model.service;

import com.soporte.digital.model.dto.TipoSolicitudDTO;
import com.soporte.digital.util.ResourcesUtils;
import java.util.ArrayList;

public class TipoSolicitudFacade {
    
    public ArrayList<TipoSolicitudDTO> ListarTodos() {
        return ResourcesUtils.listTipoSolicitud();
    }
}
