Week02 作业题目：

1.（选做）使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

2.（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

3.（选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。

4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。
## 各种 GC 的总结

- java -XX:+PrintGCDetails GCLogAnalysis
  默认 GC 是 4G
- java -XX:+PrintGCDetails -Xmx1g -Xms1g

  减少堆内存后，GC 更加的频繁

  日志解析：
```
[GC (Allocation Failure) [PSYoungGen: 262144K->43517K(305664K)] 262144K->81053K(1005056K), 0.0254034 secs] [Times: user=0.05 sys=0.12, real=0.02 secs] 
“[GC (Allocation Failure) [PSYoungGen: 262144K->43517K(305664K)] 262144K->81053K(1005056K), 0.0254034 secs]”
第一段堆内存的变化情况， 有 GC 本身的变化情况
“[Times: user=0.05 sys=0.12, real=0.02 secs]”
第二段 cpu 变化情况

“Allocation Failure”： gc 的原因

“0.0254034 secs”：gc 的时间（stw 的时间）

“262144K->43517K(305664K)”：young 区从262144K压缩到43517K，当前整个young 区最大为305664K

“262144K->81053K(1005056K)”：整个堆的压缩情况和最大值

“(262144K-43517K) - (262144K->81053K)”：提升到了 Old 区

```


```
[Full GC (Ergonomics) [PSYoungGen: 39934K->0K(232960K)] [ParOldGen: 602570K->333884K(699392K)] 642505K->333884K(932352K), [Metaspace: 2545K->2545K(1056768K)], 0.0527965 secs] [Times: user=0.27 sys=0.01, real=0.05 secs] 
解析：
    


```


- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m GCLogAnalysis

```
减小堆内存内存，Full GC次数明显增多
```

### UseSerialGC

- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m -XX:+UseSerialGC -Xloggc:UseSerialGC.log  GCLogAnalysis
```
[GC (Allocation Failure) 2021-03-28T20:58:03.322-0800: 0.157: [DefNew: 139776K->17471K(157248K), 0.0291522 secs] 139776K->49128K(506816K), 0.0293536 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 

解析：
“DefNew”：young gc
“Tenured”：老年代 gc

```

### UseParallelGC

- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m -XX:+UseParallelGC -Xloggc:UseParallelGC512.log  GCLogAnalysis
- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseParallelGC -Xloggc:UseParallelGC4g.log  GCLogAnalysis

```
当使用 UseParallelGC 在大堆内存时，GC 暂停时间会加长
```

### UseConcMarkSweepGC

- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC -Xloggc:UseConcMarkSweepGC1g.log GCLogAnalysis
- java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseConcMarkSweepGC -Xloggc:UseConcMarkSweepGC4g.log GCLogAnalysis

```
CMS Initial Mark：（初始化标记） （需要GC 暂停）
CMS-concurrent-mark-start 不需要stw
CMS-concurrent-mark （并发标记）不需要stw
CMS-concurrent-preclean-start 不需要stw
CMS-concurrent-preclean （并发预清理） 不需要stw
CMS-concurrent-abortable-preclean-start 不需要stw
CMS Final Remark：（最终标记） 需要GC 暂停
CMS-concurrent-sweep-start：
CMS-concurrent-sweep：（并发清除）
CMS-concurrent-reset-start：
CMS-concurrent-reset：（并发重置）


当 CMS GC 堆内存设置变大，young GC 次数变少
```

### UseG1GC
- java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseG1GC -Xloggc:UseG1GC1g.log GCLogAnalysis
- java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseG1GC -Xloggc:UseG1GC4g.log GCLogAnalysis

```
[Parallel Time: 5.2 ms, GC Workers: 8]
8线程 Parallel 并发执行

- (G1 Evacuation Pause) (young) (纯年轻代模式转移暂停)
- initial-mark （初始化标记）
- concurrent-root-region-scan （Root区扫描）
- concurrent-mark（并发标记）
- concurrent-cleanup （并发清理）
- remark （在此标记）
- concurrent-cleanup （清理）
- (G1 Evacuation Pause) (mixed) （转移暂停）
- Full GC

```

### 备注
- Young GC
- Full GC(young gc + old gc)
- Minor GC(小型GC)
- Major GC(大型GC)


5.（选做）运行课上的例子，以及 Netty 的例子，分析相关现象。

6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 GitHub

```java
package com.mrrightli.week02;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestTool {

    public static void main(String[] args) {
        RequestTool tool = new RequestTool();
        tool.doGet();
    }

    public void doGet() {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        URI uri = null;
        try {
            // 将参数放入键值对类NameValuePair中,再放入集合中
            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("goods_id", "609484"));
            // 设置uri信息,并将参数集合放入uri;
            uri = new URIBuilder().setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(8803)
                    .setPath("/")
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
```


以上作业，要求 2 道必做题目提交到自己 GitHub 上面，然后把自己的作业链接填写到下方的表单里面：
https://jinshuju.net/f/rEkOP6

作业提交规范：
1. 作业不要打包 ；
2. 同学们写在 md 文件里，而不要发 Word, Excel , PDF 等 ；
3. 代码类作业需提交完整 Java 代码，不能是片段；
4. 作业按课时分目录，仅上传作业相关，笔记分开记录；
5. 画图类作业提交可直接打开的图片或 md，手画的图手机拍照上传后太大，难以查看，推荐画图（推荐 PPT、Keynote）；
6. 提交记录最好要标明明确的含义（比如第几题作业）。
   GitHub 使用教程： https://u.geekbang.org/lesson/51?article=294701

学号查询方式：PC 端登录 time.geekbang.org, 点击右上角头像进入我的教室，左侧头像下方 G 开头的为学号