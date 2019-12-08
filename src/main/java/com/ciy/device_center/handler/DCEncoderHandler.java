package com.ciy.device_center.handler;

import com.ciy.device_center.DCProtoHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码协议
 */
public class DCEncoderHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof DCProtoHeader) {
            byteBuf.writeBytes(((DCProtoHeader) o).encode());
        }
    }
}
