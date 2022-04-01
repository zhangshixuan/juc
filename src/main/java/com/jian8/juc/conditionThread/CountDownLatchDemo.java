package com.jian8.juc.conditionThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
//        general();
//        countDownLatchTest();
        changjing1();
    }

    //场景1：让多个线程等待
    /**
     * >（1）在这个场景中，CountDownLatch充当的是一个发令枪的角色；
     * 就像田径赛跑时，运动员会在起跑线做准备动作，等到发令枪一声响，运动员就会奋力奔跑。
     *
     * >（2）我们通过CountDownLatch.await()，让多个参与者线程启动后阻塞等待，
     * 然后在主线程 调用CountDownLatch.countdown(1) 将计数减为0，
     * 让所有线程一起往下执行；以此实现了多个线程在同一时刻并发执行，来模拟并发请求的目的。
     */
    public static void changjing1() throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            new Thread(()->{
                try {
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + "\t运动员" + index + "号\t开始跑了");
                } catch (InterruptedException e) {

                }
            },"线程" + i).start();
        }
        Thread.sleep(1000);
        countDownLatch.countDown();
    }

    //场景2：和让单个线程等待。
    //5个人都下班，才算真正的下班
    public static void changjing2() throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " --> 员工" + index + "号\t收拾东西下班");
                countDownLatch.countDown();
            },"线程" + i).start();
        }
        countDownLatch.await();
        System.out.println("所有人都走了，可以下班了，嘿嘿^_^");
    }

    public static void general(){
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教室");
            }, "Thread-->"+i).start();
        }
        while (Thread.activeCount()>2){
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println(Thread.currentThread().getName()+"\t=====班长最后关门走人");
    }

    public static void countDownLatchTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t=====秦统一");
    }
}
