package com.hzcloud.web.controller.common;

import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.common.utils.encrypt.DESUtil;
import com.hzcloud.common.utils.encrypt.TripleDESUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "keyapi")
public class KeyController {

    // 用作三层密钥生成的分散因子，可以后续修改或添加人工修改机制
    public static String RootFactor = "root-7q1v8e4m-";
    public static String DictFactor = "dict-9r4s2v1t7d8r3v5s9m-";
    public static String FileFactor = "file-d1e5f8b3s8d4f2b7r6-";

    @GetMapping(value = "/getKey")
    public AjaxResult getKey() {
        AjaxResult ajax = AjaxResult.success();
        String id = StringUtils.getOriginKeyId(); // 生成原始随机数据用于分散密钥
        String RootKey = DESUtil.encrypt(RootFactor, id); // 卡原始密钥
        String DictKey = TripleDESUtil.encrypt(DictFactor, RootKey); // 目录原始密钥
        String FileKey = TripleDESUtil.encrypt(FileFactor, DictKey); // 文件原始密钥
        // 转为16进制字符串
        String RootHex = StringUtils.str2HexStr(RootKey);
        String DictHex = StringUtils.str2HexStr(DictKey);
        String FileHex = StringUtils.str2HexStr(FileKey);
        // 密钥的要求是32位16进制字符串，从原始密钥中按规律提取其中的部分位，生成密钥
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 48; ++i) {
            if (i >= 16) ++i;
            sb.append(RootHex.charAt(i));
        }
        ajax.put("rootKey", sb.toString());
        sb.setLength(0);
        for (int i = 1; i < 64; i += 2) {
            sb.append(DictHex.charAt(i));
        }
        ajax.put("dictKey", sb.toString());
        sb.setLength(0);
        for (int i = 1; i < 64; i += 2) {
            sb.append(FileHex.charAt(i));
        }
        ajax.put("fileKey", sb.toString());
        return ajax;
    }
}