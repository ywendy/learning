package com.ypb.sourcecode;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * @author yangpengbing
 * @ClassName ArrayList
 * @Description 手写ArrayList的实现
 * @Date 21:44 2018/12/18
 * @Version 1.0.0
 */
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 8683452581122892189L;
    /**
     * 默认的空的数组
     * 当用户没有指定ArrayList的容量时(即调用无参构造方法)，返回的是该数组-->刚创建一个ArrayList时，其内部数据量为0
     * 当用户第一次添加元素时，该数组会扩容，变成默认容量为10(DEFAULT_CAPACITY)的一个数组。通过ensureCapacityInternal实现的
     *
     * 它和EMPTY_ELEMENTDATA的区别就是：该数组是默认返回的，而后者是在用户指定容量为0时返回
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     * 空的对象数组
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    /**
     * ArrayList实际存储的数据量
     */
    private int size;
    /**
     * ArrayList基于数组实现，用该数组保证保存数据，ArrayList的容量就是该数组的长度. 不可序列化
     * - 该值为DEFAULT_EMPTY_ELEMENTDATA时，但第一次添加元素时，数组将扩容DEFAULT_CAPACITY
     */
    transient Object[] elementData;

    /**
     * 无参构造方法
     * - 创建一个空的ArrayList，此时其内部数组缓冲区elementData = {}, 长度为0
     * - 当元素第一次被加入时，扩容至默认容量10
     */
    public ArrayList(){
        // 直接将空数组赋值给elementData
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 带有初始容量initCapacity的构造方法
     * - 可能报错，java.lang.OutOfMemoryError：Requested array size exceeds VM limit
     * 如果initCapacity小于0，抛IllegalArgumentException异常
     * @param initCapacity
     */
    public ArrayList(int initCapacity) {
        if (initCapacity > 0) {
            this.elementData = new Object[initCapacity];
        } else if (initCapacity == 0) {
            // 数组缓冲区为EMPTY_ELEMENTDATA, 注意与DEFAULT_EMPTY_ELEMENTDATA区别
            this.elementData = EMPTY_ELEMENTDATA;
        }else {
            throw new IllegalArgumentException("illage capacity：" + initCapacity);
        }
    }

    /**
     * 带集合Collection参数的构造方法
     * @param c 要保存ArrayList中的集合，其内部元素将会全部添加到新创建的ArrayList实例中
     * @throws NullPointerException 当参数c为null时抛出异常
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray(); 可能不返回对象[], 详细参考java bug编号6260652 //TODO https://www.cnblogs.com/lsf90/p/5366325.html https://blog.csdn.net/GuLu_GuLu_jp/article/details/51456969
            if (elementData.getClass() != Object[].class) {
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        } else {
            // 使用空数组
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    @Override
    public int size() {
        return 0;
    }

    /**
     * Removes all of the elements of this collection that satisfy the given
     * predicate.  Errors or runtime exceptions thrown during iteration or by
     * the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns {@code true} for elements to be
     *               removed
     * @return {@code true} if any elements were removed
     * @throws NullPointerException          if the specified filter is null
     * @throws UnsupportedOperationException if elements cannot be removed
     *                                       from this collection.  Implementations may throw this exception if a
     *                                       matching element cannot be removed or if, in general, removal is not
     *                                       supported.
     * @implSpec The default implementation traverses all elements of the collection using
     * its {@link #iterator}.  Each matching element is removed using
     * {@link Iterator#remove()}.  If the collection's iterator does not
     * support removal then an {@code UnsupportedOperationException} will be
     * thrown on the first matching element.
     * @since 1.8
     */
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    /**
     * Replaces each element of this list with the result of applying the
     * operator to that element.  Errors or runtime exceptions thrown by
     * the operator are relayed to the caller.
     *
     * @param operator the operator to apply to each element
     * @throws UnsupportedOperationException if this list is unmodifiable.
     *                                       Implementations may throw this exception if an element
     *                                       cannot be replaced or if, in general, modification is not
     *                                       supported
     * @throws NullPointerException          if the specified operator is null or
     *                                       if the operator result is a null value and this list does
     *                                       not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @implSpec The default implementation is equivalent to, for this {@code list}:
     * <pre>{@code
     *     final ListIterator<E> li = list.listIterator();
     *     while (li.hasNext()) {
     *         li.set(operator.apply(li.next()));
     *     }
     * }</pre>
     * <p>
     * If the list's list-iterator does not support the {@code set} operation
     * then an {@code UnsupportedOperationException} will be thrown when
     * replacing the first element.
     * @since 1.8
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {

    }

    /**
     * Sorts this list according to the order induced by the specified
     * {@link Comparator}.
     * <p>
     * <p>All elements in this list must be <i>mutually comparable</i> using the
     * specified comparator (that is, {@code c.compare(e1, e2)} must not throw
     * a {@code ClassCastException} for any elements {@code e1} and {@code e2}
     * in the list).
     * <p>
     * <p>If the specified comparator is {@code null} then all elements in this
     * list must implement the {@link Comparable} interface and the elements'
     * {@linkplain Comparable natural ordering} should be used.
     * <p>
     * <p>This list must be modifiable, but need not be resizable.
     *
     * @param c the {@code Comparator} used to compare list elements.
     *          A {@code null} value indicates that the elements'
     *          {@linkplain Comparable natural ordering} should be used
     * @throws ClassCastException            if the list contains elements that are not
     *                                       <i>mutually comparable</i> using the specified comparator
     * @throws UnsupportedOperationException if the list's list-iterator does
     *                                       not support the {@code set} operation
     * @throws IllegalArgumentException      (<a href="Collection.html#optional-restrictions">optional</a>)
     *                                       if the comparator is found to violate the {@link Comparator}
     *                                       contract
     * @implSpec The default implementation obtains an array containing all elements in
     * this list, sorts the array, and iterates over this list resetting each
     * element from the corresponding position in the array. (This avoids the
     * n<sup>2</sup> log(n) performance that would result from attempting
     * to sort a linked list in place.)
     * @implNote This implementation is a stable, adaptive, iterative mergesort that
     * requires far fewer than n lg(n) comparisons when the input array is
     * partially sorted, while offering the performance of a traditional
     * mergesort when the input array is randomly ordered.  If the input array
     * is nearly sorted, the implementation requires approximately n
     * comparisons.  Temporary storage requirements vary from a small constant
     * for nearly sorted input arrays to n/2 object references for randomly
     * ordered input arrays.
     * <p>
     * <p>The implementation takes equal advantage of ascending and
     * descending order in its input array, and can take advantage of
     * ascending and descending order in different parts of the same
     * input array.  It is well-suited to merging two or more sorted arrays:
     * simply concatenate the arrays and sort the resulting array.
     * <p>
     * <p>The implementation was adapted from Tim Peters's list sort for Python
     * (<a href="http://svn.python.org/projects/python/trunk/Objects/listsort.txt">
     * TimSort</a>).  It uses techniques from Peter McIlroy's "Optimistic
     * Sorting and Information Theoretic Complexity", in Proceedings of the
     * Fourth Annual ACM-SIAM Symposium on Discrete Algorithms, pp 467-474,
     * January 1993.
     * @since 1.8
     */
    @Override
    public void sort(Comparator<? super E> c) {

    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super E> action) {

    }

    /**
     * Creates a {@link Spliterator} over the elements in this list.
     * <p>
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#ORDERED}.  Implementations should document the
     * reporting of additional characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @implSpec The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the list's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the list's iterator.
     * @implNote The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     * <p>
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @implSpec The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    @Override
    public Stream<E> stream() {
        return null;
    }

    /**
     * Returns a possibly parallel {@code Stream} with this collection as its
     * source.  It is allowable for this method to return a sequential stream.
     * <p>
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a possibly parallel {@code Stream} over the elements in this
     * collection
     * @implSpec The default implementation creates a parallel {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return null;
    }
}
