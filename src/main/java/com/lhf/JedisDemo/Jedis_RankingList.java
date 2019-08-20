package com.lhf.JedisDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Jedis实现排行榜
 * 实现王者荣耀手游按照积分排名
 * 
 * 
 * @author liuhefei
 * 2018年9月18日
 */
public class Jedis_RankingList {
	static int TOTAL_SIZE = 30;   //玩家总人数

	public static void main(String[] args) {
		//创建Jedis实例
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("Redis连接成功");
		
		try {
			String key = "欢迎来到王者荣耀";
			//清除可能已有的键
			jedis.del(key);
			
			//模拟生成多个游戏玩家
			List<String> players = new ArrayList<String>();
			for(int i=0;i<TOTAL_SIZE;++i) {
				//随机生成每个玩家的ID
				players.add(UUID.randomUUID().toString());
			}
			System.out.println("-----玩家登陆进入游戏-----");
			//开始记录每个玩家的得分
			for(int j=0;j<players.size();j++) {
				//模拟生成游戏玩家的得分
				int score = (int)(Math.random()*10000);
				String member = players.get(j);
				//打印玩家的游戏得分信息
				System.out.println("玩家ID: " + member + ", 玩家得分： " + score);
				//将玩家的ID和得分，都加到对应key的SortedSet中去
                jedis.zadd(key, score, member);
			}
			//输出打印全部玩家排行榜
            System.out.println();
            System.out.println("----------"+key+"-------------");
            System.out.println("------------全部玩家排行榜-----------");
            //从对应key的SortedSet中获取已经排好序的游戏玩家列表
            Set<Tuple> scoreList = jedis.zrevrangeWithScores(key, 0, -1);
            for(Tuple item : scoreList) {
            	System.out.println("玩家ID: " + item.getElement() + ", 玩家得分： " + Double.valueOf(item.getScore()));
            }
            
            //打印出排名前五的游戏玩家
            System.out.println();
            System.out.println("-----------"+key+"--------------");
            System.out.println("--------王者荣耀排名前五玩家-----------");
            scoreList = jedis.zrevrangeWithScores(key, 0, 4);
            for(Tuple item : scoreList) {
            	System.out.println("玩家ID: " + item.getElement() + ", 玩家得分： " + Double.valueOf(item.getScore()));
            }
            
            //打印出得分在5000到8000之间的玩家信息
            System.out.println();
            System.out.println("-----------"+key+"------------");
            System.out.println("--------王者荣耀得分在5000至8000的玩家-----------");
            scoreList = jedis.zrangeByScoreWithScores(key, 5000, 8000);
            for(Tuple item : scoreList) {
            	System.out.println("玩家ID: " + item.getElement() + ", 玩家得分： " + Double.valueOf(item.getScore()));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jedis.quit();
			jedis.close();
		}
	}
	

}
