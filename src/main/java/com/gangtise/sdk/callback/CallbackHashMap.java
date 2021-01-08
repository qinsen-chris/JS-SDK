package com.gangtise.sdk.callback;

import java.util.*;

public class CallbackHashMap {

    // 定时删除间隔时间
    private static final long CHECK_TIME_SECOND = 1 * 1000;
    // 数据过期时间
    private static final long DATA_TIME_SECOND = 5 * 1000;

    // 默认过期时间容器初始化数量
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    // 定时器
    private Timer timer = new Timer();

    // 到期时间容器
    private static Map<Object, Long> timerMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    //数据容器
    private static Map<Object, Object> dataMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);

    public CallbackHashMap() {
        init(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // 过期数据回调
    private TimerExpireHashMapCallback timerExpireHashMapCallback ;

    public static void main(String[] args) {
        CallbackHashMap tehm = new CallbackHashMap();
        Long currenttime = System.currentTimeMillis();
        tehm.put(1,currenttime);

        tehm.put(2,8L);
        Object obj = tehm.get(1);
        System.out.println(obj);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object obj2 = tehm.get(1);
        System.out.println(obj2);
        while (obj2!=null){
            System.out.println("xxx");
        }
    }


    /**
     * 定时删除过期键
     */
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            Iterator iter = timerMap.keySet().iterator();
            while(iter.hasNext()){
                Object key = iter.next();
                Long keyTime = timerMap.get(key);
                if (currentTime >= keyTime.longValue()) {
                    //timerMap.remove(key);
                    dataMap.remove(key);
                    iter.remove();
                }
            }
        }
    };


    /**
     * 初始化过期时间容器
     * @param initialCapacity   容器初始数量
     * @param loadFactor        随机因子
     */
    private void init(int initialCapacity, float loadFactor) {
        timerMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        dataMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);

        timer.schedule(timerTask, CHECK_TIME_SECOND,CHECK_TIME_SECOND);
    }

    /**
     * 获取数据
     * @param key
     */
    public static Object get(Object key) {
        return dataMap.get(key);
    }

    /**
     * 放入数据
     * @param key           键值
     * @param value         数据
     * @return  数据
     */
    public static Object put(Object key, Object value) {
        setKeyExpireTime(key);
        //return super.put(key, value);
        return dataMap.put(key,value);
    }

    /**
     * 返回key过期剩余时间（秒）
     * @param key   键值
     * @return      返回key过期剩余时间（秒）
     */
    public Long checkKeyExpireTime(Object key) {
        Long second = timerMap.get(key);
        if(second == null) {
            return null;
        }
        long currentTime = System.currentTimeMillis();
        return ((second.longValue() - currentTime) / 1000);
    }

    /**
     * 为键值设置过期时间
     * @param key               键值
     */
    public static void setKeyExpireTime(Object key) {

        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime + DATA_TIME_SECOND;
        timerMap.put(key, expireTime);

    }

    /**
     * 设置过期数据设置监听
     * @param timerExpireHashMapCallback    监听回调
     */
    public void setTimerExpireHashMapCallback(TimerExpireHashMapCallback timerExpireHashMapCallback) {
        this.timerExpireHashMapCallback = timerExpireHashMapCallback;
    }

    /**
     * 数据设置回调
     * @param <K>
     * @param <V>
     */
    static interface TimerExpireHashMapCallback<K, V> {
        /**
         * 监听回调
         * @param key   过期键
         * @param value 过期值
         * @throws RuntimeException
         */
        public void callback(K key, V value) throws RuntimeException;
    }




}
