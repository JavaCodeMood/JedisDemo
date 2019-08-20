package com.lhf.dataType;

import redis.clients.jedis.Jedis;

/**
 * Java操作Redis有序集合类型
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisSortedSet {
	
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * Redis SortedSet（有序集合）类型
	 * 
	 */
	public void redisSortedSet() {
		Jedis jedis = getJedis();
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
    	RedisSortedSet redis = new RedisSortedSet();
    	redis.redisSortedSet();
	}

}
