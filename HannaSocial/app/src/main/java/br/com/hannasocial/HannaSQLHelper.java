package br.com.hannasocial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HannaSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbHannaSocial";
    private static final int VERSAO_BANCO = 1;
    private String queryCreateTable = "CREATE TABLE tabela ("
                                        +" colina_id INTEGER PRIMARY KEY AUTOIMCREMENT,"
                                        +" imei TEXT NULL, "
                                        +" apiid TEXT NULL, "
                                        +" registrationid TEXT NULL )";

    public static final String TABELA_HANNASOCIAL = "tbl_HannaSocial";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_APIID = "apiid";
    public static final String COLUNA_IMEI = "imei";
    public static final String COLUNA_REGISTRATIONID = "registrationid";


    public HannaSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTable
                .replaceAll("tabela",TABELA_HANNASOCIAL)
                .replaceAll("colina_id",COLUNA_ID)
                .replaceAll("apiid",COLUNA_APIID)
                .replaceAll("imei",COLUNA_IMEI )
                .replaceAll("imei",COLUNA_REGISTRATIONID ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
