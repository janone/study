package org.example.hazelcast.mapstore;

import com.hazelcast.map.MapLoader;
   import com.hazelcast.map.MapStore;

   import java.util.Collection;
   import java.util.HashMap;
   import java.util.Map;

   public class MyMapStore implements MapStore<Object, Object> {

       @Override
       public void store(Object key, Object value) {
           // 实现存储单个键值对的逻辑
           System.out.println("store key: " + key + ", value: " + value);
       }

       @Override
       public void storeAll(Map<Object, Object> map) {
           // 实现存储多个键值对的逻辑
           System.out.println("storeAll map: " + map);
       }

       @Override
       public void delete(Object key) {
           // 实现删除单个键的逻辑
           System.out.println("delete key: " + key);
       }

       @Override
       public void deleteAll(Collection<Object> keys) {
           // 实现删除多个键的逻辑
           System.out.println("deleteAll keys: " + keys);
       }

       @Override
       public String load(Object key) {
           // 实现加载单个键的逻辑
           System.out.println("load key: " + key);
           return null;
       }

       @Override
       public Map<Object, Object> loadAll(Collection<Object> keys) {
           // 实现加载多个键的逻辑
           System.out.println("loadAll");
           return new HashMap<>();
       }

       @Override
       public Iterable<Object> loadAllKeys() {
           // 实现加载所有键的逻辑
           System.out.println("loadAllKeys");
           return null;
       }
   }
   