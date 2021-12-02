package com.djx.product.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author dong jing xi
 * @date 2021/11/28 22:11
 **/
public class CreateUserEvent extends ApplicationEvent {

    @Getter
    private final String username;

    public CreateUserEvent(String username) {
        super(username);
        this.username = username;

    }
}
