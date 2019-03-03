package inventarioreal.com.inventarioreal_admin.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

/**
 * Created by jamper91 on 27/01/2015.
 */

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static String DB_PATH = "";
    private static String DB_NAME = "inventarioReal.db";
    private SQLiteDatabase db_g=null;
    private boolean is_in_transaction=false;
    private static DataBase instance=null;
    private static String TAG= "Database";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    public DataBase(Context context)
    {
        super( context , DB_NAME , null , 1);
        this.context = context;


    }

    public static DataBase getInstance(Context context) {
        if(instance==null)
            return new DataBase(context);
        return instance;
    }

    public void init_transaction(){
        is_in_transaction=true;
        if (this.db_g==null) {
            db_g= getWritableDatabase();
        }
        db_g.beginTransaction();
    }

    public void commit() {
        this.db_g.setTransactionSuccessful();
        is_in_transaction=false;
        this.db_g.close();
    }
    public void rollback(){
        this.db_g.endTransaction();
        is_in_transaction=false;
        this.db_g.close();
    }
    private SQLiteDatabase get_db(){
        if(is_in_transaction){
            return this.db_g;
        }else{
            return getWritableDatabase();
        }
    }

    private void close_db(SQLiteDatabase db){
        if(!is_in_transaction){
            db.close();
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        ///holas
        this.generarSql(db);
    }

    protected void generarSql(SQLiteDatabase db)
    {
        LinkedList<String> columns=new LinkedList<>();
        columns.add(Constants.state+ " INT NULL");
        columns.add(Constants.epc+ " varchar(45) NULL");
        columns.add(Constants.companias_id+ " INT NOT NULL");
        columns.add(Constants.createdAt+ " varchar(45) NULL");
        columns.add(Constants.updatedAt+ " varchar(45) NULL");
        db.execSQL(this.crear_tabla(Constants.table_epcs, columns));


        columns=new LinkedList<>();
        columns.add(Constants.ean+ " VARCHAR(45) NULL");
        columns.add(Constants.plu+ " VARCHAR(45) NULL");
        columns.add(Constants.plu2+ " VARCHAR(45) NULL");
        columns.add(Constants.plu3+ " VARCHAR(45) NULL");
        columns.add(Constants.marca+ " VARCHAR(45) NULL");
        columns.add(Constants.genero+ " VARCHAR(45) NULL");
        columns.add(Constants.color+ " VARCHAR(45) NULL");
        columns.add(Constants.talla+ " VARCHAR(45) NULL");
        columns.add(Constants.categoria+ " VARCHAR(45)");
        columns.add(Constants.descripcion+ " TEXT NULL");
        columns.add(Constants.cantidad+ " MEDIUMTEXT NULL");
        columns.add(Constants.imagen+ " TEXT NULL");
        columns.add(Constants.createdAt+ " DATETIME NULL");
        columns.add(Constants.precio_costo+ " DOUBLE NULL");
        columns.add(Constants.precio_venta+ " DOUBLE NULL");
        columns.add(Constants.companias_id+ " INT NOT NULL");
        columns.add(Constants.updatedAt+ " DATETIME NULL");
        db.execSQL(this.crear_tabla(Constants.table_productos, columns));

        columns=new LinkedList<>();
        columns.add(Constants.name+ " VARCHAR NULL");
        columns.add(Constants.locales_id+ " INT NOT NULL");
        columns.add(Constants.createdAt+ " DATETIME NULL");
        columns.add(Constants.updatedAt+ " DATETIME NULL");
        db.execSQL(this.crear_tabla(Constants.table_zonas, columns));

        columns=new LinkedList<>();
        columns.add(Constants.productos_id +" productos_id INT(11) NOT NULL");
        columns.add(Constants.zonas_id +" zonas_id INT(11) NOT NULL");
        columns.add(Constants.fecha_ingreso +" fecha_ingreso DATETIME NULL DEFAULT NULL");
        columns.add(Constants.fecha_venta +" fecha_venta DATETIME NULL DEFAULT NULL");
        columns.add(Constants.fecha_devolucion +" fecha_devolucion DATETIME NULL DEFAULT NULL");
        columns.add(Constants.devolucion_observaciones +" devolucion_observaciones TEXT NULL");
        columns.add(Constants.devoluciones_id +" devoluciones_id INT(11) NOT NULL");
        columns.add(Constants.logs_usuarios +" logs_usuarios TEXT NULL");
        columns.add(Constants.ventas_id +" ventas_id INT(11) NOT NULL");
        columns.add(Constants.epcs_id +" epcs_id INT(11) NOT NULL");
        columns.add(Constants.createdAt +" createdAt DATETIME NULL DEFAULT NULL");
        columns.add(Constants.updatedAt +" updatedAt DATETIME NULL DEFAULT NULL");
        db.execSQL(this.crear_tabla(Constants.table_productos_zonas, columns));




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borro la base de datos
        Log.i("onUpgrade", "Actualizando la base de datos de "+oldVersion+ "  a "+newVersion);
//        context.deleteDatabase(DB_NAME);
    }




    //region Funciones Sql

    /**
     * Funcion para generar el sql para crear una tabla
     * @param table_name
     * @param columnas
     * @return
     */
    private String crear_tabla(String table_name, LinkedList<String> columnas)
    {
        //Agrego el campo id
        columnas.addFirst("id INTEGER PRIMARY KEY AUTOINCREMENT");
        String sql="Create table %table_name (%columas);";
        sql=sql.replace("%table_name",table_name);
        String col=columnas.toString();
        col= col.substring(1,col.length()-1);
        sql=sql.replace("%columas",col);
        return sql;

    }

    /**
     * Se encarga de ingresar un registro dentro de una tabla
     * @param table_name Nombre de la tabla donde se va a insertar
     * @param columnas Lista con las columnas a agregar, la lista debe contener
     *                 un array de string donde el primer elemento es el nombre de la columna
     *                 y el segundo es el valor a ingresar, ejemplo: [name][Julian]
     * @return
     */
    public String add(String table_name, LinkedList<String[]> columnas)
    {
        String sql="";
        sql="INSERT INTO %table (%campos) values(%valores)";
        String campos="", valores="";
        for (int i = 0; i < columnas.size(); i++) {
            String columna[]=columnas.get(i);
            campos+=columna[0]+",";
            valores+=columna[1]+",";
        }
        campos=campos.substring(0,campos.length() - 1);
        valores=valores.substring(0, valores.length() - 1);

        sql=sql.replace("%table",table_name);
        sql=sql.replace("%campos",campos);
        sql=sql.replace("%valores", valores);

        this.execQuery(null,sql);
        return sql;
    }

    public long add(String table_name, ContentValues fields)
    {
        try {
            SQLiteDatabase db=null;
            db = get_db();
            long id = db.insertWithOnConflict(table_name, Constants.id, fields,SQLiteDatabase.CONFLICT_REPLACE);
            close_db(db);
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Esta funcion se encarga de eliminar el registro de una tabla especifica
     * @param table_name Nombre de la tabla a eliminar
     * @param id Id del elemento a eliminar
     * @return
     */
    public String remove(String table_name, String id)
    {
        String sql="delete from %table where id=%id";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%id",id);
        this.execQuery(null,sql);
        return sql;
    }

    public String remove(String table_name, String column_name, String id)
    {
        String sql="delete from %table where %column_name=%id";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column_name",column_name);
        sql=sql.replace("%id",id);
        this.execQuery(null,sql);
        return sql;
    }


    /**
     * Esta funcion se encarga de actualizar un elemento dentro de una tabla en especifico
     * @param table_name Nombre de la tabla a actualizar
     * @param id Id del elemento a actualizar
     * @param columnas Lista con las columnas a agregar, la lista debe contener
     *                 un array de string donde el primer elemento es el nombre de la columna
     *                 y el segundo es el valor a ingresar, ejemplo: [name][Julian]
     * @return
     */
    public String setByKey(String table_name, String id, LinkedList<String[]> columnas)
    {
        String sql="UPDATE %table SET %campos WHERE id=%id";
        String campos="";
        for (int i = 0; i < columnas.size(); i++) {
            String columna[]=columnas.get(i);
            campos+=columna[0]+"="+columna[1]+",";

        }
        campos=campos.substring(0,campos.length()-1);

        sql=sql.replace("%table",table_name);
        sql=sql.replace("%campos",campos);
        sql=sql.replace("%id",id);
        this.execQuery(null,sql);
        Log.d("update", sql);
        return sql;
    }
    public long setByKey(String table_name, String id, ContentValues values){
        try {
            String where = "id="+id;
            SQLiteDatabase db=null;
            db = get_db();
            long r =db.update(table_name, values, where, null);
            close_db(db);
            return r;
        } catch (Exception e) {
            return -1;
        }
    }
    public String setByKey(String table_name, String key, String key_name, LinkedList<String[]> columnas)
    {
        String sql="UPDATE %table SET %campos WHERE %key_name=%key";
        String campos="";
        for (int i = 0; i < columnas.size(); i++) {
            String columna[]=columnas.get(i);
            campos+=columna[0]+"="+columna[1]+",";

        }
        campos=campos.substring(0,campos.length()-1);

        sql=sql.replace("%table",table_name);
        sql=sql.replace("%campos",campos);
        sql=sql.replace("%key_name",key_name);
        sql=sql.replace("%key",key);
        this.execQuery(null,sql);
        Log.d("update", sql);
        return sql;
    }

    private String unescapeHtml4(String text)
    {
        text = text.replace("\n","\n ");
        text = text.replace("\r","\r ");
//        text = StringEscapeUtils.unescapeHtml4(text);
        text = text.replace("&ntilde;","ñ");

        return text;
    }

    /**
     * Esta funcion se encarga de ejecutar una sentencia sql que no sea un execQuery
     * @param sql Codigo sql a ejecutar
     * @return
     */
    public boolean execQuery(SQLiteDatabase db, String sql)
    {
        try{
            db = this.get_db();
            sql = unescapeHtml4(sql);
            db.execSQL(sql);
            Log.e("sql", sql);
            return true;
        }catch (Exception e)
        {
            Log.e("Error sql", e.getMessage());
            return false;
        }

    }

    /**
     * Esta funcion se encarga de ejecutar una accion execQuery de sql y retornar los resultados
     * @param sql Sentencia sql a ejecutar
     * @return
     */
    private Cursor execQuery(String sql)
    {
        try{
            SQLiteDatabase db = this.get_db();
            Cursor c= db.rawQuery(sql,null);
//            db.close();
            return c;
        }catch (Exception e)
        {
            Log.e("Error ejecutando sql",sql);
            Log.e("Error ejecutando sql", e.getMessage());
            return null;
        }
    }

    /**
     * Esta funcion se encarga de consultar el elemento de una tabla, basado en el id
     * @param table_name
     * @param id
     * @return
     */
    public HashMap<String,String> getById(String table_name, String id)
    {
        HashMap<String, String> datos = new HashMap<String, String>();
        String sql="SELECT * FROM %table where id=%id";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%id", id);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor = valor.replace("\\n", System.getProperty("line.separator"));
                        valor = valor.replace("\\r", "");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
            }
            if(datos.size()>0)
                return  datos;
            else
                return null;
        }else{
            return null;
        }

    }

    /**
     * Esta funcion se encarga de consultar el elemento de una tabla, basado en el id
     * @param table_name Nombre de la tabla
     * @param column Nombre de la columna
     * @param value Valor a consultar
     * @return
     */
    public HashMap<String,String> getById(String table_name, String column, String value)
    {
        HashMap<String, String> datos = new HashMap<String, String>();
        String sql="SELECT * FROM %table where %column=%value";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column", column);
        sql=sql.replace("%value", value);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor = valor.replace("\\n", System.getProperty("line.separator"));
                        valor = valor.replace("\\r", "");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
            }
            if(datos.size()>0)
                return  datos;
            else
                return null;
        }else{
            return null;
        }

    }

    /**
     * Esta funcion se encarga de consultar el elemento de una tabla, basado en el id y retornar el
     * resultado como el objecto dado
     * @param table_name
     * @param column
     * @param value
     * @param myClass
     * @return
     */
    public Object getById(String table_name, String column, String value, Class myClass)
    {
        String sql="SELECT * FROM %table where %column=%value";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column", column);
        sql=sql.replace("%value", value);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                //Se inicia la clase que se ha de retornar
                InventarioRealPojo inventarioRealPojo = null;
                try {
                    inventarioRealPojo = (InventarioRealPojo)myClass.newInstance();
                    inventarioRealPojo.fromCursor(c);
                    return inventarioRealPojo;
                } catch (IllegalAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    return null;
                } catch (InstantiationException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    return null;
                }
            }
            c.close();
            return null;

        }else{
            return null;
        }

    }

    /**
     * Esta accion se encarga de consultar todos los elementos de una tabla
     * @param table_name Nombre de la tabla a consultar
     * @return
     */
    public LinkedList<HashMap<String,String>> getByTable(String table_name)
    {
        LinkedList<HashMap<String,String>> retornar= new LinkedList<HashMap<String,String>>();
        String sql="SELECT * FROM %table";
        sql=sql.replace("%table",table_name);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                HashMap<String, String> datos = new HashMap<String, String>();
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor=valor.replace("\n","\n ");
                        valor=valor.replace("\r","\r ");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
                retornar.add(datos);
            }
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            return null;
        }

    }

    public LinkedList<HashMap<String,String>> getByTable(String table_name, String order_by)
    {
        LinkedList<HashMap<String,String>> retornar= new LinkedList<HashMap<String,String>>();
        String sql="SELECT * FROM %table order by %order_by";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%order_by",order_by);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                HashMap<String, String> datos = new HashMap<String, String>();
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor=valor.replace("\n","\n ");
                        valor=valor.replace("\r","\r ");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
                retornar.add(datos);
            }
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            return null;
        }

    }
    /**
     * Se encarga de obtener todos los datos de una tabla dada, filtrandolos por una columna
     * @param table_name Nombre de la tabla a consultar
     * @param column Nombre de la columna con la cual se realizara el filtro
     * @param value Valor con el que se compararan los datos
     * @return
     */
    public LinkedList<HashMap<String,String>> getByColumn(String table_name, String column, String value)
    {
        LinkedList<HashMap<String,String>> retornar= new LinkedList<HashMap<String,String>>();
        String sql="SELECT * FROM %table where %column=%valor";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column",column);
        sql=sql.replace("%valor",value);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                HashMap<String, String> datos = new HashMap<String, String>();
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor=valor.replace("\n","\n ");
                        valor=valor.replace("\r","\r ");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
                retornar.add(datos);
            }
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            return null;
        }

    }

    /**
     * Get rows from table based in a column given
     * @param table_name Name of the table
     * @param column Name of the column
     * @param value Value to filter
     * @param myClass Class of object to return
     * @return List of object
     */
    public LinkedList getByColumn(String table_name, String column, String value, Class myClass){
        LinkedList<Object> retornar= new LinkedList<>();
        String sql="SELECT * FROM %table where %column=%valor";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column",column);
        sql=sql.replace("%valor",value);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                //Se inicia la clase que se ha de retornar
                InventarioRealPojo instance = null;
                try {
                    instance = (InventarioRealPojo)myClass.newInstance();
                    instance.fromCursor(c);
                    retornar.add(instance);

                } catch (IllegalAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    return null;
                } catch (InstantiationException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    return null;
                }

            }
            c.close();
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            c.close();
            return null;
        }

    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }


    /**
     * Se encarga de obtener todos los datos de una tabla dada, filtrandolos por una columna y ordenandolos por una columna dada
     * @param table_name Nombre de la tabla a consultar
     * @param column Nombre de la columna con la cual se realizara el filtro
     * @param value Valor con el que se compararan los datos
     * @param order Columna por el cual se ordenaran los datos
     * @return
     */
    public LinkedList<HashMap<String,String>> getByColumn(String table_name, String column, String value, String order)
    {
        LinkedList<HashMap<String,String>> retornar= new LinkedList<HashMap<String,String>>();
        String sql="SELECT * FROM %table where %column=%valor ORDER BY %order";
        sql=sql.replace("%table",table_name);
        sql=sql.replace("%column",column);
        sql=sql.replace("%valor",value);
        sql=sql.replace("%order",order);
        Cursor c=this.execQuery(sql);
        if(c!=null)
        {
            while (c.moveToNext())
            {
                HashMap<String, String> datos = new HashMap<String, String>();
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor=valor.replace("\n","\n ");
                        valor=valor.replace("\r","\r ");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;","ñ");

                        datos.put(columna,valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
                retornar.add(datos);
            }
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            return null;
        }

    }

    public LinkedList<HashMap<String,String>> query(String sql)
    {
        LinkedList<HashMap<String,String>> retornar= new LinkedList<HashMap<String,String>>();

        Cursor c=this.execQuery(sql);
        if(c!=null)
        {

            while (c.moveToNext())
            {

                HashMap<String, String> datos = new HashMap<String, String>();
                for(int i=0;i<c.getColumnCount();i++)
                {
                    if(c.getString(i)!=null)
                    {
                        String columna=c.getColumnName(i);
                        columna=columna.replace("\n","\n ");
                        columna=columna.replace("\r","\r ");
//                        columna= StringEscapeUtils.unescapeHtml4(columna);
                        columna=columna.replace("&ntilde;","ñ");
                        String valor=c.getString(i);
                        valor=valor.replace("\n", "\n ");
                        valor=valor.replace("\r","\r ");
//                        valor = StringEscapeUtils.unescapeHtml4(valor);
                        valor = valor.replace("&ntilde;", "ñ");
                        valor = valor.replaceAll("\\s+$", "");
                        datos.put( columna, valor);
                    }else
                        datos.put(c.getColumnName(i), "null");
                }
                retornar.add(datos);
            }
            if(retornar.size()>0)
                return  retornar;
            else
                return null;
        }else{
            return null;
        }

    }


    //endregion

    //region Copiar .sqlite base de datos
    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException {

        boolean dbExist = checkDataBase();
        if(dbExist)
        {
            this.getWritableDatabase();
        }

        dbExist = checkDataBase();

        if (!dbExist) {
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {


            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            // database don't exist yet.
            e.printStackTrace();

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }
    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just createdAt empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    //endregion

}