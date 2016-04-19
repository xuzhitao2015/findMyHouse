package utill;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DownloadFile {
    /**
     * 根据url和文件类型生成需要保存的文件名，去掉url中的非文件名字符
     * @param url
     * @param contentType
     * @return
     */
    public String getFileName(String url,String contentType){
        url = url.substring(7); //剔除http://
        //text/html类型
        if(contentType.indexOf("html")!=-1){
            url = url.replace("[\\?/:*|<>\"]","_")+".html";
//            url = url.substring(0,url.indexOf("?"))+".html";
            return url;
        }else {
            return url.replace("[\\?/:*|<>\"]","_")+"."+contentType.substring(contentType.lastIndexOf("/")+1);
//            return url.substring(0,url.indexOf("?"))+"."+contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }

    /**
     * 保存网页字节数组到本地文件，filePath为要保存文件的相对地址
     * @param data
     * @param filePath
     */
    public void saveToLocal(byte[] data,String filePath,String filename){
        OutputStream out = null;
        try {
            File file = new File(filename);
            File path = new File(filePath);
            System.out.println(filePath+filename);
            if(!path.exists()){
                path.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }


            out =  new FileOutputStream(new File(filePath+filename));
            for (int i = 0; i < data.length; i++) {
                out.write(data[i]);
            }
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(out!=null){
                    out.flush();
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 下载指向的网页
     * @param url
     * @return
     */
    public String downloadFile(String url){
        String filePath = null;
        //1.生成HttpClient对象并设置参数
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            //根据响应码处理响应内容
            if(statusCode!= HttpStatus.SC_OK){
                System.err.println("Method failed，Status Code :"+statusCode);
                filePath = null;
            }
            HttpEntity httpEntity = response.getEntity();
            Header[] headers = response.getAllHeaders();
            filePath = "E:\\test\\";
            saveToLocal(EntityUtils.toByteArray(httpEntity),filePath,getFileName(url,httpEntity.getContentType().getValue()));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return filePath;
    }
}
