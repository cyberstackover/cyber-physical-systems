package com.cyber.api.job;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeakyBucket {

    public int capacity;

    public Map<String, List> buffer = new HashMap<>();

    public LeakyBucket(int capacity) {
        this.capacity = capacity;
    }

    public List fillOrConsume(String key, List data) {
        List temp = buffer.remove(key);

        if (temp == null) {
            temp = new ArrayList();
        }
        
        temp.addAll(data);
        
        if (temp.size() >= capacity) {
            return temp;
        }
        
        buffer.put(key, temp);

        return null;
    }

}
