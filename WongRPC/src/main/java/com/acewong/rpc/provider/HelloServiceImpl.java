package com.acewong.rpc.provider;

import com.acewong.rpc.api.IRpcHelloService;

/**
 * @author AceWong
 */
public class HelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        return "AceWong";
    }
}
