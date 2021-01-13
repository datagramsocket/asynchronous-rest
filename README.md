# asynchronous-rest
spring内置的异步功能。Callable、DeferredResult. 
采用异步的好处:当一个请求花费的时间比较长时，采用同步的方法，方法会长时间占用http请求线程。  
采用异步能够释放请求线程，业务代码在单独的线程里执行，得到结果后再返回不占用请求线程。


