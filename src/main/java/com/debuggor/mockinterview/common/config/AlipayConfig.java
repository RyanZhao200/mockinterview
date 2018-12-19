package com.debuggor.mockinterview.common.config;

/**
 * 支付宝配置
 */
public class AlipayConfig {

    /**
     * 在后台获取（必须配置）
     */
    public static String app_id = "2016092400584890";
    /**
     * 教程查看获取方式（必须配置）
     */
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQClOZ3yJbFihbQnEJtUnilpFaNFBGpmTQHpmDq/VNPboh9yvhm3PRuAApxNZEwXB5lZhWUm9tiGquJRoFvjxEGP85wJXV7LBuT+kPuIXGO8mvHW/CgWM1t6gBLKJH2R5kvFeq93UrStM6Z6idRx5B2mrlZUqsZ6Pss4prrwBjCP4L1FKAYIWilT4Rh+BiKwah2iGsOgq4pHK3NgvglrXJ5qCYz/jp3Kq4nP959AQASZCt99UL+K07YIHZyVPBacyqdmj5NUKeqj112oLfLPJUAI33VcS/qFJoQW7MWT0h481g00tzsTUKbmN8HAy5mHQIZRFlGoYGhO28lXW+D429FpAgMBAAECggEAZ4DrmAjS0ZROofY+SEj4ShM6/35fpQxyuUIa80cRnNI3/yplc6u9MXjxL244WGjzQOhW0IzsYZhlRJs/VTTZmXMTea5bNrZv96UdXXP7KuipvXKjLh0oHlpi6uznGOH675AGoapW8vWbQcJcKqirhoJKJKoCwgvaRPf1vW0zOL2eVY+LnWVjGw3fCnLVe5JqvQdta+2ZUay0nIc74nNsvUFuAACeTDsPpKBrwbSFElzbrvIAL4HOrrS4cLZJmw7CLrZtYfLca2U/eYoOvFkQ6ZPB59SWrHLx3pJEKosRaM+MYtM2uYdz0O2bgxHCc67d2BFYGcSckQ57yCOLSGvKAQKBgQDog1GmcEaDWdaa3O46GbDD95odrW0bCE7eYj4qqhEPkKVwkS/DDCMtKF4qjW+Tm6b3q2tsCydYMhHaFoTOkm8zJ6sZArEkMzPQBQGHRpax2piKLjLfvX8rT/VkD3Dj5TGSCSWU3bz633VsLnTXu2XqFnDt+UCiofWJB2k0Qc27wQKBgQC16kNdABxGc6ce4NcPM/KPM5ouEdqggsXSDRKqIqR2jrnF9dI9wMmmF7H/HTpqbK0kQx9nNMJ1N1e+vqaOQ090le/R1Oi+kbOnfCq6f2xLtLeffUx9EG/xKg+GzPKQ7G3OhK76UNmYW23Iq1EXhqTLVseVgvWnvzNOBTaG8+6fqQKBgCHEoKEZgUQmUgKfv4F0pbVwSlOxEj1w0XN+tSShGmU2TrCwhU1AgANY74QikmfPnUou/STt4cLvUqRnzNXrcnbtde3l9DAbt5SohXZODCi8ZJb86qqP7N4veKYVkAGpEa/x4CFkqsOMnJMflByYvM5of/Iv1vFHsE88Yk0SmXyBAoGAMuKgjTcmsZ5QNRb3kU0aoGvyyMg9u4BZDXSSr/yYvIEX/gPRF20B+u/N3mqymGpl1CriceAUNO2SIcz6kfjKFPaL6sn/gK8I7eSRnzFdfZ/DbV2DM8iRR4hYZ4CUqW05GfeLaoGLHE8W2nbxa/9BwY84rN3BEXcuRri2k/FP4mECgYB/P0YWyGXy5mS9jRb4mcr2CRBhAPgHKcUZBAiFPI5beMM/lU7A4pHBnitwtXRSTsHIWGoEtHGr5c7nxj8u3fQhEtMLBuYO1n10JJaYwd/F/p4LpiPjQV2nMRQy1Q6XAWJggycFOrhZygrQOsZLuNxvU3tRcAxzWTHA1S3z0vpTYw==";
    /**
     * 教程查看获取方式（必须配置）
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA02ndSHdbllVUMKBDeF7NVU+wEICCb9H3GaFOAsZ6DbtGnNXop1mHST0GcBmKICmW7Of5lFfNJDSgMVtrA5f0qo0xVGnr/8nmJVghJpAu4xrjlo6zAHQcI9WtPmAUUYkEYuWl9cVDX1aZECLCawXe7ae+alfjo0DejmZrAzGtaUHJ+XLJ2LkilXki8HUMng1xZeY7nCa6Mjr3bSsArT0PJAlgjw0/hm3rZcEsaMAbKjjMz/sxxVzuueTWDhmFBfAbwu9yg/UPNEim+at7TNwKoe3KtAsotBmWmjNT11PfOcVDlssELVwk8F3ZTCco4g+NADIpPTvPaynMoBcsSFZkKQIDAQAB";

    public static String notify_url = "http://localhost:8080/pay/alipayNotifyNotice";

    public static String return_url = "http://localhost:8080/pay/alipayReturnNotice";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";
    /**
     * 注意：沙箱测试环境，正式环境为：https://openapi.alipay.com/gateway.do
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
