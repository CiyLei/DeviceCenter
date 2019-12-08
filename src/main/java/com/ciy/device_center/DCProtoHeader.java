package com.ciy.device_center;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.*;

public class DCProtoHeader {

    // 当前客户端版本
    public static final int CURRENT_CLIENT_VERSION = 200;
    // 协议头长度
    private static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;

    // 包头长度
    private int headLength;
    // 版本信息
    private int clientVersion;
    // 命令值
    private int cmdId;
    // 回应码
    private int seq;
    // 包内容
    private byte[] body;

    public DCProtoHeader(int cmdId, int seq, byte[] body) {
        this.cmdId = cmdId;
        this.seq = seq;
        this.body = body;
    }

    public DCProtoHeader(ByteBuf data) {
        InputStream socketInput = new ByteBufInputStream(data);
        final DataInputStream dis = new DataInputStream(socketInput);

        try {
            headLength = dis.readInt();
            clientVersion = dis.readInt();
            cmdId = dis.readInt();
            seq = dis.readInt();
            int bodyLen = dis.readInt();

            if (clientVersion == CURRENT_CLIENT_VERSION) {
                if (bodyLen > 0) {
                    body = new byte[bodyLen];
                    dis.readFully(body);
                } else {
                    switch (cmdId) {
                        // 心跳包
                        case 6:
                            break;
                    }
                }
                socketInput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] encode() {
        final int headerLength = FIXED_HEADER_SKIP;
        final int bodyLength = (body == null ? 0 : body.length);
        final int packLength = headerLength + bodyLength;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(packLength);

        try {
            final DataOutputStream dos = new DataOutputStream(baos);

            dos.writeInt(headerLength);
            dos.writeInt(CURRENT_CLIENT_VERSION);
            dos.writeInt(cmdId);
            dos.writeInt(seq);
            dos.writeInt(bodyLength);

            if (body != null) {
                dos.write(body);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                baos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    public int getHeadLength() {
        return headLength;
    }

    public void setHeadLength(int headLength) {
        this.headLength = headLength;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getCmdId() {
        return cmdId;
    }

    public void setCmdId(int cmdId) {
        this.cmdId = cmdId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static DCProtoHeader cmdProto(int cmdId) {
        return new DCProtoHeader(cmdId, 0, new byte[]{1});
    }
}
