/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.alibaba.tuna.netty.handler.codec.rtsp;

import com.alibaba.tuna.netty.buffer.ByteBuf;
import com.alibaba.tuna.netty.handler.codec.http.FullHttpResponse;
import com.alibaba.tuna.netty.handler.codec.http.HttpResponse;
import com.alibaba.tuna.netty.util.CharsetUtil;

import static com.alibaba.tuna.netty.handler.codec.http.HttpConstants.*;

/**
 * Encodes an RTSP response represented in {@link FullHttpResponse} into
 * a {@link ByteBuf}.

 */
public class RtspResponseEncoder extends RtspObjectEncoder<HttpResponse> {
    private static final byte[] CRLF = { CR, LF };

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return msg instanceof FullHttpResponse;
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpResponse response)
            throws Exception {
        encodeAscii(response.getProtocolVersion().toString(), buf);
        buf.writeByte(SP);
        buf.writeBytes(String.valueOf(response.getStatus().code()).getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(SP);
        encodeAscii(String.valueOf(response.getStatus().reasonPhrase()), buf);
        buf.writeBytes(CRLF);
    }
}
