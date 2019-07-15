package com.landleaf.ibsaas.client.light.client;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.light.handle.netty.NettyChannelPoolHandler;
import com.landleaf.ibsaas.common.tcp.code.Hex;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by YuQi on 2017/7/31.
 */
@Component
public class NettyPoolClient {
    final EventLoopGroup group = new NioEventLoopGroup();
    final Bootstrap strap = new Bootstrap();
    private Map<String,InetSocketAddress> hostMap = Maps.newHashMapWithExpectedSize(4);

    public ChannelPoolMap<InetSocketAddress, SimpleChannelPool> poolMap;
    public void build(){
        strap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);

        poolMap = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(strap.remoteAddress(key), new NettyChannelPoolHandler(), 2);
            }
        };
        InetSocketAddress host4 = new InetSocketAddress("192.168.10.170", 4196);
        InetSocketAddress host3 = new InetSocketAddress("192.168.10.173", 4196);
        hostMap.put("3",host3);
        hostMap.put("4",host4);
    }


    public InetSocketAddress getHost(String host){
        InetSocketAddress inetSocketAddress = hostMap.get(host);
        if (inetSocketAddress == null){
            throw new IllegalArgumentException("not found InetSocketAddress for host :"+host);
        }
        return  inetSocketAddress;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        NettyPoolClient client = new NettyPoolClient();
        client.build();
        final String ECHO_REQ = "Hello Netty.$_";
        final String s = ".R1S1!.";
//        for (int i = 0; i < 10; i++) {
            // depending on when you use addr1 or addr2 you will get different pools.
            final SimpleChannelPool pool = client.poolMap.get(client.getHost("2"));
            Future<Channel> f = pool.acquire();
            f.addListener((FutureListener<Channel>) f1 -> {
                if (f1.isSuccess()) {
                    Channel ch = f1.getNow();

                    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                    byte[] b = {(byte)0x02,(byte)0x52,(byte)0x31 ,(byte)0x53 ,(byte)0x31 ,(byte)0x21 ,(byte)0x03};
                    buffer.writeBytes(Hex.toBytes("02523153302103"));
                    ch.writeAndFlush(Hex.toBytes("02523153312103"));
                    // Release back to pool
                    pool.release(ch);
                }
            });
//        }
    }





    /**
     * @Title:hexString2Bytes
     * @Description:16进制字符串转字节数组
     * @param src  16进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    //charToByte返回在指定字符的第一个发生的字符串中的索引，即返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }






    }
