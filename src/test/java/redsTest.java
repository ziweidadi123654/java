import com.sun.org.apache.xerces.internal.impl.xs.util.XSNamedMap4Types;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import sun.java2d.pipe.SpanIterator;

import java.util.*;

public class redsTest {
    private Jedis jedis;
    @Before
    public void before(){
        jedis=new Jedis("192.168.224.142",7000);
    }
    @Test
    public void test(){
        //设置一个key/value
        String set = jedis.set("name", "zhaolin");
        jedis.set("age","20");
        //根据key获取对应的值
        String name = jedis.get("name");
        System.out.println(name);
        //一次设置多个key/value
        jedis.mget("name","赵霖","age","18","address","北京");
        //一次获得多个key的value
        List<String> mget = jedis.mget("name", "age", "address");
        for (String s : mget) {
            System.out.println(s);
        }
        //获得原始key的值，同时设置新的value
        jedis.getSet("name","月倾浅");
        //获得对应key存储value的长度
        Long name1 = jedis.strlen("name");
        System.out.println(name1);
        //为对应的存储的value追加内容
        Long append = jedis.append("age", "岁");
        //截取value的内容
        String name2 = jedis.getrange("name", 0, 6);
        System.out.println(name2);
        //设置一个key存活的有效期（秒）
        String code = jedis.setex("code", 1000, "123");
        //设置一个key存活的有效期（豪秒）
        jedis.psetex("code1",10000000,"sapd");
    //存在不做任何操作,不存在添加
        String setex = jedis.setex("name", 1, "dahuang");
        System.out.println(setex);
        //可以同时设置多个key,只有有一个存在都不保存
        Long msetnx = jedis.msetnx("name", "xiaotu");
        System.out.println(msetnx);
    }
    @Test
    public void testlist(){
        //将某个值加入到一个key列表头部
        Long lpush = jedis.lpush("name", "zha", "ad");
        System.out.println(lpush);
        //同lpush,但是必须要保证这个key存在
        Long lpushx = jedis.lpushx("name", "ads");
        System.out.println(lpushx);
        //将某个值加入到一个key列表末尾
        Long rpushx = jedis.rpushx("name", "ad");
        System.out.println(rpushx);
        //返回和移除列表的第一个元素
        String name = jedis.lpop("name");
        System.out.println(name);
        //返回和移除列表的第一个元素
        String name1 = jedis.rpop("name");
        System.out.println(name1);
        //设置某一个指定索引的值(索引必须存在)
        String lset = jedis.lset("name", 1, "ads");
        System.out.println(lset);
        //获取某一个下标区间内的元素
        List<String> name2 = jedis.lrange("name", 0, 9);
        System.out.println(name2);
        //获取某一个指定索引位置的元素
        String name3 = jedis.lindex("name", 2);
        System.out.println(name3);
        //保留列表中特定间内的元素
        String name4 = jedis.ltrim("name", 1, 5);
        System.out.println(name4);
        Long linsert = jedis.linsert("name", BinaryClient.LIST_POSITION.AFTER, "za", "zhaolin");
        System.out.println(linsert);
    }
    @Test
    public void testSet(){
        //为集合添加元素
        Long sadd = jedis.sadd("set", "xiaoming", "adsad", "sdad");
        System.out.println(sadd);
        //返回集合中元素个数
        Long name1 = jedis.scard("set");
        System.out.println(name1);
        //随机返回一个元素
        String name2 = jedis.spop("set");
        System.out.println(name2);
        //从一个集合向另一个集合移动元素
        Long smove = jedis.smove("set", "sq", "xiaoming");
        System.out.println(smove);
        //从集合中删除一个元素
        Long srem = jedis.srem("set", "sdad");
        System.out.println(srem);
        //判断一个集合中是否含有这个元素
        Boolean sismember = jedis.sismember("set", "xiaoming");
        System.out.println(sismember);
        //随机返回元素
        String set = jedis.srandmember("set");
        System.out.println(set);
        jedis.sadd("sqq","adsad");
        //去掉第一个集合中其它集合含有的相同元素
        Set<String> sdiff = jedis.sdiff("set", "sqq");
        for (String s : sdiff) {
            System.out.println(s);
        }
        //求交集
        Set<String> sinter = jedis.sinter("set", "sqq");
        for (String s : sinter) {
            System.out.println(s);
        }
        //求和集
        Set<String> sunion = jedis.sunion("set", "sqq");
        for (String s : sunion) {
            System.out.println(s);
        }
    }
    @Test
    public void testZset() {
        //添加一个有序集合元素
        Long zadd = jedis.zadd("zadd", 1, "adads");
        System.out.println(zadd);
        //返回集合的元素个数
        Long zadd1 = jedis.zcard("zadd");
        System.out.println(zadd1);
        //返回一个范围内的元素
        Set<String> zadd2 = jedis.zrange("zadd", 0, -1);
        for (String s : zadd2) {
            System.out.println(s);
        }
        // 按照分数查找一个范围内的元素
        Set<String> zadd3 = jedis.zrangeByScore("zadd", -1, 1);
        System.out.println(zadd3);
        //返回排名
        Long zrank = jedis.zrank("zadd", "adads");
        System.out.println(zrank);
        //倒序排名
        Long zrevrank = jedis.zrevrank("zadd", "adads");
        System.out.println(zrevrank);
        //显示某一个元素的分数
        Double zscore = jedis.zscore("zadd", "adads");
        System.out.println(zscore);
        //移除某一个元素
        Long zrem = jedis.zrem("zadd", "adads");
        System.out.println(zrem);
        //给某个特定元素加分
        Double zincrby = jedis.zincrby("zadd", 2, "aa");
        System.out.println(zincrby);
    }
    @Test
    public void testHash(){
        //设置一个key/value对
        Long hset = jedis.hset("map", "1", "zhaolin");
        jedis.hset("map","2","sd");
        System.out.println(hset);
        //获得一个key对应的value
        String map = jedis.hget("map", "1");
        System.out.println(map);
        //获得所有的key/value对
        Map<String, String> map1 = jedis.hgetAll("map");
        for (String s : map1.keySet()) {
            System.out.println(s);
        }
        //判断一个key是否存在
        Boolean map2 = jedis.hexists("map", "1");
        System.out.println(map2);
    //设置多个key/value
        Map<String,String>  map3=new HashMap<String,String>();
        map3.put("a","zzz");
        String aa = jedis.hmset("aa", map3);
        System.out.println(aa);
        //删除某一个key/value对
        Long map4 = jedis.hdel("map", "1");
        System.out.println(map4);
        //判断一个key是否存在
        Boolean map5 = jedis.hexists("map", "2");
        System.out.println(map5);
        //获得所有的key
        Set<String> map6 = jedis.hkeys("map");
        for (String s : map6) {
            System.out.println(s);
        }
        Long hset1 = jedis.hset("io", "1", "s");
        System.out.println(hset1);
        //为value进行加法运算
        Long io = jedis.hincrBy("map", "2", 1);
        System.out.println(io);
        //为value加入浮点值
        Double io1 = jedis.hincrByFloat("map", "2", 2);
        System.out.println(io1);
    }


}
