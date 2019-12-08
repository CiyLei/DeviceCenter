package com.ciy.device_center.handler;

import com.ciy.device_center.DCProtoHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 协议解码
 */
public class DCDecoderHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        DCProtoHeader header = new DCProtoHeader(byteBuf);
        list.add(header);
        byteBuf.discardReadBytes();
    }
}
