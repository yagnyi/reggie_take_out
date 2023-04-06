package com.oil.reggie_take_out.common;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static Long getId(){
        return threadLocal.get();
    }

    public static void setId(Long id){
         threadLocal.set(id);
    }


}
