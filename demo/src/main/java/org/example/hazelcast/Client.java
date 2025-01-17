package org.example.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Client {

    public static void main(String[] args) {
        clientPutValue();
//        clientGetValue();
//        clientModifyValue();
    }

    public static void clientPutValue(){
        HazelcastInstance instance = HazelcastClient.newHazelcastClient();
        IMap<Object, Object> myMap = instance.getMap("MyMap");
        myMap.put("client2", "clientValue2");
        instance.shutdown();
    }

    public static void clientGetValue(){
        HazelcastInstance instance = HazelcastClient.newHazelcastClient();
        IMap<Object, Object> myMap = instance.getMap("MyMap");
        Object client2 = myMap.get("client2");
        System.out.println("client2 = " + client2);
        instance.shutdown();
    }
    public static void clientModifyValue(){
        HazelcastInstance instance = HazelcastClient.newHazelcastClient();
        IMap<Object, Object> myMap = instance.getMap("MyMap");
        Object client21 = myMap.get("client2");
        System.out.println("client2 = " + client21);
        myMap.put("client2", "newClientValue2");
        Object client2 = myMap.get("client2");
        System.out.println("client2 = " + client2);
        instance.shutdown();
    }
}
