/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

import java.io.File;
import java.util.ArrayList;

/**
 * 搜索有效的插件
 *
 * @author wcss
 */
public class JSearchPlugins {

    private static  String dirTemplate = "{DIR}/AndroidToolPlugins";
    public static  String mainPluginDir = "/opt/JApkInstaller/AndroidToolPlugins";

    /*
     * 判断目录是否有效 
     */
    public static Boolean isPluginDir(String dir) {
        File config = new File(dir + "/plugin.xml");
        File image = new File(dir + "/image.png");
        File readme = new File(dir + "/readme.txt");
        File script = new File(dir + "/run.sh");

        if (config.exists() && image.exists() && readme.exists() && script.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 搜索主插件目录
     *
     * @return
     */
    public static  ArrayList<JPluginInfo> searchMainPluginDir() {
        File main = new File(mainPluginDir);
        if (main.exists()) {
            ArrayList<JPluginInfo> list = new ArrayList<JPluginInfo>();
            File[] files = main.listFiles();
            for (File f : files) {
                try {
                    if (f.isDirectory()) {
                        if (isPluginDir(f.getAbsolutePath())) {
                            JPluginInfo jpi = JPluginInfo.loadConfig(f.getAbsolutePath() + "/plugin.xml");
                            jpi.setPluginWorkspace(f.getAbsolutePath());
                            list.add(jpi);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("读取目录" + f.getAbsolutePath() + "时出错，该目录被丢弃!");
                }
            }

            return list;
        } else {
            return null;
        }
    }

    /**
     * 搜索子插件目录
     *
     * @return
     */
    public static  ArrayList<JPluginInfo> searchHomePluginDir() {
        File main = new File(JRunScriptFilters.replaceStr(dirTemplate, "{DIR}", JAppToolKit.JRunHelper.getUserHomeDirPath()));
        if (main.exists()) {
            ArrayList<JPluginInfo> list = new ArrayList<JPluginInfo>();
            File[] files = main.listFiles();
            for (File f : files) {
                try {
                    if (f.isDirectory()) {
                        if (isPluginDir(f.getAbsolutePath())) {
                            JPluginInfo jpi = JPluginInfo.loadConfig(f.getAbsolutePath() + "/plugin.xml");
                            jpi.setPluginWorkspace(f.getAbsolutePath());
                            list.add(jpi);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("读取目录" + f.getAbsolutePath() + "时出错，该目录被丢弃!");
                }
            }

            return list;
        } else {
            return null;
        }
    }

    /**
     * 搜索插件列表
     * @return 
     */
    public static  ArrayList<JPluginInfo> searchPlugins()
    {
        ArrayList<JPluginInfo> plugins = new ArrayList<JPluginInfo>();
        ArrayList<JPluginInfo> main = searchMainPluginDir();
        ArrayList<JPluginInfo> home = searchHomePluginDir();
        if (main != null)
        {
            plugins.addAll(main);
        }
        
        if (home != null)
        {
            plugins.addAll(home);
        }
        
        return plugins;
    }

}
