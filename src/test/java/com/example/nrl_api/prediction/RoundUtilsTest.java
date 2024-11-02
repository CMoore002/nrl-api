package com.example.nrl_api.prediction;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundUtilsTest {

    @Test
    public void testGetRoundOrder_Standard(){
        int index = RoundUtils.getRoundOrder("Round 5");
        assertThat(index).isEqualTo(4);
    }

    @Test
    public void testGetRoundOrder_Finals(){
        int index = RoundUtils.getRoundOrder("Finals Week 2");
        assertThat(index).isEqualTo(28);
    }

    @Test
    public void testGetRoundOrder_InvalidRound(){
        int index = RoundUtils.getRoundOrder("Round 50");
        assertThat(index).isEqualTo(-1);
    }
}
