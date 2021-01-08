package com.gangtise.sdk.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExpireHashMap<K, V> extends HashMap<K, V> {

    // 定时删除过期键，间隔时间
    private static final long CHECK_TIME_SECOND = 5 * 1000;

    // 默认过期时间容器初始化数量
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    // 定时器
    private Timer timer = new Timer();

    // 期时间容器
    private Map<Object, Long> timerMap;

    // 过期数据回调
    private TimerExpireHashMapCallback timerExpireHashMapCallback ;

    /**
     * 定时删除过期键
     */
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            timerMap.keySet().forEach(key -> {
                Long keyTime = timerMap.get(key);
                if (currentTime >= keyTime.longValue()) {
                    if(timerExpireHashMapCallback != null) {
                        try {
                            timerExpireHashMapCallback.callback(key, get(key));
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                    timerMap.remove(key);
                    remove(key);
                }
            });
        }
    };

    /**
     * 构造方法
     * @param initialCapacity   容器初始数量
     * @param loadFactor        随机因子
     */
    public TimerExpireHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        init(initialCapacity, loadFactor);
    }

    /**
     * 构造方法
     * @param initialCapacity   容器初始数量
     */
    public TimerExpireHashMap(int initialCapacity) {
        super(initialCapacity);
        init(initialCapacity, 0);
    }

    /**
     * 构造方法
     */
    public TimerExpireHashMap() {
        super();
        init(DEFAULT_INITIAL_CAPACITY, 0);
    }

    /**
     * 构造方法
     * @param map
     */
    public TimerExpireHashMap(Map map) {
        super(map);
        init(DEFAULT_INITIAL_CAPACITY, 0);
    }

    /**
     * 初始化过期时间容器
     * @param initialCapacity   容器初始数量
     * @param loadFactor        随机因子
     */
    private void init(int initialCapacity, float loadFactor) {
        timerMap = new HashMap<>(initialCapacity, loadFactor);
        timer.schedule(timerTask, CHECK_TIME_SECOND);
    }

    /**
     * 获取数据
     * @param key
     */
    @Override
    public V get(Object key) {
        Long expireTime = checkKeyExpireTime(key);
        if (expireTime == null || expireTime > 0) {
            return super.get(key);
        }
        return null;
    }

    /**
     * 放入数据
     * @param key           键值
     * @param value         数据
     * @param expireSecond  过期时间（秒）
     * @return  数据
     */
    public V put(K key, V value, Long expireSecond) {
        if(expireSecond != null && expireSecond.longValue() > 0) {
            setKeyExpireTime(key, expireSecond);
        }
        return super.put(key, value);
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
     * @param expireSecond      过期时间（秒）
     */
    public void setKeyExpireTime(Object key, Long expireSecond) {
        if(expireSecond != null && expireSecond.longValue() > 0 && this.containsKey(key)) {
            long currentTime = System.currentTimeMillis();
            long expireTime = currentTime + (expireSecond * 1000);
            timerMap.put(key, expireTime);
        }
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


    public static void main(String[] args) {
        TimerExpireHashMap tehm = new TimerExpireHashMap();
        Long currenttime = System.currentTimeMillis();
        tehm.put(1,currenttime,5L);

        tehm.put(2,8L);
        Object obj = tehm.get(1);
        System.out.println(obj);
    }

}
