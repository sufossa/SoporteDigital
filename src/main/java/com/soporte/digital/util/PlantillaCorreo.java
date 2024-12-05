package com.soporte.digital.util;

import com.soporte.digital.model.dto.SolicitudDTO;

public class PlantillaCorreo {

    public static String FinalizarSolicitud(SolicitudDTO solicitud, String nombreCliente) {

        String cuerpo = "<html lang='en'>\n"
                + "<head>\n"
                + "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
                + "</head>\n"
                + "<body>\n"
                + "    <center>\n"
                + "        <div style='text-align: left; color:black; width: 670px; box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2); transition: 0.3s;'>\n"
                + "            <div style='padding: 20px;'>\n"
                + "                <p>Hola, @@_NOMBRES_COMPLETOS_@@:</p>\n"
                + "                <p>Nos complace informarte que tu solicitud con ID <strong>@@_ID_SOLICITUD_@@</strong> ha sido finalizada con éxito.</p>\n"
                + "                <p>Puedes verificar el estado actualizado directamente en la aplicación.</p>\n"
                + "                <p style='text-align: justify;'>Si tienes alguna consulta adicional, no dudes en comunicarte con nuestro equipo de soporte.</p>\n"
                + "                <hr style='border: none; height: 1px; background-color: #ccc;'>\n"
                + "                <p style='font-size: 14px; color: gray;'>Este mensaje fue generado automáticamente. Por favor, no respondas a este correo.</p>\n"
                + "                <p style='font-size: 14px; color: gray;'>Gracias por confiar en nosotros.</p>\n"
                + "                <p style='font-weight: bold;'>Equipo del Soporte Digital</p>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </center>\n"
                + "</body>\n"
                + "</html>";

        cuerpo = cuerpo.replaceAll("@@_NOMBRES_COMPLETOS_@@", nombreCliente);
        cuerpo = cuerpo.replaceAll("@@_ID_SOLICITUD_@@", String.valueOf(solicitud.getIdSolicitud()));

        return cuerpo;
    }
}
