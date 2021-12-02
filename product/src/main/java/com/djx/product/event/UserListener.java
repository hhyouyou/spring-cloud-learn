package com.djx.product.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author dong jing xi
 * @date 2021/11/28 22:13
 **/
@Component
public class UserListener {


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void userScoreInitListener(CreateUserEvent createUserEvent) {

        System.out.println("create user and init score:" + createUserEvent.getUsername());

    }

    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    @EventListener
    public void userPermitInitListener(CreateUserEvent createUserEvent) {

        System.out.println("create user and init Permit:" + createUserEvent.getUsername());

    }

    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    @EventListener
    public void userRoleInitListener(CreateUserEvent createUserEvent) {

        System.out.println("create user and init role:" + createUserEvent.getUsername());

    }

}
