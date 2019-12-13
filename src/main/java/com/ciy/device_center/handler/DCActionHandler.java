package com.ciy.device_center.handler;

import com.ciy.device_center.DCProtoHeader;
import com.ciy.device_center.component.IDeviceCenter;
import com.ciy.device_center.model.DeviceAppModel;
import com.ciy.device_center.proto.AppProto;
import com.ciy.device_center.utils.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * 处理命令
 */
public class DCActionHandler extends ChannelInboundHandlerAdapter {

    public final static int SEND_DEVICE_APP_CMDID = 10006;
    public final static int SEND_SHOCK_CMDID = 10007;
    public final static int CMDID_NOOPING = 6;
    public static AttributeKey<DeviceAppModel> DEVICE_MODEL_ATTRIBUTE_KEY = AttributeKey.valueOf("deviceModelAttributeKey");

    IDeviceCenter deviceCenter = SpringUtil.getBean(IDeviceCenter.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DCProtoHeader) {
            DCProtoHeader protoHeader = (DCProtoHeader) msg;
            switch (protoHeader.getCmdId()) {
                case SEND_DEVICE_APP_CMDID:
                    // 新设备加入
                    AppProto.Info appInfo = AppProto.Info.parseFrom(protoHeader.getBody());
                    DeviceAppModel deviceAppModel = new DeviceAppModel(appInfo, ctx);
                    ctx.channel().attr(DEVICE_MODEL_ATTRIBUTE_KEY).set(deviceAppModel);
                    deviceCenter.addAppInfo(deviceAppModel);
                    // 随便给个回复，不然会断
                    ctx.writeAndFlush(new DCProtoHeader(protoHeader.getCmdId(), protoHeader.getSeq(), new byte[] {1}));
                    break;
                case CMDID_NOOPING:
                    // 心跳包
                    ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(protoHeader.encode()));
                    break;
            }
        }
    }

    /**
     * 设备离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        DeviceAppModel deviceAppModel = ctx.channel().attr(DEVICE_MODEL_ATTRIBUTE_KEY).get();
        if (deviceAppModel != null) {
            deviceCenter.removeAppInfo(deviceAppModel);
        }
    }
}
