package org.example.hazelcast.mapstore;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
   import com.hazelcast.core.HazelcastInstance;
   import com.hazelcast.map.IMap;

   public class HazelcastExample {
       public static void main(String[] args) {
           Config config = HazelcastConfig.getConfig();
           HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
           IMap<Integer, String> myMap = hazelcastInstance.getMap("myMap");
           myMap.put(1, "value1");
           System.out.println(myMap.get(1));
       }
   }
   