package com.lhf.dataType;

import java.util.List;
import redis.clients.jedis.Jedis;

/**
 * Java操作Redis 列表类型
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisList {
	
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * redis的list类型
	 */
	public void redisList(){
		Jedis jedis = getJedis();
		//在列表的头部添加数据
		jedis.lpush("list", "姗姗","age","20","address","beijing");
		//在列表的尾部添加数据
		jedis.rpush("height", "170cm","cupSize","C罩杯");
		//返回长度
		System.out.println("列表长度：" + jedis.llen("list"));
		System.out.println("列表list下标为2的元素：" + jedis.lindex("list", 2));
	    System.out.println("移除一个元素：" + jedis.lrem("list", 1, "age"));
	    System.out.println("将列表 key 下标为 index 的元素的值设置为 value：" + jedis.lset("list", 5, "hello world"));
	    System.out.println("移除并返回列表list的尾元素：" + jedis.rpop("list"));
		//取值
		List<String> list = jedis.lrange("list", 0, -1);
		for(String str : list){
			System.out.println(str);
		}
		//System.out.println("删除key为list的数据"+jedis.del("list"));
		System.out.println("删除key为height的数据"+jedis.del("height"));
	}
	
	public static void main(String[] args) {
		RedisList redis = new RedisList();
		redis.redisList();
	}

}
