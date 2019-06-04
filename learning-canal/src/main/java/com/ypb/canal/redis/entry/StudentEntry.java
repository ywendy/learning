package com.ypb.canal.redis.entry;

import com.ypb.canal.redis.annotation.PrimaryKey;
import com.ypb.canal.redis.utils.ConstantUtils;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: StudentEntry
 * @Description: 学生实体
 * @author yangpengbing
 * @date 2019-06-04-11:04
 * @version V1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentEntry extends CustomDbObj implements Serializable {

	@PrimaryKey
	private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;

	public StudentEntry() {
		super(ConstantUtils.DATABASE, "student");
	}
}