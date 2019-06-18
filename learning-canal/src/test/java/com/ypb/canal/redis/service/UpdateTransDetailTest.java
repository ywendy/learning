package com.ypb.canal.redis.service;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.ypb.canal.redis.entry.TransDetailEntry;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateTransDetailTest {

	@Autowired
	private UpdateTransDetail updateTransDetail;
	private Long groupID = 64562L;

	@Test
	public void updateTransDetail() {
		List<Long> list = Lists
				.newArrayList(3508472L ,3546306L ,3879269L ,3924929L ,4028341L ,4100147L ,4152243L, 886164408416474466L,
						6673762061010361394L, 6680431320218233906L, 6681071033769798706L, 6681462571578436658L,
						6682269960804248626L, 6682943664995445810L, 6683401418289740850L, 6684787949780868146L,
						6686273574799416370L, 6688615547325656114L, 6689245361397046322L, 6689613968308186162L,
						6690477480073014322L, 6696404028034710578L);

		// 6681462571578436658 6686273574799416370 存在多消费的会员卡
		list = Lists.newArrayList(6696404028034710578L);
		for (Long cardID : list) {
			updateTransDetail.updateTransDetail(groupID, cardID);
		}
	}

	@Test
	public void demo1() throws IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\数据\\cardID.txt");
		List<String> lines = FileUtils.readLines(file, Charsets.UTF_8.displayName());

		if (CollectionUtils.isEmpty(lines)) {
			return;
		}

		Multimap<Long, Long> multimap = HashMultimap.create();
		for (String line : lines) {
			List<String> strings = Splitter.on("\t").splitToList(line);

			multimap.put(Long.valueOf(strings.get(0)), Long.valueOf(strings.get(1)));
		}

		for (Long groupID : multimap.keySet()) {
			for (Long cardID : multimap.get(groupID)) {
				updateTransDetail.updateTransDetail(groupID, cardID);
			}
		}
	}

	@Test
	public void update(){
		groupID = 004003L;
		try {
			updateTransDetail.createSql(groupID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void demo() {
		String transReceiptsTxt = "┏━━━━━━━━━━━━━━┓\n"
				+ "┃◇◇会员交易　商户对账单◇◇┃\n"
				+ "┗━━━━━━━━━━━━━━┛\n"
				+ "    等级：储值卡\n"
				+ "    状态：正常\n"
				+ "实体卡号：00119\n"
				+ "    手机：13674868922（绑定）\n"
				+ "    姓名：刘（女）\n"
				+ "…………………………………………\n"
				+ "交易流水：6700105122636106802\n"
				+ "交易店铺：和合记粥面大王\n"
				+ "入会店铺：和合记粥面大王\n"
				+ "交易时间：2019-06-08 18:29:48\n"
				+ "…………………………………………\n"
				+ "交易前现金卡值：3.64元\n"
				+ "交易前赠送卡值：0.36元\n"
				+ "…………………………………………\n"
				+ "【本次消费交易】\n"
				+ "收银系统单号： 2019060818294213733972460794\n"
				+ "消费金额： 16\n"
				+ "…………………………………………\n"
				+ "交易后现金卡值：3.64元\n"
				+ "交易后赠送卡值：0.36元\n"
				+ "…………………………………………\n"
				+ "打印时间：2019-06-08 18:29:48\n"
				+ "操作人员：123|小娟";

		TransDetailEntry entry = new TransDetailEntry();
		entry.setTransAfterMoneyBalance(BigDecimal.valueOf(3.64));
		entry.setNewTransAfterMoneyBalance(BigDecimal.valueOf(60.91));

		entry.setTransAfterGiveBalance(BigDecimal.valueOf(0.36));
		entry.setNewTransAfterGiveBalance(BigDecimal.valueOf(6.09));

		TransDetailEntry transDetailEntry = new TransDetailEntry();
		transDetailEntry.setTransAfterMoneyBalance(BigDecimal.valueOf(3.64));
		transDetailEntry.setNewTransAfterMoneyBalance(BigDecimal.valueOf(60.91));
		transDetailEntry.setTransAfterGiveBalance(BigDecimal.valueOf(0.36));
		transDetailEntry.setNewTransAfterGiveBalance(BigDecimal.valueOf(6.09));

		String txt = setTransReceiptsTxt(transReceiptsTxt, entry, transDetailEntry);

		System.out.println("txt = " + txt);
	}

	private String setTransReceiptsTxt(String transReceiptsTxt, TransDetailEntry entry, TransDetailEntry transDetailEntry) {
		String str = "交易前现金卡值：" + entry.getTransAfterMoneyBalance() + "元";
		String newStr = "交易前现金卡值：" + entry.getNewTransAfterMoneyBalance() + "元";

		transReceiptsTxt = transReceiptsTxt.replace(str, newStr);

		str = "交易前赠送卡值："+entry.getTransAfterGiveBalance()+"元";
		newStr = "交易前赠送卡值：" + entry.getNewTransAfterGiveBalance() + "元";

		transReceiptsTxt = transReceiptsTxt.replace(str, newStr);

		str = "交易后现金卡值：" + transDetailEntry.getTransAfterMoneyBalance() + "元";
		newStr = "交易后现金卡值：" + transDetailEntry.getNewTransAfterMoneyBalance() + "元";

		transReceiptsTxt = transReceiptsTxt.replace(str, newStr);

		str = "交易后赠送卡值：" + transDetailEntry.getTransAfterGiveBalance() + "元";
		newStr = "交易后赠送卡值：" + transDetailEntry.getNewTransAfterGiveBalance() + "元";

		transReceiptsTxt = transReceiptsTxt.replace(str, newStr);

		return transReceiptsTxt;
	}
}