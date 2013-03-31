/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

import com.thoughtworks.xstream.XStream;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <title>使用XML文件存取可序列化的对象的类</title> <description>提供保存和读取的方法</description>
 *
 */
public class ObjectToXMLUtil
{
    
    /**
     * 把java的可序列化的对象(实现Serializable接口)序列化保存到XML文件里面,如果想一次保存多个可序列化对象请用集合进行封装
     * 保存时将会用现在的对象原来的XML文件内容
     *
     * @param obj 要序列化的可序列化的对象
     * @param fileName 带完全的保存路径的文件名
     * @throws FileNotFoundException 指定位置的文件不存在
     * @throws IOException 输出时发生异常
     * @throws Exception 其他运行时异常
     */
    public static void saveObjectToXml(Object obj, String fileName)
            throws FileNotFoundException, IOException, Exception {
        XStream xmlSerObj = new XStream();
        //创建输出文件 
        File fo = new File(fileName);
        //创建文件输出流 
        FileOutputStream fos = new FileOutputStream(fo);
        try
        {
           xmlSerObj.toXML(obj,fos);
        }finally
        {
           //关闭输出流 
           fos.close();
        }
    }

    /**
     * 读取由objSource指定的XML文件中的序列化保存的对象,返回的结果
     */
    public static Object loadObjectFromXml(String objSource)
            throws FileNotFoundException, IOException, Exception {
        XStream xmlSerObj = new XStream();        
        File fin = new File(objSource);
        Object obj = null;
        try {
            obj = xmlSerObj.fromXML(fin);
        } catch (Exception e) {
            // TODO Auto-generated catch block     
        }
        return obj;
    }
}
