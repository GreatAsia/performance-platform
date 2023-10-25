package com.okay.testcenter.tools;

import com.okay.testcenter.domain.dubbo.DubboCase;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhou
 * @date 2021/3/17
 */
public class ZookeeperConnect {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnect.class);
    private static ZooKeeper zooKeeper = null;


    private  ZookeeperConnect(){}

    static class ZookeeperFactory{
        private static final ZookeeperConnect instance = new ZookeeperConnect();
    }

   public static ZookeeperConnect getInstance(String address){
       try {
           CountDownLatch latch = new CountDownLatch(1);
           zooKeeper = new ZooKeeper(address, 100000, new Watcher() {
               @Override
               public void process(WatchedEvent event) {
                   if (event.getType() == Event.EventType.None) {
                       if (event.getState() == Event.KeeperState.SyncConnected) {
                           latch.countDown();
                       } else {
                           logger.error("连接失败.");
                           latch.countDown();
                       }
                   }
               }
           });
       } catch (IOException e) {
           logger.error("连接失败=={}",e.getMessage());
           e.printStackTrace();
       }
        return  ZookeeperFactory.instance;
   }



    public  List<String> getServiceList(){

        List<String> rootItems = new ArrayList<>();
        List<String> serviceList = new ArrayList<>();
        try {
             rootItems = zooKeeper.getChildren("/dubbo", true);
            if("metadata".equals(rootItems.get(0))){
                rootItems.remove(0);
            }
            for(String item : rootItems){
                if(item.equals("metadata") || item.contains("MonitorService")){
                    continue;
                }
                serviceList.add(item);
            }


        } catch (KeeperException e) {
            logger.error("获取服务列表失败=={}",e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serviceList;
    }


    public List<String> getSvrList(List<String> serverList){
        List<String> listSvr = new ArrayList<>();

        for(String server : serverList){
            if (server.equals("") ){
                continue;
            }
            String svr = server.split("\\.")[2];

            if(!listSvr.contains(svr)){
                listSvr.add(svr);
            }
        }
        return listSvr;
    }


    public  List<String> getRelateServiceList(String svr,List<String> serverList){

        List<String> getServer = new ArrayList<>();
        for(String server : serverList){
            if (server.equals("")){
                    continue;
                }
                String svrName = server.split("\\.")[2];
                if(svr.equals(svrName)){
                    getServer.add(server);
                }
            }
        return getServer;
    }




    public  DubboCase getServiceInfo(String serviceName){
        List<String> rootItems = new ArrayList<>();
        DubboCase dubboCase = new DubboCase();
        try {
            rootItems = zooKeeper.getChildren("/dubbo/"+ serviceName +"/providers", true);
        } catch (KeeperException e) {
            logger.error("获取服务信息失败=={}",e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(String item : rootItems){

            if(item.equals("")){
               continue;
            }
            try {
                item =  URLDecoder.decode(item, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String address = item.split("/")[2];
            String version = item.split("version=")[1];
            String[] methods = item.split("methods=")[1].split("&")[0].split(",");
            List<String> methodList = Arrays.asList(methods);

            dubboCase.setAddress(address);
            dubboCase.setInterFaceClassName(serviceName);
            dubboCase.setVersion(version);
            dubboCase.setMethodList(methodList);
        }

       return dubboCase;
    }


   public  void closed(){
        if(zooKeeper != null ){
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                logger.error("close zk fail =={}",e.getMessage());
                e.printStackTrace();
            }
        }


   }


   public static String getRequestType(DubboCase dubboCase) throws IOException, InterruptedException {

       final Process process;
       String cmd = "telnet " + dubboCase.getAddress() ;
       String lscmd = "ls -l " + dubboCase.getInterFaceClassName();
       String[] cmds =  new String[]{ "cmd", "/c", cmd , lscmd};

       process = Runtime.getRuntime().exec(System.getProperty("user.dir") + File.separator + "telnet.bat");
       String normal = printMessage(process.getInputStream());
       String error = printMessage(process.getErrorStream());
       int waitfor = process.waitFor();
       String result = "";
       if (!normal.equals("") || normal.length() != 0) {
           result = normal;
       } else {
           result = error;
       }

       String[] methods =  result.split("\n");
       for (String method : methods){
           if(method.equals("") || method.length() == 0){
               continue;
           }
           String methodInfo = method.split(" ")[1];
           String requestType = methodInfo.split("\\)")[0].split("\\(")[1];
           String methodName = methodInfo.split("\\(")[0];
           if(dubboCase.getMethodName().equals(methodName)){
               logger.info("methodName==" + methodName);
               logger.info("requestType==" + requestType);
           }

       }
     return result;
   }

    public static String printMessage(final InputStream input) {

        StringBuffer resultLog = new StringBuffer();
        try {
            Reader reader = new InputStreamReader(input, "UTF-8");
            BufferedReader bf = new BufferedReader(reader);
            String line = null;

            while ((line = bf.readLine()) != null) {
                if(!"".equals(line) || line.length() != 0){
                    resultLog.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultLog.toString();
    }

}
