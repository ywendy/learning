package com.ypb.canal.redis.service;

import com.google.common.collect.Lists;
import com.ypb.canal.redis.entry.TransDetailEntry;
import com.ypb.canal.redis.mapper.TransDetailEntryMapper;
import com.ypb.canal.redis.utils.BeanToMapUtils;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class UpdateTransDetail {

	@Autowired
	private TransDetailEntryMapper transDetailEntryMapper;

	public void updateTransDetail(Long groupID, Long cardID){
		List<TransDetailEntry> entries = transDetailEntryMapper.getTransDetailByCardID(groupID, cardID);

		if (CollectionUtils.isEmpty(entries)) {
			return;
		}

		TransDetailEntry entry = entries.get(BigDecimal.ZERO.intValue());
		entry.setNewTransAfterMoneyBalance(entry.getTransAfterMoneyBalance());
		entry.setNewTransAfterGiveBalance(entry.getTransAfterGiveBalance());

		transDetailEntryMapper.updateByPrimaryKeySelective(entry);

		TransDetailEntry detail1 = transDetailEntryMapper
				.getTransDetailByLinkedID(entry.getGroupID(), entry.getTransID());

		if (Objects.nonNull(detail1)) {
			detail1.setNewTransAfterMoneyBalance(detail1.getTransAfterMoneyBalance());
			detail1.setNewTransAfterGiveBalance(detail1.getTransAfterGiveBalance());

			transDetailEntryMapper.updateByPrimaryKeySelective(detail1);
		}

		for (int i = 1; i < entries.size(); i++) {
			TransDetailEntry entry1 = entries.get(i);

			TransDetailEntry detail = transDetailEntryMapper
					.getTransDetailByLinkedID(entry1.getGroupID(), entry1.getTransID());

			BigDecimal newTransAfterMoneyBalance = initCalc(entry, entry1);
			BigDecimal newTransAfterGiveBalance = initCalcByGive(entry, entry1);

			entry1.setNewTransAfterMoneyBalance(newTransAfterMoneyBalance);
			entry1.setNewTransAfterGiveBalance(newTransAfterGiveBalance);
			entry1.setTransReceiptsTxt(setTransReceiptsTxt(entry1.getTransReceiptsTxt(), entry, entry1));

			if (Objects.nonNull(detail)) {
				detail.setNewTransAfterMoneyBalance(newTransAfterMoneyBalance);
				detail.setNewTransAfterGiveBalance(newTransAfterGiveBalance);

				detail.setTransReceiptsTxt(setTransReceiptsTxt(detail.getTransReceiptsTxt(), entry1, entry1));

				transDetailEntryMapper.updateByPrimaryKeySelective(detail);
			}

			transDetailEntryMapper.updateByPrimaryKeySelective(entry1);

			entry = entry1;
		}
	}

	public void createSql(Long groupID) throws Exception {
		List<TransDetailEntry> entries = transDetailEntryMapper.getTransDetails(groupID);

		if (CollectionUtils.isEmpty(entries)) {
			return;
		}

		List<String> sqls = Lists.newArrayList();
		for (TransDetailEntry entry : entries) {
			String sql = createSql(entry);

			sqls.add(sql);
		}

		File file = new File("E:\\64562\\" + groupID + ".sql");
		FileUtils.writeLines(file, Charsets.UTF_8.displayName(), sqls);
	}

	private String createSql(TransDetailEntry entry) throws Exception {
		Map<String, Object> map = BeanToMapUtils.convertBean(entry, Boolean.TRUE);

		String sql = "UPDATE tbl_crm_trans_detail SET transAfterMoneyBalance = ${newTransAfterMoneyBalance}, transAfterGiveBalance = ${newTransAfterGiveBalance}, transReceiptsTxt = '${transReceiptsTxt}' WHERE groupID = ${groupID} AND cardID = ${cardID} AND transID = ${transID} AND transAfterMoneyBalance = ${transAfterMoneyBalance} AND transAfterGiveBalance = ${transAfterGiveBalance};";

		return StrSubstitutor.replace(sql, map);
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

	private BigDecimal initCalcByGive(TransDetailEntry entry, TransDetailEntry entry1) {
		return entry.getNewTransAfterGiveBalance().add(entry1.getSaveReturnMoneyAmount())
				.subtract(entry1.getGiveBalancePay());
	}

	private BigDecimal initCalc(TransDetailEntry entry, TransDetailEntry entry1) {
		return entry.getNewTransAfterMoneyBalance().add(entry1.getSaveMoneyAmount())
				.subtract(entry1.getDeductionMoneyAmount());
	}
}
