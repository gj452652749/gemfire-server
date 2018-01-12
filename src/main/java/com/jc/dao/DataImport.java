package com.jc.dao;

import com.jc.domain.Customer;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: wangjie
 * @Description:在类加载的时候就会将以下10条数据存进去
 * @Date: Created in 14:48 2018/1/11
 */
@Component
@CacheServerApplication(name = "GemFireContinuousQueryServer")
public class DataImport {
    @Bean(name = "Customers")
    PartitionedRegionFactoryBean<Long, Customer> customersRegion(GemFireCache gemfireCache) {
        PartitionedRegionFactoryBean<Long, Customer> customers = new PartitionedRegionFactoryBean<>();
        customers.setCache(gemfireCache);
        customers.setClose(false);
        customers.setPersistent(false);
        return customers;
    }
    @Resource(name = "Customers")
    private Region<Long, Customer> customers;
    private Random random = new Random(System.currentTimeMillis());


    private static final AtomicLong id = new AtomicLong(0L);
     public static final List<Customer> customerList = new ArrayList<>();
    private static final Customer jonDoe = newCustomer(1,"Jon", "Doe");
    private static final Customer janeDoe = newCustomer(2,"Jane", "Doe");
    private static final Customer cookieDoe = newCustomer(3,"Cookie", "Doe");
    private static final Customer froDoe = newCustomer(4,"Fro", "Doe");
    private static final Customer hoDoe = newCustomer(5,"Ho", "Doe");
    private static final Customer lanDoe = newCustomer(7,"Lan", "Doe");
    private static final Customer pieDoe = newCustomer(11,"Pie", "Doe");
    private static final Customer sourDoe = newCustomer(23,"Sour", "Doe");
    private static final Customer jackHandy = newCustomer(232,"Jack", "Handy");
    public static Customer newCustomer(long id,String firstName, String lastName) {
        Customer customer = new Customer(id, firstName, lastName);
        customerList.add(customer);
        return customer;
    }

    private static Customer findCustomer(int index) {
        return customerList.get(index);
    }
    private static Customer findCustomerById(Long id) {
        return customerList.stream().filter(customer -> customer.getId().equals(id)).findAny().orElse(null);
    }
    private Customer save(Customer customer) {
        this.customers.put(customer.getId(), customer);
        return customer;
    }
    public Customer addCustomer(long id,String firstName, String lastName){
        Customer customer = new Customer(id, firstName, lastName);
        customerList.add(customer);
        return customer;
    }


}