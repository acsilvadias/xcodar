package br.com.hannasocial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HannaSocialRepositorio {

    private HannaSQLHelper helper;


    public HannaSocialRepositorio(Context ctx) {
        helper = new HannaSQLHelper(ctx);

    }

    private long inserir(tblHannaSocial tblhannasocial){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HannaSQLHelper.COLUNA_APIID, tblhannasocial.apiid);
        cv.put(HannaSQLHelper.COLUNA_IMEI, tblhannasocial.imei);

        long id = db.insert(HannaSQLHelper.TABELA_HANNASOCIAL,null , cv);
        if (id != -1)
        {
            tblhannasocial.id = id;
        }

        db.close();
        return id;
    }

    private long atualizar(tblHannaSocial tblhannasocial){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HannaSQLHelper.COLUNA_ID, tblhannasocial.id);
        cv.put(HannaSQLHelper.COLUNA_APIID, tblhannasocial.apiid);
        cv.put(HannaSQLHelper.COLUNA_IMEI, tblhannasocial.imei);
        cv.put(HannaSQLHelper.COLUNA_IMEI, tblhannasocial.imei);

        int linhasAfetadas = db.update(HannaSQLHelper.TABELA_HANNASOCIAL, cv, HannaSQLHelper.COLUNA_ID +" = ?",new String[]{String.valueOf(tblhannasocial.id)});

        db.close();
        return linhasAfetadas;
    }

    public void salvar(tblHannaSocial tblhannahocial ){
        if(tblhannahocial.id ==0)
        {
            inserir(tblhannahocial);
        }else{
            atualizar(tblhannahocial);
        }
    }

    public int excluir(tblHannaSocial tblhannahocial){
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(HannaSQLHelper.TABELA_HANNASOCIAL,
                HannaSQLHelper.COLUNA_ID + " + ?",
                new String[]{String.valueOf(tblhannahocial.id)});
        db.close();
        return linhasAfetadas;

    }
}
