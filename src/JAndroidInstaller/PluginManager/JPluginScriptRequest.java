/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

/**
 * 脚本运行时请求界面响应
 * @author wcss
 */
public interface JPluginScriptRequest
{
    void printHint(String content);
    void shell(String cmd,String param);
}
