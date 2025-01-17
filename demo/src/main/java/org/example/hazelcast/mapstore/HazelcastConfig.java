package org.example.hazelcast.mapstore;

import com.hazelcast.config.Config;
   import com.hazelcast.config.MapConfig;
   import com.hazelcast.config.MapStoreConfig;

   public class HazelcastConfig {
       public static Config getConfig() {
           Config config = new Config();
           // 注意：mapstore针对的是其中一个Map，而不是所有Map
           MapConfig mapConfig = config.getMapConfig("MyMap");
           MapStoreConfig mapStoreConfig = new MapStoreConfig();
           mapStoreConfig.setClassName("org.example.hazelcast.mapstore.MyMapStore");
           mapStoreConfig.setWriteDelaySeconds(1);
           mapStoreConfig.setWriteBatchSize(1);
           mapStoreConfig.setWriteCoalescing(true);
           mapConfig.setMapStoreConfig(mapStoreConfig);
           return config;
       }
   }
   