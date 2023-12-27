package cn.originmc.plugins.polygon.controller.flag;

import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
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
        normalFlags.addFlag(flagBreak);
        normalFlags.addFlag(flagPlace);
        normalFlags.addFlag(flagInteract);
        normalFlags.addFlag(flagDamage);
        normalFlags.addFlag(flagMove);
        normalFlags.addFlag(flagMessage);
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
        normalFlags.addFlag(flagBreak);
        normalFlags.addFlag(flagPlace);
        normalFlags.addFlag(flagInteract);
        normalFlags.addFlag(flagDamage);
        normalFlags.addFlag(flagMove);
        normalFlags.addFlag(flagMessage);
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

}
