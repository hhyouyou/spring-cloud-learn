package com.djx.consumer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;

import java.util.List;
import java.util.Random;

/**
 * @author dong jing xi
 * @date 2021/12/3 22:01
 **/
public class RandomServiceInstanceChooser implements ServiceInstanceChooser {

    private final DiscoveryClient discoveryClient;

    private final Random random;


    public RandomServiceInstanceChooser(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.random = new Random();
    }

    @Override
    public ServiceInstance choose(String serviceId) {

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        return instances.get(random.nextInt(instances.size()));
    }


}
