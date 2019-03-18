# 学习spring注解开发

## spring开发常用的注解

- @Controller

注解作用在类上，如果写在接口上是无法注册到IOC容器中的。
配置类为controller层， 注册到IOC容器中的bean的名称是类名首字母小写(不是类名的全路径), 如果@Controller注解作用在两个相同的Controller类上，包扫描启动IOC容器的时候会报错。 即使使用@Lazy注解也不行

- @RestController
- @Service

注解作用在类上，如果写在接口上，是无法注册到IOC容器中的，默认bean的名称是类名首字母小写。 eg bookServiceImpl

- @Repository

注解作用在类上，如果写在接口上，是无法注册到IOC容器中的，默认bean的名称是类名首字母小写。eg bookDaoImpl

- @Component

注解作用在类上， 如果写在接口上，是无法注册到IOC容器中的，默认bean的名称是类名首字母小写。

- @Configuration

配置类注解，替代xml中的配置。作用等同于配置文件。

- @Bean

给IOC容器中注册一个Bean，类型为方法的返回值的类型，id默认是方法名

- @ComponentScan

指定要扫描的包，等同于<context:component-scan base-package="***" />

useDefaultFilters = false, 设置为false和includeFilters配合使用，只包含指定的classes的，如果设置为true的话，@Controller。 @Service。@Repository @Component注解都会包含
excludeFilters = Filter[] : 指定扫描的时候按照什么规则排除那些组件
includeFilters = Filter[] : 指定扫描的时候只需要包含那些组件
FilterType.ANNOATION: 按照注解 默认
FilterType.ASSIGNABLE_TYPE: 按照给定的类型
FilterType.ASPECTJ: 使用aspectj表达式
FilterType.REGEX: 使用正则指定
FilterType.CUSTOM: 使用自定义规则

这个注解只作用于@Controller，@Service，@Repository，@Component这种注解， 对其他的注解不起作用。

- @Scope
  
  设置Bean的作用域。 默认是单实例的
  prototype： 多实例， ioc容器启动的时候不会调用方法创建对象保存的容器中，每次获取的时候才会调用方法创建对象。
  singleton： 单实例， ioc容器启动的时候会调用方法创建对象保存到容器中，以后每次获取直接从容器中获取即可。 
  request：  同一个请求创建一个实例
  session： 同一个session创建一个实例
  
- @Lazy

  懒加载： 只作用于单实例的bean，单实例的bean默认在容器启动的时候就会创建对象；
  懒加载，容器启动的时候不会创建对象，第一次调用使用(获取)Bean创建对象，并初始化。

- @Conditional

  作用于方法和类上
  作用在方法上： 按照一定的条件进行判断，满足条件给容器中注册bean
  作用在类上： 满足当前的条件，这个类中配置的所有bean注册才能生效

- @Import

  快速给容器中导入一个组件
  @Import(要导入到容器中的组件)，容器中就会有自动注册这个组件，id默认是全类名
  ImportSelector：返回需要导入的组件的全类名数组
  
  需要注册的组件不能是接口，必须是类(接口的实现类)

- FactoryBean

  使用Spring提供的FactoryBean(工厂Bean)
  1. 默认获取到的是工厂bean调用Object创建的对象，不是FactoryBean。
  2. 如果想获取工厂Bean本身，需要给id前面加一个&，$factoryBean。