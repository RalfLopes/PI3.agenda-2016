/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.blacksytem.agenda;

import java.sql.Date;

/**
 *
 * @author temp.cas
 */
public class Contato {
    
    private String NM_PESSOA;
    private Date DT_NASCIMENTO;
    private String TELEFONE;
    private String VL_EMAIL;

    public Contato(String NM_PESSOA){
        this.NM_PESSOA = NM_PESSOA;
    }

    public Contato(String NM_PESSOA, Date DT_NASCIMENTO, String TELEFONE, String VL_EMAIL) {
        this.NM_PESSOA = NM_PESSOA;
        this.DT_NASCIMENTO = DT_NASCIMENTO;
        this.TELEFONE = TELEFONE;
        this.VL_EMAIL = VL_EMAIL;
    }

    public String getNM_PESSOA() {
        return NM_PESSOA;
    }

    public void setNM_PESSOA(String NM_PESSOA) {
        this.NM_PESSOA = NM_PESSOA;
    }

    public Date getDT_NASCIMENTO() {
        return DT_NASCIMENTO;
    }

    public void setDT_NASCIMENTO(Date DT_NASCIMENTO) {
        this.DT_NASCIMENTO = DT_NASCIMENTO;
    }

    public String getTELEFONE() {
        return TELEFONE;
    }

    public void setTELEFONE(String TELEFONE) {
        this.TELEFONE = TELEFONE;
    }

    public String getVL_EMAIL() {
        return VL_EMAIL;
    }

    public void setVL_EMAIL(String VL_EMAIL) {
        this.VL_EMAIL = VL_EMAIL;
    }

    
    
   
    
    
    
}
