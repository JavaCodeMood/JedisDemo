package com.lhf.JedisDemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Jedis事务
 * 假如我的银行卡中余额是100，我要购买一本价值40元的图书和一个价值70的书包， 
 * 先去购买图书，购买之后，我的余额为60，
 * 再去购买书包，此时将会购买失败，因为银行卡余额不足，导致购买失败
 * 
 * 付款方是账户A，收款方是账户B
 * 
 * @author liuhefei 2018年9月16日
 */
public class Jedis_TransactionTest1 {
	// 创建Jedis实例
	private static Jedis jedis = new Jedis("127.0.0.1", 6379);

	/**
	 * 购物
	 * 
	 * @param goodsName 购买的商品名称
	 * @param balanceA 付款方余额
	 * @param price 购买的商品价格
	 * @param balanceB 收款方余额
	 * @return
	 * @throws InterruptedException
	 * 
	 * @author liuhefei 2018年9月16日
	 */
	public boolean shopping(String goodsName, int balanceA, int price, int balanceB) throws InterruptedException {

		// 使用WATCH命令监视balanceA键
		jedis.watch("balanceA");
		// 获取Redis数据库中balanceA键的值，并转化为整形
		balanceA = Integer.parseInt(jedis.get("balanceA"));
		// 如果付款方余额小于所要购买的图书价格，则取消balanceA键的监控，提示余额不足，图书购买失败
		if (balanceA < price) {
			jedis.unwatch();
			System.out.println("余额不足，购买" + goodsName + "失败");
			return false;
		} else {
			System.out.println("*******开始购物*********");
			System.out.println("购买：" + goodsName);
			// 1.使用MULTI命令开启事务
			Transaction transaction = jedis.multi();
			// 2.事务命令入队
			transaction.decrBy("balanceA", price); // 付款方余额减去支付的金额
			transaction.incrBy("balanceB", price); // 收款方余额加上支付的金额
			// 3.使用EXEC命令执行事务
			transaction.exec();
			// 购买成功之后
			balanceA = Integer.parseInt(jedis.get("balanceA"));
			balanceB = Integer.parseInt(jedis.get("balanceB"));

			System.out.println(goodsName + "购买成功");
			System.out.println("付款方余额: " + balanceA);
			System.out.println("收款方余额: " + balanceB);

			return true;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Jedis_TransactionTest1 goShopping = new Jedis_TransactionTest1();

		int balanceA = 0; // 付款方账户余额
		int balanceB = 0; // 收款方账户余额
		int bookPrice = 40; // 图书价格
		int bagPricae = 70; // 书包价格
		String goodsName1 = "图书";
		String goodsName2 = "书包";

		// 初始化银行卡余额为100
		jedis.set("balanceA", "100");

		System.out.println("去购买图书");
		goShopping.shopping(goodsName1, balanceA, bookPrice, balanceB);
		System.out.println("\n\n去购买书包");
		goShopping.shopping(goodsName2, balanceA, bagPricae, balanceB);
	}

}
