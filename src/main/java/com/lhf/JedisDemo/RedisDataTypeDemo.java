package com.lhf.JedisDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * Redis五种数据库操作实例
 * 类型：字符串，哈希表，列表，集合，有序集合
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisDataTypeDemo {
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * Redis的key类型
	 */
	public void redisKey(){
		Jedis jedis = RedisDataTypeDemo.getJedis();
		jedis.set("mykey", "redis data type");
		System.out.println("查看键为mykey的是否存在："+jedis.exists("mykey"));
		System.out.println("键mykey的值为："+jedis.get("mykey"));
		System.out.println("查看键为mykey的类型："+jedis.type("mykey"));
		System.out.println("随机获得一个key:"+jedis.randomKey());
		System.out.println("将mykey重命名为mykey1:"+ jedis.rename("mykey", "mykey1"));
		System.out.println("删除key为mykey:"+jedis.del("mykey"));
	}
	
	/**
	 * Redis的String类型
	 */
	public void redisString(){
		Jedis jedis = RedisDataTypeDemo.getJedis(); 
        System.out.println("设置name："+jedis.set("name", "小花"));  
        System.out.println("设置name1："+jedis.set("name1", "小花1"));  
        System.out.println("设置name2："+jedis.set("name2", "小花2"));  
        System.out.println("设置name，如果存在返回0："+jedis.setnx("name", "小花哈哈"));  
        System.out.println("获取key为name和name1的value值："+jedis.mget("name","name1"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("递减1："+jedis.decr("count"));  
        System.out.println("递减2："+jedis.decrBy("index",2));  
        System.out.println("在name后面添加String："+jedis.append("name", ",我爱你"));  
        System.out.println("获取key为name的值："+jedis.get("name"));  
	}
	
	/**
	 * redis的list类型
	 */
	public void redisList(){
		Jedis jedis = RedisDataTypeDemo.getJedis();
		//在列表的头部添加数据
		jedis.lpush("list", "姗姗","age","20","address","beijing");
		//在列表的尾部添加数据
		jedis.rpush("height", "170cm","cupSize","C罩杯");
		//返回长度
		jedis.llen("list");
		//取值
		List<String> list = jedis.lrange("list", 0, -1);
		for(String str : list){
			System.out.println(str);
		}
		//System.out.println("删除key为list的数据"+jedis.del("list"));
		System.out.println("删除key为height的数据"+jedis.del("height"));
	}
	
	/**
	 * redis的set类型
	 */
	public void redisSet(){
		Jedis jedis = RedisDataTypeDemo.getJedis();
		jedis.sadd("city", "北京","上海","广州","深圳","昆明","武汉","大理");
		System.out.println("取出集合的头部元素：" + jedis.spop("city"));
		System.out.println("随机取出一个值："+ jedis.srandmember("city"));
		/*Redis Srem 命令用于移除集合中的一个或多个成员元素，不存在的成员元素会被忽略。
		        当 key 不是集合类型，返回一个错误。
		*/
		jedis.srem("city", "北京");
		//Redis Smembers 命令返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
		System.out.println(jedis.smembers("city"));
		//Redis Sismember 命令判断成员元素是否是集合的成员。 判断深圳是否是city集合的元素
		System.out.println(jedis.sismember("city", "深圳"));
		//Redis Srandmember 命令用于返回集合中的一个随机元素。
		System.out.println(jedis.srandmember("city"));
		//Redis Scard 命令返回集合中元素的数量。
		System.out.println(jedis.scard("city"));
		
		jedis.sadd("city2", "昆明","香港","澳门","台湾","上海","北京","成都");
		System.out.println("交集："+jedis.sinter("city","city2"));
		System.out.println("并集："+jedis.sunion("city","city2"));
		System.out.println("差集："+jedis.sdiff("city","city2"));
	}
	
	/**
	 * redis 哈希（map)数据类型
	 */
	public void redisMap(){
		Jedis jedis = RedisDataTypeDemo.getJedis();
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
	
	/**
	 * Redis SortedSet（有序集合）类型
	 * 
	 */
	public void redisSortedSet() {
		Jedis jedis = RedisDataTypeDemo.getJedis();
		jedis.zadd("math-score", 100, "小明");
		jedis.zadd("math-score", 92, "张三");
		jedis.zadd("math-score", 70, "李四");
		jedis.zadd("math-score", 50, "小花");
		//返回有序集 math-score 中，指定区间内的成员。
		System.out.println(jedis.zrange("math-score", 0, -1));
		//返回有序集  math-score 的基数。
		System.out.println(jedis.zcard("math-score"));
		//返回有序集 math-score 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
		System.out.println(jedis.zcount("math-score", 50, 90));
		//为有序集 math-score 的成员 小花 的 score 值加上增量 15 。
		System.out.println(jedis.zincrby("math-score", 15, "小花"));
		//返回有序集  math-score 中，成员 member 的 score 值。
		System.out.println(jedis.zscore("math-score", "小花"));
		//返回有序集 math-score 中成员 张三 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
		System.out.println(jedis.zrank("math-score", "张三"));
		//返回有序集 math-score 中，指定区间内的成员。
		System.out.println(jedis.zrevrange("math-score", 90, 100));
		//返回有序集 math-score 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员
		System.out.println(jedis.zrevrangeByScore("math-score", 100, 60));
		//移除有序集  math-score 中的一个或多个成员，不存在的成员将被忽略。
		System.out.println(jedis.zrem("math-score", "小花","小五"));
	}
	
	public static void main(String[] args) {
		RedisDataTypeDemo redis = new RedisDataTypeDemo();
		//redis.redisKey();
		redis.redisString();
		//redis.redisList();
		//redis.redisSet();
		//redis.redisMap();
		//redis.redisSortedSet();
	}
}
