package com.ypb.datastructure;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: ArrayStack
 * @Description: 栈(使用数组来实现栈 ， 栈先进后出 ， 只能访问栈顶的数据) https://www.cnblogs.com/Kevin-ZhangCG/p/10299861.html
 * @date 2019/1/30-11:25
 */
@Slf4j
public class ArrayStack {

	private long[] data;
	private int size;
	private int top;

	/**
	 * 创建栈
	 */
	public ArrayStack(int maxSize) {
		this.size = maxSize;
		this.data = new long[maxSize];
		this.top = -1;
	}

	public void push(int value) {
		if (isFull()) {
			log.info("stack is full!");
			return;
		}

		data[++top] = value;
	}

	/**
	 * 返回栈顶元素，并移除
	 */
	public long pop() {
		if (isEmpty()) {
			log.info("stack is empty!");
			return 0;
		}

		return data[top--];
	}

	/**
	 * 返回栈顶元素，不移除
	 */
	public long peak() {
		if (isEmpty()) {
			log.info("stack is empty!");

			return 0;
		}

		return data[top];
	}

	/**
	 * 遍历栈元素
	 */
	public void dispaly() {
		if (isEmpty()) {
			log.info("stack is empty!");
			return;
		}

		for (int i = top; i > 0; i--) {
			System.out.println("data[i] = " + data[i]);
		}
	}

	private boolean isEmpty() {
		return top == -1;
	}

	private boolean isFull() {
		return top == size - 1;
	}

	public static void main(String[] args) {
		int maxSize = 10;
		ArrayStack stack = new ArrayStack(maxSize);

		System.out.println(stack.pop());
		stack.push(222);
		System.out.println(stack.pop());
	}
}
