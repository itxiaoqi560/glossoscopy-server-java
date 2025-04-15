package com.tcm.glossoscopy.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @PostConstruct
    public static void initAllRule() {
        initDegradeRules();
        initFlowRules();
    }


    public static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("myResource");//资源名称
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//限流类型：QPS（每秒请求数）
        rule.setCount(20);//每秒最多允许20个请求
        rules.add(rule);
        FlowRuleManager.loadRules(rules);//加载规则
    }

    public static void initDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("myResource");//资源名称
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);//熔断类型：异常数
        rule.setCount(2);//异常数达到2次时熔断
        rule.setTimeWindow(10);//熔断时间窗口为10秒
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);//加载规则
    }
}