/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vnpay.sercurity;

/**
 *
 * @author thopd
 */
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class Base64 {

    private static final String LANGUAGE = "ISO8859_1";

    public Base64() throws NoSuchAlgorithmException {
    }

    static Object getEncoder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private static final int fillChar1 = 61;
    private static final int fillChar2 = 61;
    public String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public String encode(String paramString) {
        try {
            byte[] arrayOfByte = stringToByteArray(paramString);

            int j = arrayOfByte.length;
            StringBuilder localStringBuffer = new StringBuilder((j / 3 + 1) * 4);
            for (int k = 0; k < j; k++) {
                int i = arrayOfByte[k] >> 2 & 0x3F;
                localStringBuffer.append(this.cvt.charAt(i));
                i = arrayOfByte[k] << 4 & 0x3F;
                k++;
                if (k < j) {
                    i |= arrayOfByte[k] >> 4 & 0xF;
                }
                localStringBuffer.append(this.cvt.charAt(i));
                if (k < j) {
                    i = arrayOfByte[k] << 2 & 0x3F;
                    k++;
                    if (k < j) {
                        i |= arrayOfByte[k] >> 6 & 0x3;
                    }
                    localStringBuffer.append(this.cvt.charAt(i));
                } else {
                    k++;
                    localStringBuffer.append((char) this.fillChar1);
                }
                if (k < j) {
                    i = arrayOfByte[k] & 0x3F;
                    localStringBuffer.append(this.cvt.charAt(i));
                } else {
                    localStringBuffer.append((char) this.fillChar2);
                }
            }
            return localStringBuffer.toString();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public String decode(String paramString) {
        try {
            byte[] arrayOfByte = stringToByteArray(paramString);

            int k = arrayOfByte.length;
            StringBuilder localStringBuffer = new StringBuilder(k * 3 / 4);
            for (int m = 0; m < k; m++) {
                int i = this.cvt.indexOf(arrayOfByte[m]);
                m++;
                int j = this.cvt.indexOf(arrayOfByte[m]);
                i = i << 2 | j >> 4 & 0x3;
                localStringBuffer.append((char) i);
                m++;
                if (m < k) {
                    i = arrayOfByte[m];
                    if (this.fillChar1 == i) {
                        break;
                    }
                    i = this.cvt.indexOf((char) i);
                    j = j << 4 & 0xF0 | i >> 2 & 0xF;
                    localStringBuffer.append((char) j);
                }
                m++;
                if (m < k) {
                    j = arrayOfByte[m];
                    if (this.fillChar2 == j) {
                        break;
                    }
                    j = this.cvt.indexOf((char) j);
                    i = i << 6 & 0xC0 | j;
                    localStringBuffer.append((char) i);
                }
            }
            return localStringBuffer.toString();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public String encode(byte[] paramArrayOfByte) {
        try {
            int j = paramArrayOfByte.length;
            StringBuilder localStringBuffer = new StringBuilder((j / 3 + 1) * 4);
            for (int k = 0; k < j; k++) {
                int i = paramArrayOfByte[k] >> 2 & 0x3F;
                localStringBuffer.append(this.cvt.charAt(i));
                i = paramArrayOfByte[k] << 4 & 0x3F;
                k++;
                if (k < j) {
                    i |= paramArrayOfByte[k] >> 4 & 0xF;
                }
                localStringBuffer.append(this.cvt.charAt(i));
                if (k < j) {
                    i = paramArrayOfByte[k] << 2 & 0x3F;
                    k++;
                    if (k < j) {
                        i |= paramArrayOfByte[k] >> 6 & 0x3;
                    }
                    localStringBuffer.append(this.cvt.charAt(i));
                } else {
                    k++;
                    localStringBuffer.append((char) this.fillChar1);
                }
                if (k < j) {
                    i = paramArrayOfByte[k] & 0x3F;
                    localStringBuffer.append(this.cvt.charAt(i));
                } else {
                    localStringBuffer.append((char) this.fillChar2);
                }
            }
            return localStringBuffer.toString();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public String decode(byte[] paramArrayOfByte) {
        try {
            int k = paramArrayOfByte.length;
            StringBuilder localStringBuffer = new StringBuilder(k * 3 / 4);
            for (int m = 0; m < k; m++) {
                int i = this.cvt.indexOf(paramArrayOfByte[m]);
                m++;
                int j = this.cvt.indexOf(paramArrayOfByte[m]);
                i = i << 2 | j >> 4 & 0x3;
                localStringBuffer.append((char) i);
                m++;
                if (m < k) {
                    i = paramArrayOfByte[m];
                    if (this.fillChar1 == i) {
                        break;
                    }
                    i = this.cvt.indexOf((char) i);
                    j = j << 4 & 0xF0 | i >> 2 & 0xF;
                    localStringBuffer.append((char) j);
                }
                m++;
                if (m < k) {
                    j = paramArrayOfByte[m];
                    if (this.fillChar2 == j) {
                        break;
                    }
                    j = this.cvt.indexOf((char) j);
                    i = i << 6 & 0xC0 | j;
                    localStringBuffer.append((char) i);
                }
            }
            return localStringBuffer.toString();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public String byteArrayToString(byte[] paramArrayOfByte)
            throws UnsupportedEncodingException {
        if (paramArrayOfByte != null) {
            return new String(paramArrayOfByte, StandardCharsets.ISO_8859_1);
        }
        return null;
    }

    public byte[] stringToByteArray(String paramString)
            throws UnsupportedEncodingException {
        if (paramString != null) {
            return paramString.getBytes(StandardCharsets.ISO_8859_1);
        }
        return new byte[0];
    }

    private Random localRandom = SecureRandom.getInstanceStrong();
    public String createRandomString() {
        try {
            String str = "";
            byte[] arrayOfByte = stringToByteArray(this.cvt);
            for (int i = 64; i > 0; i--) {
                int j = Math.abs(this.localRandom.nextInt()) % i;

                char c = (char) arrayOfByte[j];
                arrayOfByte[j] = arrayOfByte[(i - 1)];
                str = str + c;
            }
            return str;
        } catch (Exception localException) {
            log.info(String.valueOf(localException));
        }
        return null;
    }

    public static void main(String[] paramArrayOfString) throws NoSuchAlgorithmException {
        Base64 localBase64 = new Base64();
        log.info(localBase64.encode("Dang Ha Trung"));
        localBase64.cvt = localBase64.createRandomString();
        log.info(localBase64.cvt);

        log.info(localBase64.encode("Dang Ha Trung"));
        log.info(localBase64.decode(localBase64.encode("Dang Ha Trung")));
    }
}
