/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

/**
 *
 * @author wcss
 */
public class JSudoTool {

    /**
     * 查询系统的图形Sudo命令
     *
     * @return
     * @throws Exception
     */
    public static String findSudoCmd() throws Exception {
        //assume Unix or Linux
//        String[] browsers = {
//            "gksudo", "kdesudo","gksu"};
//        String browser = null;
//        for (int count = 0; count < browsers.length && browser == null; count++) {
//            if (Runtime.getRuntime().exec(
//                    new String[]{"which", browsers[count]}).waitFor() == 0) {
//                browser = browsers[count];
//            }
//        }
//        if (browser != null) {
//            return browser;
//        } else {
//            return null;
//        }
        return "pkexec sh ";
    }

    /**
     * 运行Root脚本
     *
     * @param scriptPath
     */
    public static Process runSudoScript(String scriptPath) throws Exception {
        try {
            String[] cmdstrs = null;
            String sudoCmd = findSudoCmd();
            System.out.println("using sudo format: " +sudoCmd + "xxx");
            if (sudoCmd != null) {
                cmdstrs = new String[]{
                    "chmod +x " + scriptPath,
                     sudoCmd + scriptPath
                };
                JAppToolKit.JDataHelper.writeAllLines(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/runsyscmd.sh", cmdstrs);
                JAppToolKit.JRunHelper.runSysCmd("chmod +x " + JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/runsyscmd.sh");
                return JAppToolKit.JRunHelper.runSysCmd(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/runsyscmd.sh");
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }
}
