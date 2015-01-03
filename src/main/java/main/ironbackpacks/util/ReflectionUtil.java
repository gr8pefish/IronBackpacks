package main.ironbackpacks.util;

import java.lang.reflect.Method;

public class ReflectionUtil {

    public static <E> Method getMethod(Class<?> clazz, String methodName, Class<?> params) {
        try {
            Method m = clazz.getDeclaredMethod(methodName, params);
            m.setAccessible(true);
            return m;
        } catch (Exception e) {
            System.out.println("Unable to find method: " + methodName);
        }
        return null;
    }

    public static void invokeMethod(Method method, Class<?> clazz, Object params){
        try{
            method.invoke(clazz ,params);
        } catch (Exception e){
            System.out.println("Unable to invoke method: " + method.getName());
        }
    }

}
