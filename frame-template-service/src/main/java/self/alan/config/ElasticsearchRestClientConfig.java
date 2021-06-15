package self.alan.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ElasticsearchRestClient
 * @Description TODO
 * @Date 2020/12/7 下午8:17
 * @Created by liuhao
 */
@Configuration
public class ElasticsearchRestClientConfig {


    /**
     * ES地址,ip:port
     */
    @Value("${config.elasticsearch.host}")
    private String ipPort;

    @Value("${config.elasticsearch.userName}")
    private String  userName;

    @Value("${config.elasticsearch.password}")
    private String  password;


    @Bean
    public RestClientBuilder restClientBuilder() {

        HttpHost[] httpHosts = makeHttpHost(ipPort);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        RestClientBuilder builder = RestClient.builder(httpHosts).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(-1);
                requestConfigBuilder.setSocketTimeout(-1);
                requestConfigBuilder.setConnectionRequestTimeout(-1);
                return requestConfigBuilder;
            }
        }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });


        return RestClient.builder(httpHosts);
    }


//    @Bean(name = "highLevelClient")
    @Bean
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {

        return new RestHighLevelClient(restClientBuilder);
    }


    /**
     * 支持集群
     * @param nodesStr
     * @return
     */
    private HttpHost[] makeHttpHost(String nodesStr) {

        List<HttpHost> httpHostList = new ArrayList<HttpHost>();

        String[] nodes = nodesStr.split(",");

        for (int i = 0; i < nodes.length; i++) {
            String node = nodes[i];

            String[] address = node.split(":");
            String ip = address[0];
            int port = Integer.parseInt(address[1]);

            HttpHost httpHost = new HttpHost(ip, port, "http");

            httpHostList.add(httpHost);
        }

        HttpHost[] httpArrayList = new HttpHost[httpHostList.size()];

        HttpHost[] httpHosts = httpHostList.toArray(httpArrayList);

        return httpHosts;
    }

}
