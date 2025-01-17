package org.example.hazelcast;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.example.hazelcast.mapstore.HazelcastConfig;

public class Server {

    public static void main(String[] args) {
        Config config = HazelcastConfig.getConfig();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IMap<Object, Object> myMap = instance.getMap("MyMap");
        myMap.put("key", "value");

        IQueue<Object> myQueue = instance.getQueue("MyQueue");
        myQueue.offer("value1");
        myQueue.offer("value2");

    }
}
