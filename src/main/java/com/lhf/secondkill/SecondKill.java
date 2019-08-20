package com.lhf.secondkill;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 秒杀抢购
 * 
 * 
 * @author liuhefei 2018年9月19日
 */
public class SecondKill implements Runnable {

	String iPhone = "iPhone";// 监视keys
	Jedis jedis = new Jedis("127.0.0.1", 6379);
	String userinfo;

	public SecondKill() {

	}

	public SecondKill(String uinfo) {
		this.userinfo = uinfo;
	}

	public void run() {
		try {
			jedis.watch(iPhone);// watchkeys

			String val = jedis.get(iPhone);
			int valint = Integer.valueOf(val);

			if (valint <= 100 && valint >= 1) {
                //1.multi命令开启事务
				Transaction tx = jedis.multi();
				//2、命令入队
				tx.incrBy("iPhone", -1);
                //exec命令提交事务
				List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null

				if (list == null || list.size() == 0) {

					String failuserifo = "fail" + userinfo;
					String failinfo = "用户：" + failuserifo + " 商品争抢失败，抢购失败";
					System.out.println(failinfo);
					/* 抢购失败业务逻辑 */
					jedis.setnx(failuserifo, failinfo);
				} else {
					for (Object succ : list) {
						String succuserifo = "succ" + succ.toString() + userinfo;
						String succinfo = "用户：" + succuserifo + " 抢购成功，当前抢购成功人数:" + (1 - (valint - 100));
						System.out.println(succinfo);
						/* 抢购成功业务逻辑 */
						jedis.setnx(succuserifo, succinfo);
					}
				}
			} else {
				String failuserifo = "kcfail" + userinfo;
				String failinfo1 = "用户：" + failuserifo + " 商品被抢购完毕，抢购失败";
				System.out.println(failinfo1);
				jedis.setnx(failuserifo, failinfo1);
				// Thread.sleep(500);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}
}
