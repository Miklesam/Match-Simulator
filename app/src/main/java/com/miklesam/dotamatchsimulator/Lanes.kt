package com.miklesam.dotamatchsimulator

enum class Lanes (val positionX:Double,val positionY:Double){
    RADIANT_TOP(8.0,16.0),
    RADIANT_MID(38.0,46.0),
    RADIANT_BOT(72.0,81.0),
    DIRE_TOP(16.0,18.0),
    DIRE_MID(46.0,50.0),
    DIRE_BOT(81.0,84.0),
    DIRE_TOP_TIER_2(36.0,15.0),
    RADIANT_TOP_TIER_2(6.0,40.0),
    DIRE_TOP_TIER_3_RADIANT_SIDE(55.0,10.0),
    DIRE_TOP_TIER_3_DIRE_SIDE(60.0,14.0),
    RADIANT_TOP_TIER_3(6.0,56.0),
    RADIANT_ANCIENT_RADIANT_SIDE(5.0,76.0),
    RADIANT_ANCIENT_DIRE_SIDE(13.0,80.0),
    DIRE_ANCIENT_RADIANT_SIDE(71.0,15.0),
    DIRE_ANCIENT_DIRE_SIDE(80.0,17.0),
    DIRE_MID_TIER_2_RADIANT_SIDE(56.0,35.0),
    DIRE_MID_TIER_2_DIRE_SIDE(62.0,38.0),
    RADIANT_MID_TIER_2_RADIANT_SIDE(20.0,60.0),
    RADIANT_MID_TIER_2_DIRE_SIDE(25.0,65.0),
    RADIANT_MID_TIER_3_RADIANT_SIDE(12.0,70.0),
    RADIANT_MID_TIER_3_DIRE_SIDE(20.0,73.0),
    DIRE_MID_TIER_3_RADIANT_SIDE(65.0,22.0),
    DIRE_MID_TIER_3_DIRE_SIDE(73.0,26.0),
    DIRE_BOT_TIER_2_RADIANT_SIDE(81.0,45.0),
    DIRE_BOT_TIER_2_DIRE_SIDE(81.0,45.0),
    RADIANT_BOT_TIER_2_RADIANT_SIDE(38.0,81.0),
    RADIANT_BOT_TIER_2_DIRE_SIDE(50.0,90.0),
    RADIANT_BOT_TIER_3_RADIANT_SIDE(17.0,81.0),
    RADIANT_BOT_TIER_3_DIRE_SIDE(30.0,90.0),
    DIRE_BOT_TIER_3_RADIANT_SIDE(81.0,32.0),
    DIRE_BOT_TIER_3_DIRE_SIDE(81.0,32.0);

}