package com.ypb.canal.redis.entry;

import com.ypb.canal.redis.annotation.PrimaryKey;
import com.ypb.canal.redis.utils.ConstantUtils;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CourseEntry
 * @Description: 课程实体
 * @author yangpengbing
 * @date 2019-06-04-10:58
 * @version V1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseEntry extends CustomDbObj implements Serializable {

	@PrimaryKey
	private Integer cId;

    private String name;

	private static final long serialVersionUID = 1L;

	public CourseEntry() {
		super(ConstantUtils.DATABASE, "course");
	}
}