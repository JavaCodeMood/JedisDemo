package com.lhf.dataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.Jedis;

/**
 * Java操作Redis 哈希表类型
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisHash {
	
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * redis 哈希（map)数据类型
	 */
	public void redisMap(){
		Jedis jedis = getJedis();
		jedis.hset("bigCity", "big", "北京");
		System.out.println("取值：" + jedis.hget("bigCity", "big"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("big1", "上海");
		map.put("big2", "香港");
		map.put("big3", "武汉");
		jedis.hmset("bigCity2", map);
		List<String> list1 = jedis.hmget("bigCity2", "big1","big2","big3");
		for(String str1 : list1){
			System.out.println(str1);
		}
		//删除map中的某个键值
		jedis.hdel("bigCity2", "上海");
		System.out.println(jedis.hmget("bigCity2", "height")); //因为删除了，所有返回的是null
		System.out.println(jedis.hlen("bigCity2")); //返回key为bigCity2的键中存放的值的个数2 
		System.out.println(jedis.exists("bigCity2"));//是否存在key为bigCity2的记录 返回true  
		System.out.println(jedis.hkeys("bigCity2"));//返回map对象中的所有key  
		System.out.println(jedis.hvals("bigCity2"));//返回map对象中的所有value 
	}
	
	public static void main(String[] args) {
		RedisHash redis = new RedisHash();
		redis.redisMap();
	}

}
