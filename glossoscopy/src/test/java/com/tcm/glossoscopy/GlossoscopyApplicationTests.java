package com.tcm.glossoscopy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class GlossoscopyApplicationTests {
//    @Resource
//    private AliOssUtil aliOssUtil;
//    @Resource
//    private AliSmsUtil aliSmsUtil;
    //    @Test
//    void testAliOss(){
//        String url="https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/0056aaf5-3526-4676-8461-3c73380f7ca3.png";
//        String objectName = url.substring(url.lastIndexOf('/')+1);
//        String base64Image = aliOssUtil.getImageAsBase64(objectName);
//        System.out.println(base64Image);
//    }
//    @Test
//    void testAliSms(){
//        String phoneNumber = "18673578538";
//        aliSmsUtil.sendMessage(phoneNumber,"040621");
//    }

//    @Resource
//    private MongoTemplate mongoTemplate;
//    @Resource
//    private TestRepository testRepository;
//
//    @org.junit.jupiter.api.Test
//    void test(){
//        mongoTemplate.save(Test.builder().id(2L).name("itxiaoqi").createTime(LocalDateTime.now()).build());
//        testRepository.save(Test.builder().id(3L).name("itxiaoqi").createTime(LocalDateTime.now()).build());
//    }

//    @Test
//    public void testLeapYearFeb28() {
//        String result = NextDateCalculator.getNextDate(2020, 2, 28);
//        assertEquals("2020/2/29", result);
//    }
//
//    @Test
//    public void testNonLeapYearFeb28() {
//        String result = NextDateCalculator.getNextDate(2021, 2, 28);
//        assertEquals("2021/3/1", result);
//    }
//
//    @Test
//    public void testLeapYearFeb29() {
//        String result = NextDateCalculator.getNextDate(2020, 2, 29);
//        assertEquals("2020/3/1", result);
//    }
//
//    @Test
//    public void testYearCrossing() {
//        String result = NextDateCalculator.getNextDate(2021, 12, 31);
//        assertEquals("2022/1/1", result);
//    }
//
//    @Test
//    public void testSmallMonthCrossing() {
//        String result = NextDateCalculator.getNextDate(2020, 4, 30);
//        assertEquals("2020/5/1", result);
//    }
//
//    @Test
//    public void testBigMonthCrossing() {
//        String result = NextDateCalculator.getNextDate(2020, 1, 31);
//        assertEquals("2020/2/1", result);
//    }
//
//    @Test
//    public void testInvalidMonthLessThan1() {
//        String result = NextDateCalculator.getNextDate(2020, 0, 1);
//        assertEquals("输入的日期不合法！", result);
//    }
//
//    @Test
//    public void testInvalidMonthGreaterThan12() {
//        String result = NextDateCalculator.getNextDate(2020, 13, 1);
//        assertEquals("输入的日期不合法！", result);
//    }
//
//    @Test
//    public void testInvalidDayLessThan1() {
//        String result = NextDateCalculator.getNextDate(2020, 1, 0);
//        assertEquals("输入的日期不合法！", result);
//    }
//
//    @Test
//    public void testInvalidDayExceedMax() {
//        String result = NextDateCalculator.getNextDate(2020, 4, 31);
//        assertEquals("输入的日期不合法！", result);
//    }
//
//    @Test
//    public void testMaxYear() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2147483648/1/1";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("2147483648/1/2", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testMinYear() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "-2147483649/1/1";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("输入格式错误，请输入有效的数字！", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testMaxMonth() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2022/2147483648/1";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("输入格式错误，请输入有效的数字！", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testMinMonth() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2022/-2147483649/1";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("输入格式错误，请输入有效的数字！", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testMaxDay() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2022/1/2147483648";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("输入格式错误，请输入有效的数字！", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testMinDay() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2022/1/-2147483649";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("输入格式错误，请输入有效的数字！", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testDateWithLeadingSpace() {
//        InputStream originalIn = System.in;
//        try {
//            String input = " 2022/1/1";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("2022/1/2", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testDateWithTrailingSpace() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "2022/1/1 ";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("2022/1/2", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
//
//    @Test
//    public void testNoInput() {
//        InputStream originalIn = System.in;
//        try {
//            String input = "\n";
//            System.setIn(new ByteArrayInputStream(input.getBytes()));
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            System.setOut(new java.io.PrintStream(outputStream));
//            // 执行程序
//            NextDateCalculator.main(new String[0]);
//            String output = outputStream.toString();
//            assertEquals("", output);
//        } finally {
//            System.setIn(originalIn);
//        }
//    }
}
