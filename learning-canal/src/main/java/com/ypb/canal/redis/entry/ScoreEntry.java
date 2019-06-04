package com.ypb.canal.redis.entry;

import com.ypb.canal.redis.annotation.PrimaryKey;
import com.ypb.canal.redis.utils.ConstantUtils;
import java.io.Serializable;
import lombok.Data;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: ScoreEntry
 * @Description: 学生分数实体
 * @date 2019-06-04-10:58
 */
@Data
public class ScoreEntry extends CustomDbObj implements Serializable {

	@PrimaryKey
	private Integer scId;

	private Integer sId;

	private Integer cId;

	private Integer score;

	private static final long serialVersionUID = 1L;

	public ScoreEntry() {
		super(ConstantUtils.DATABASE, "sc");
	}
}