package com.example.pruebaandroid;

public class UsuarioGlobal {
    private static UsuarioGlobal instance;
    private String usuarioGlobal;
    private String correoGlobal;
    private String contraGlobal;

    private UsuarioGlobal(){ }

    public static UsuarioGlobal getInstance() {
        if (instance == null) {
            instance = new UsuarioGlobal();
        }
        return instance;
    }

    public String getUsuarioGlobal() {
        return usuarioGlobal;
    }

    public String getCorreoGlobal(){
        return correoGlobal;
    }

    public String getContraGlobal(){
        return contraGlobal;
    }

    public void setUsuarioGlobal(String usuario) {
        this.usuarioGlobal = usuario;
    }

    public void setCorreoGlobal(String correo) {
        this.correoGlobal = correo;
    }

    public void setContraGlobal(String contra){
        this.contraGlobal = contra;
    }
}
