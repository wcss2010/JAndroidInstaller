/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 执行插件脚本
 *
 * @author wcss
 */
public class JRunPluginScript implements Runnable {

    private JPluginScriptRequest requestEvent;

    /**
     * @return the requestEvent
     */
    public JPluginScriptRequest getRequestEvent() {
        return requestEvent;
    }

    /**
     * @param requestEvent the requestEvent to set
     */
    public void setRequestEvent(JPluginScriptRequest requestEvent) {
        this.requestEvent = requestEvent;
    }

    /**
     * 运行脚本
     *
     * @param workspace
     * @param imageFile
     * @param scriptPath
     * @throws Exception
     */
    public void runScript(String workspace, String imageFile, String scriptPath) throws Exception {
        JRunScriptFilters.currentSelectedImageFilePath = imageFile;
        if (JRunScriptFilters.existStrInScript(scriptPath, JRunScriptFilters.waitStateMethod)) {
            throw new Exception("error script!");
        } else {
            String destPath = JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/plugin_" + new Date().getTime() + ".sh";
            JRunScriptFilters.filterScript(scriptPath, destPath);
            traceScript(destPath, workspace);
        }
    }

    /**
     * 跟踪运行脚本
     *
     * @param start
     * @param workspace
     * @throws Exception
     */
    private void traceScript(String start, String workspace) throws Exception {
        ArrayList<String> startupPlugin = new ArrayList<String>();
        startupPlugin.add("#!/bin/sh");
        startupPlugin.add("cd " + workspace);
        startupPlugin.add("chmod +x " + start);
        startupPlugin.add(start);
        String runP = JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/run_" + new Date().getTime() + ".sh";
        JAppToolKit.JDataHelper.writeAllLines(runP, startupPlugin);
        JAppToolKit.JRunHelper.runSysCmd("chmod +x " + runP);

        threadRunScript = runP;
        Thread tt = new Thread(this);
        tt.setDaemon(true);
        tt.start();
    }

    public static void main(String[] args) {
        try {
            JRunPluginScript rps = new JRunPluginScript();
            rps.runScript("/home/wcss/", "/home/wcss/image.img", "/home/wcss/测试.sh");
        } catch (Exception ex) {
            Logger.getLogger(JRunScriptFilters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String threadRunScript = "";

    @Override
    public void run() {

        try {
            Process cc = JAppToolKit.JRunHelper.runSysCmd(threadRunScript, false);
            //InputStream errorin = cc.getErrorStream();
            InputStream showin = cc.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(showin));
            String nowline = br.readLine();
            while (nowline != null) {
                if (nowline != null && nowline.startsWith("printinfo")) {
                    nowline = JRunScriptFilters.replaceStr(nowline, "printinfo", "");

                    System.out.println("需要打印 " + nowline);

                    if (this.getRequestEvent() != null) {
                        this.requestEvent.printHint(nowline);
                    }
                }
                nowline = br.readLine();
            }

            br.close();
            showin.close();
            //errorin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}