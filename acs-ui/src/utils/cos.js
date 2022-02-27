import CryptoJS from 'crypto-js'

/**
 * 获取八位随机数指令
 * 00 84 00 00 08
 * @return
 */
export function get8Random () {
  var cla = "00";
  var ins = "84";
  var p1 = "00";
  var p2 = "00";
  var lc = "08";
  var data = "";
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 外部认证指令
 * 00 82 00 00 08 13C6B3B56914A6F5
 * @param Random 8字节加密后的随机数
 * @return
 */
export function externalAuthentication(Random) {
  var cla = "00";
  var ins = "82";
  var p1 = "00";
  var p2 = "00";
  var lc = "08";
  var data = Random;
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 擦除当前目录指令
 * 80 0E 00 00 00
 * @return
 */
export function eraseDf() {
  var cla = "80";
  var ins = "0E";
  var p1 = "00";
  var p2 = "00";
  var lc = "00";
  var data = "";
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 选择文件
 * 00 A4 00 00 02 3F00
 * @param fileName 目录标识符
 * @return
 */
export function selectFile(fileName) {
  var cla = "00";
  var ins = "A4";
  var p1 = "00";
  var p2 = "00";
  var lc = "02";
  var data = fileName;
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 创建KEY文件
 * 80 E0 0000 07 3F 0153 95 F0 FFFF
 * 80 E0 0000 07 3F 0153 95 F0 FFFF
 * @return
 */
export function createKey () {
  var cla = "80";
  var ins = "E0";
  var p1P2 = '0000';
  var lc = "07";
  var data = "3F015395F0FFFF";
  var le = "";
  return cla+ins+p1P2+lc+data+le;
}

/**
 * 装载密钥
 * 80 D4 01 00 15 F9 F0F0 AA 88 FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
 * 80 D4 01 00 15 F9 F0F0 0A FF 22222222222222222222222222222222
 * @param key
 * @return
 */
export function loadKey (key) {
  var cla = "80";
  var ins = "D4";
  var p1 = '01';
  var p2 = '00';
  var lc = "15";
  var data = "F9F0F0AA88"+key;
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 创建 DF
 * 80E0 DF01 11 38 07FF FAFA 95 7FFF A00000000386980701
 * @param fileName 文件标识符
 * @return
 */
export function createDf (fileName) {
  var cla = "80";
  var ins = "E0";
  var p1P2 = fileName;
  var lc = "11";
  var data = "3807FFFAFA957FFFA00000000386980701";
  var le = "";
  return cla+ins+p1P2+lc+data+le;
}

/**
 * 创建可变长记录文件
 * 80 E0 0011 07 AC0020F0F0FFFF
 * @param fileName
 * @return
 */
export function createfile (fileName) {
  var cla = "80";
  var ins = "E0";
  var p1P2 = fileName;
  var lc = "07";
  var data = "AC0020F0F0FFFF";
  var le = "";
  return cla+ins+p1P2+lc+data+le;
}

/**
 * 写文件
 * 00 E2 00 00 04 01 02 03 04
 * @param d 写入数据
 * @return
 */
export function writeFile(d) {
  var cla = "00";
  var ins = "E2";
  var p1 = "00";
  var p2 = "00";
  var lc = "";
  if (d.length/2<16) {
    lc = lc + '0' + (d.length/2).toString(16)
  } else {
    lc = lc + (d.length/2).toString(16)
  }
  var data = d;
  var le = "";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * 读定长记录文件
 * 00 B2 01 04 00
 * @return
 */
export function readFile() {
  var cla = "00";
  var ins = "B2";
  var p1 = "01";
  var p2 = "04";
  var lc = "";
  var data = "";
  var le = "00";
  return cla+ins+p1+p2+lc+data+le;
}

/**
 * DES 加密
 * @param data 加密前的8字节随机数
 * @param key 加密密钥
 * @returns {string} 加密后的8字节随机数
 * @constructor
 */
export function DesEncrypt (data, key) {
  var keyHex = CryptoJS.enc.Hex.parse(key);
  var message = CryptoJS.enc.Hex.parse(data);
  var encrypted = CryptoJS.DES.encrypt(message, keyHex, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
  var encryptedStr = encrypted.ciphertext.toString().toUpperCase().substr(0, 16);
  return encryptedStr;
}
export function MacCalculate (data, processKey, icv) {
  var keyHex = CryptoJS.enc.Hex.parse(processKey);
  var icvBtyes = CryptoJS.enc.Hex.parse(icv);
  var message = CryptoJS.enc.Hex.parse(data + '80');
  var dataLength = message.sigBytes - 1;
  var blockCount = parseInt(dataLength / 8) + 1;
  //第一个8位与初始偏移量进行异或计算
  var desXorBytes = [];
  var desXor0 = message.words[0] ^ icvBtyes.words[0];
  var desXor1 = message.words[1] ^ icvBtyes.words[1];
  desXorBytes[0] = desXor0;
  desXorBytes[1] = desXor1;
  var desXor = CryptoJS.lib.WordArray.create();
  desXor.words = desXorBytes || [];
  desXor.sigBytes = 8;
  for (var i = 1; i < blockCount; i++) {
    var encrypted = CryptoJS.DES.encrypt(desXor, keyHex, { iv: CryptoJS.enc.Hex.parse("0000000000000000"), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
    var desStr = encrypted.ciphertext.toString().toUpperCase().substr(0, 16);
    var des = CryptoJS.enc.Hex.parse(desStr);
    desXorBytes = [];
    desXor0 = message.words[2 * i] ^ des.words[0];
    desXorBytes[0] = desXor0;
    if (typeof (message.words[2 * i + 1]) != "undefined") {
      desXor1 = message.words[2 * i + 1] ^ des.words[1];
      desXorBytes[1] = desXor1;
    } else {
      desXor1 = des.words[1];
      desXorBytes[1] = desXor1;
    }
    desXor = CryptoJS.lib.WordArray.create();
    desXor.words = desXorBytes || [];
    desXor.sigBytes = 8;
  }
  var encrypted = CryptoJS.DES.encrypt(desXor, keyHex, { iv: CryptoJS.enc.Hex.parse("0000000000000000"), mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
  var desStr = encrypted.ciphertext.toString().toUpperCase().substr(0, 8);
  return desStr;
}
