package org.example.hazelcast;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Server2 {

    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IMap<Object, Object> myMap = instance.getMap("MyMap");
        Object myMap1 = myMap.get("key");
        System.out.println(myMap1);

        IQueue<Object> myQueue = instance.getQueue("MyQueue");
        Object poll = myQueue.poll();
        System.out.println(poll);
    }
}
