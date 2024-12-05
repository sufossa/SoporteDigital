package com.soporte.digital.util;

import com.soporte.digital.model.dto.EstadoDTO;
import com.soporte.digital.model.dto.TipoDocumentoDTO;
import com.soporte.digital.model.dto.TipoSolicitudDTO;
import com.soporte.digital.model.dto.TipoUsuarioDTO;
import java.util.ArrayList;

public class ResourcesUtils {

    public static ArrayList<TipoDocumentoDTO> listTipoDocumentosCliente() {
        ArrayList<TipoDocumentoDTO> lista = new ArrayList<>();
        lista.add(new TipoDocumentoDTO("0", "DTNDSR - Documento Tributario de No Domiciliado sin RUC"));
        lista.add(new TipoDocumentoDTO("1", "DNI - Documento Nacional de Identidad"));
        lista.add(new TipoDocumentoDTO("4", "CE - Carnet de Extranjería"));
        lista.add(new TipoDocumentoDTO("6", "RUC - Registro Único de Contribuyentes"));
        lista.add(new TipoDocumentoDTO("7", "PAS - Pasaporte"));
        lista.add(new TipoDocumentoDTO("A", "CDI - Cédula Diplomática de Identidad"));
        lista.add(new TipoDocumentoDTO("B", "DIPRND - DOC.IDENT.PAIS.RESIDENCIA-NO.D"));
        lista.add(new TipoDocumentoDTO("C", "TIN - Tax Identification Number - TIN – Doc Trib PP.NN"));
        lista.add(new TipoDocumentoDTO("D", "IN - Identification Number - IN – Doc Trib PP. JJ"));
        lista.add(new TipoDocumentoDTO("E", "TAM - Tarjeta Andina de Migración"));
        return lista;
    }

    public static ArrayList<TipoDocumentoDTO> listTipoDocumentosColaborador() {
        ArrayList<TipoDocumentoDTO> lista = new ArrayList<>();
        lista.add(new TipoDocumentoDTO("1", "DNI - Documento Nacional de Identidad"));
        lista.add(new TipoDocumentoDTO("4", "CE - Carnet de Extranjería"));
        lista.add(new TipoDocumentoDTO("7", "PAS - Pasaporte"));
        lista.add(new TipoDocumentoDTO("E", "TAM - Tarjeta Andina de Migración"));
        return lista;
    }

    public static ArrayList<TipoSolicitudDTO> listTipoSolicitud() {
        ArrayList<TipoSolicitudDTO> lista = new ArrayList<>();
        lista.add(new TipoSolicitudDTO("C", "Capacitación"));
        lista.add(new TipoSolicitudDTO("E", "Error de Software"));
        lista.add(new TipoSolicitudDTO("R", "Requerimiento"));
        return lista;
    }

    public static ArrayList<EstadoDTO> listEstadoSolicitud() {
        ArrayList<EstadoDTO> lista = new ArrayList<>();
        lista.add(new EstadoDTO("P", "Pendiente"));
        lista.add(new EstadoDTO("A", "Asignada"));
        lista.add(new EstadoDTO("R", "Proceso"));
        lista.add(new EstadoDTO("T", "Atendida"));
        return lista;
    }

    public static ArrayList<TipoUsuarioDTO> listTipoUsuario() {
        ArrayList<TipoUsuarioDTO> lista = new ArrayList<>();
        lista.add(new TipoUsuarioDTO("C", "Colaborador del Cliente"));
        lista.add(new TipoUsuarioDTO("E", "Colaborador de la Empresa"));
        lista.add(new TipoUsuarioDTO("S", "Gestor de Soporte"));
        return lista;
    }
}
