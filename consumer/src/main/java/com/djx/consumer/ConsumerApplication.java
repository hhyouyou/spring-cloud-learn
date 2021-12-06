package com.djx.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.ServiceRequestWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongjingxi
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }


    @Autowired
    private LoadBalancerClient loadBalancer;


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> list = new ArrayList<>(
                restTemplate.getInterceptors());
        list.add(((request, body, execution) -> {
            final URI originalUri = request.getURI();
            String serviceName = originalUri.getHost();

            return loadBalancer.execute(serviceName, instance -> {
                HttpRequest serviceRequest = new ServiceRequestWrapper(request, instance, this.loadBalancer);
                return execution.execute(serviceRequest, body);
            });

        }));
        restTemplate.setInterceptors(list);
        return restTemplate;
    }


    @RestController
    static class Consumer {

        @Autowired
        private DiscoveryClient discoveryClient;

        @Autowired
        private RestTemplate restTemplate;

        private final String serviceName = "cloud-provider";

        @GetMapping("/info")
        public String info() {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

            List<String> services = discoveryClient.getServices();

            System.out.println(services.toString());

            return instances.stream()
                    .map(instance -> String.format("[serviceId:%s, host:%s, ip:%s ]", instance.getServiceId(), instance.getHost(), instance.getPort()))
                    .collect(Collectors.joining());
        }

        @GetMapping("/choose")
        public String choose() {

            RandomServiceInstanceChooser randomServiceInstanceChooser = new RandomServiceInstanceChooser(discoveryClient);

            ServiceInstance serviceInstance = randomServiceInstanceChooser.choose(serviceName);

            return restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/echo?name=hello", String.class);

        }


        @GetMapping("/hello")
        public String hello() {

            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

            ServiceInstance serviceInstance = instances.stream().findAny().orElseThrow(() -> new IllegalStateException("no" + serviceName + " install available"));

            return restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/echo?name=hello", String.class);
        }

    }
}
