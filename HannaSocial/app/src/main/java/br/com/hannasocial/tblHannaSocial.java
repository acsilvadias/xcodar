package br.com.hannasocial;

import java.io.Serializable;

public class tblHannaSocial implements Serializable {
    public long id;
    public String apiid;
    public String imei;

    public tblHannaSocial( long id, String apiid,  String imei) {
        this.id = id;
        this.apiid = apiid;
        this.imei = imei;
    }

    public tblHannaSocial( String apiid,  String imei) {
        this(0, apiid, imei);
    }

    @Override
    public String toString(){
        return imei;
    }
}
