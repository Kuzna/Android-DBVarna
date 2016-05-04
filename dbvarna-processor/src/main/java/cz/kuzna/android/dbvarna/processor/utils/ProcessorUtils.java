package cz.kuzna.android.dbvarna.processor.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.sql.Blob;
import java.util.HashMap;

import javax.lang.model.type.TypeMirror;

/**
 * @author Radek Kuznik
 */
public class ProcessorUtils {

    private static final HashMap<String, Class> typeMap = new HashMap<String, Class>() {
        {
            put(byte.class.getName(), byte.class);
            put(short.class.getName(), short.class);
            put(int.class.getName(), int.class);
            put(long.class.getName(), long.class);
            put(float.class.getName(), float.class);
            put(double.class.getName(), double.class);
            put(boolean.class.getName(), boolean.class);
            put(char.class.getName(), char.class);
            put(byte[].class.getName(), byte[].class);
            put(Byte.class.getName(), byte.class);
            put(Short.class.getName(), short.class);
            put(Integer.class.getName(), int.class);
            put(Long.class.getName(), long.class);
            put(Float.class.getName(), float.class);
            put(Double.class.getName(), double.class);
            put(Boolean.class.getName(), Boolean.class);
            put(Character.class.getName(), char.class);
            put(String.class.getName(), String.class);
            put(Byte[].class.getName(), Byte[].class);
            put(Blob.class.getName(), Blob.class);
        }
    };


    public static boolean isBooleanColumn(final TypeMirror type) {
        final String typeName = ClassName.get(type).toString();

        if(typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName())) {
            return true;
        }

        return false;
    }

    public static Class getValueClass(final TypeName typeName) {
        return typeMap.get(typeName.toString());
    }
}
