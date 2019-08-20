package com.lhf.dataType;

import redis.clients.jedis.Jedis;

/**
 * Java操作Redis集合类型
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisSet {
	
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * redis的set类型
	 */
	public void redisSet(){
		Jedis jedis = getJedis();
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
	
	public static void main(String[] args) {
		RedisSet redis = new RedisSet();
		redis.redisSet();
	}

}
