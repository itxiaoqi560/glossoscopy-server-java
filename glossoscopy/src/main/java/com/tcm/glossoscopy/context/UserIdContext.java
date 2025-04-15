package com.tcm.glossoscopy.context;

public class UserIdContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void removeId(){
        threadLocal.remove();
    }

    public static Long getId(){
        return threadLocal.get();
    }

    public static void setId(Long id){
        threadLocal.set(id);
    }

}
