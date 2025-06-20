import java.util.*;

public class Tset3 {
    public static void main(String[] args) {
        //斗地主发牌
        //先把牌弄出来，2个集合，一个集合装花色，一个集合装数字
        List<String> type=new ArrayList<>();
        type.add("♥");
        type.add("♠");
        type.add("♦");
        type.add("♣");
        List<String> typecount=new ArrayList<>();
        for(int i=3;i<=10;i++){
            typecount.add(""+i);
        }
        //把非数字牌添加进来
        typecount.add("J");
        typecount.add("Q");
        typecount.add("K");
        typecount.add("A");
        typecount.add("2");
        //创建牌的map，每张牌都有一个编号，编号从0-53，把每张牌装进去
        Map<Integer,String> poke=new HashMap<>();
        int index=0;
        for(String s:typecount){
            for(String sc:type){
                poke.put(index,s+sc);
                index++;
            }
        }
        poke.put(52,"大王");
        poke.put(53,"小王");
        System.out.println(poke);
        //创建一个牌编号集合，以便洗牌
        List<Integer> number=new ArrayList<>();
        for(int i=0;i<54;i++){
            number.add(i);
        }
        //洗牌，编号乱了
        Collections.shuffle(number);
        //需要4个集合来装牌对应的编号，3个玩家，1个底牌
        List<Integer> p1=new ArrayList<>();
        List<Integer> p2=new ArrayList<>();
        List<Integer> p3=new ArrayList<>();
        List<Integer> buttonpoke=new ArrayList<>();
        //发牌对应的编号
        for(int i=0;i<54;i++){
            if(i>=51){
                buttonpoke.add(number.get(i));
            }else{
                if(i%3==1){
                    p1.add(number.get(i));
                }else  if(i%3==2){
                    p2.add(number.get(i));
                }else  if(i%3==0){
                    p3.add(number.get(i));
                }
            }
        }
        //对编号排序
        Collections.sort(p1);
        Collections.sort(p2);
        Collections.sort(p3);
        Collections.sort(buttonpoke);
        //需要4个集合来装牌
        List<String> p11=new ArrayList<>();
        List<String> p22=new ArrayList<>();
        List<String> p33=new ArrayList<>();
        List<String> buttonpoke1=new ArrayList<>();
        //根据编号去牌库获取对应的牌
        for(Integer i:p1){
            p11.add(poke.get(i));
        }
        for(Integer i:p2){
            p22.add(poke.get(i));
        }
        for(Integer i:p3){
            p33.add(poke.get(i));
        }
        for(Integer i:buttonpoke){
            buttonpoke1.add(poke.get(i));
        }
        System.out.println("玩家1："+p11);
        System.out.println("玩家2："+p22);
        System.out.println("玩家3："+p33);
        System.out.println("底牌："+buttonpoke1);
    }
}