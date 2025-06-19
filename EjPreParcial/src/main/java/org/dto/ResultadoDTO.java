package org.dto;

public class ResultadoDTO {

    private boolean succeso;
    private String mensaje;
    public ResultadoDTO(){};

    public ResultadoDTO(boolean succedio, String msj){
        this.succeso=succedio;
        this.mensaje=msj;
    }
    public boolean getSucceso() {
        return succeso;
    }

    public void setSucceso(boolean suc) {
        this.succeso = suc;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
