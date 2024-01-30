package cn.originmc.plugins.polygon.controller.flag;

import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
import cn.originmc.plugins.polygon.core.player.manager.TerritoryMemberManager;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;

public class FlagsGenerator {


    public static NormalFlags createVisitorFlags() {
        NormalFlags normalFlags = new NormalFlags("visitor");
        NormalFlag flagBreak = new NormalFlag("break");
        flagBreak.addValue("enable", "false");
        NormalFlag flagPlace = new NormalFlag("place");
        flagPlace.addValue("enable", "false");
        NormalFlag flagInteract = new NormalFlag("use");
        flagInteract.addValue("enable", "false");
        NormalFlag flagDamage = new NormalFlag("damage");
        flagDamage.addValue("enable", "false");
        NormalFlag flagMove = new NormalFlag("move");
        flagMove.addValue("enable", "true");
        NormalFlag flagMessage = new NormalFlag("message");
        flagMessage.addValue("join", LangData.getServerText("visitor-join-message", "&a你作为访客进入了领地 &b~"));
        flagMessage.addValue("leave", LangData.getServerText("visitor-leave-message", "&a你离开了领地 &b~"));
        NormalFlag flagTp=new NormalFlag("tp");
        flagTp.addValue("enable","false");
        normalFlags.addFlag(flagBreak);
        normalFlags.addFlag(flagPlace);
        normalFlags.addFlag(flagInteract);
        normalFlags.addFlag(flagDamage);
        normalFlags.addFlag(flagMove);
        normalFlags.addFlag(flagMessage);
        normalFlags.addFlag(flagTp);
        return normalFlags;
    }

    public static NormalFlags createMemberFlags() {
        NormalFlags normalFlags = new NormalFlags("member");
        NormalFlag flagBreak = new NormalFlag("break");
        flagBreak.addValue("enable", "true");
        NormalFlag flagPlace = new NormalFlag("place");
        flagPlace.addValue("enable", "true");
        NormalFlag flagInteract = new NormalFlag("use");
        flagInteract.addValue("enable", "true");
        NormalFlag flagDamage = new NormalFlag("damage");
        flagDamage.addValue("enable", "false");
        NormalFlag flagMove = new NormalFlag("move");
        flagMove.addValue("enable", "true");
        NormalFlag flagMessage = new NormalFlag("message");
        flagMessage.addValue("join", LangData.getServerText("member-join-message", "&a欢迎回到 &b~"));
        flagMessage.addValue("leave", LangData.getServerText("member-leave-message", "&a一路顺风 &b~"));
        NormalFlag flagTp=new NormalFlag("tp");
        flagTp.addValue("enable","false");
        normalFlags.addFlag(flagBreak);
        normalFlags.addFlag(flagPlace);
        normalFlags.addFlag(flagInteract);
        normalFlags.addFlag(flagDamage);
        normalFlags.addFlag(flagMove);
        normalFlags.addFlag(flagMessage);
        normalFlags.addFlag(flagTp);
        return normalFlags;
    }

    public static NormalFlags createDefaultMemberFlags() {
        NormalFlags flags = new NormalFlags("default");
        NormalFlag flag = new NormalFlag("group");
        flag.addValue("value", "member");
        flags.addFlag(flag);
        return flags;
    }

    public static NormalFlags createDefaultOwnerFlags() {
        NormalFlags flags = new NormalFlags("default");
        NormalFlag flag = new NormalFlag("group");
        flag.addValue("value", "owner");
        flags.addFlag(flag);
        return flags;
    }

    public static NormalFlags createDefaultAdminFlags() {
        NormalFlags flags = new NormalFlags("default");
        NormalFlag flag = new NormalFlag("group");
        flag.addValue("value", "admin");
        flags.addFlag(flag);
        return flags;
    }
    public static String getGroup(TerritoryMember territoryMember){
        NormalFlags flags = (NormalFlags) territoryMember.getFlags("default");
        NormalFlag flag = (NormalFlag) flags.getFlag("group");
        return flag.getValue("value");
    }
    public static NormalFlag getMemberFlag(Territory territory,String id,String flagName){
        TerritoryMemberManager territoryMemberManager=territory.getTerritoryMemberManager();
        TerritoryMember territoryMember=territoryMemberManager.getMember(id);
        if (territoryMember==null){
            return (NormalFlag) territory.getFlags("visitor").getFlag("tp");
        }
        NormalFlags flags = (NormalFlags) territoryMember.getFlags("default");
        NormalFlag flag = (NormalFlag) flags.getFlag(flagName);
        if (flag!=null){
            return flag;
        }
        String group=getGroup(territoryMember);
        if(territory.hasFlagsSpace(group)){
            flags = (NormalFlags) territory.getFlags(group);
            flag = (NormalFlag) flags.getFlag(flagName);
            return flag;
        }
        return null;
    }

}
