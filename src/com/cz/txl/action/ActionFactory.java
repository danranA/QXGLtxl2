package com.cz.txl.action;

import com.cz.txl.common.Constants;

/**
 * 该工厂根据传入的Action名字返回具体的业务action实例
 *
 */
public class ActionFactory {
    //单例模式：不需要创建对象
    private ActionFactory(){
    }
    //单实例访问方法，得到ActionFactory对象
    public static ActionFactory getActionFactory(){
        if(af == null){
            af = new ActionFactory();
        }
        return af;
    }
    /**
     * 根据具体的Action类名字创建Action对象
     * @param ActionClassName ：具体的Action类全名
     * @return：Action类型对象
     */
    public Action getAction(String actionName){
        String actionClassName = Constants.PACKAGE_PATH + actionName;
        //实现方法
        Action action = null;
        try{
            action = (Action) Class.forName(actionClassName).newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        return action;
    }

    public Action getAction(String actionPath,String actionName){
        String actionClassName = Constants.PACKAGE_PATH + actionPath+ "." + actionName;
        //实现方法
        Action action = null;
        try{
            action = (Action) Class.forName(actionClassName).newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        return action;
    }

    private static ActionFactory af;
}
