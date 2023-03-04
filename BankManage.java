package com.song;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: song
 * @Date: 2023/02/24/13:00
 * @Description: 账户类Account（卡号accountId、姓名accountName、性别accountSex、密码accountPassword、余额accountBalance、每次取现额度withdrawalAmount）
 */
public class BankManage {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Account> accountList = new ArrayList<>();
    //默认没有上锁
    private boolean isLock = false;
    private Account accountLock;

    public BankManage() {
        Account account = new Account("11111111", "宋一", "男", "1", 1000, 500);
        accountList.add(account);
    }

    //欢迎界面
    public void start() {
        while (true) {
            System.out.println("==欢迎进入小宋同学的银行系统==");
            //登陆
            System.out.println("1 用户登陆");
            //注册
            System.out.println("2 用户开户");
            //退出系统
            System.out.println("3 退出系统");
            //任何位置输入0都可返回上一级
            System.out.println("0 返回上一级");
            System.out.println("请选择您要进行的操作:");

            //指令需要设置为字符串,否则如果输入指令不正确会报错
            String select = sc.next();
            switch (select) {
                case "1":
                    //登陆
                    userLogin();
                    break;
                case "2":
                    //注册
                    createAccount();
                    break;
                case "3":
                    //退出系统
                    System.out.println("欢迎下次再来!");
                    //终止虚拟机
                    System.exit(0);
                case "0":
                    //没有上一级了,现在为最初的界面
                    System.out.println("当前界面没有上一级,请重新输入......");
                    break;
                default:
                    //录入的指令有错
                    System.out.println("输入的指令有误,请重新输入......");
                    break;
            }
        }
    }

    //创建账户
    private void createAccount() {
        System.out.println("==用户开户界面==");

        while (true) {
            String accountId = createCardId();

            System.out.print("请您输入账户用户名:");
            String accountName = sc.next();
            //如果键盘输入的是0,直接结束方法，返回上一层,下面出现的代码相似
            if (accountName.equals("0")) {
                return;
            }

            System.out.print("请您输入您的性别:");
            String accountSex;
            while (true) {
                accountSex = sc.next();
                if (accountSex.equals("0")) {
                    return;
                }
                //设置性别只能为“男”或“女,”不能用或，要用与运算，因为是反着判断
                if (!(accountSex.equals("男")) && !(accountSex.equals("女"))) {
                    System.out.println("输入的性别不合法,请重新输入......");
                } else {
                    break;
                }
            }

            //调用方法判断是先生还是女士
            String appellation = judgmentIdentity(accountSex);

            System.out.print("请您输入账户密码:");
            String accountPassword = sc.next();
            if (accountPassword.equals("0")) {
                return;
            }


            System.out.print("请您输入确认密码:");
            while (true) {
                String accountConfirmPassword = sc.next();
                if (accountConfirmPassword.equals("0")) {
                    return;
                }

                if (accountConfirmPassword.equals(accountPassword)) {
                    break;
                } else {
                    System.out.println("输入的确认密码不正确,请重新输入......");
                }
            }

            System.out.print("请您输入账户每次取现限额:");
            double withdrawalAmount = sc.nextDouble();
            if (withdrawalAmount == 0) {
                return;
            }

            Account account = new Account(accountId, accountName, accountSex, accountPassword, withdrawalAmount);
            accountList.add(account);

            System.out.println("恭喜您," + account.getAccountName() + appellation + ",您开户成功,您的卡号是:" + account.getAccountId());

            System.out.println("是否继续进行开户: YES/NO");
            String yesOrNo = sc.next();
            if (yesOrNo.equals("NO")) {
                break;
            }
        }
    }

    //随机生成卡号
    private String createCardId() {
        Random r = new Random();

        while (true) {
            //判断条件，如果为false，则说明卡号已经存在，为true表示卡号唯一
            boolean flag = true;
            //获取随机的数字
//            String[] randomNumber = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String cardId = "";
            //生成卡号第一位不能为0
            int indexFirst = r.nextInt(9) + 1;
            //将随机的字符累加成字符串
            cardId += indexFirst;
            for (int i = 0; i < 7; i++) {
                int index = r.nextInt(10);
                cardId += index;
            }
            //判断卡号是否重复
            for (int i = 0; i < accountList.size(); i++) {
                //获取账户集合中的每一个元素
                Account account = accountList.get(i);
                //判断是否有重复的卡号
                if (account.getAccountId().equals(cardId)) {
                    flag = false;
                    break;
                }
            }

            //如果卡号唯一就返回卡号
            if (flag) {
                return cardId;
            }
            //如果boolean反着来，
            /*
            if (!flag) {
                break;
            }
             */
        }
    }

    //用户登陆界面
    private void userLogin() {
        System.out.println("==系统登录操作==");
        //判断该银行中是否存在银行账户,系统中没有账户的话不允许登陆
        if (accountList.size() == 0) {
            System.out.println("该银行还未录入账户信息,请先进行用户注册操作......");
            //直接跳转到用户开户界面
            System.out.println("是否进行用户开户操作? YES/NO");
            String select = sc.next();
            if (select.equals("YES")) {
                createAccount();
                System.out.println("1 系统登陆");
                System.out.println("0 返回上一级");
                System.out.println("请选择:");
                String select1 = sc.next();
                if (select1.equals("0")) {
                    return;
                }
            } else {
                return;
            }
        }

        System.out.println("请您输入登陆卡号:");

        String accountId;

        int index = -1;

        while (true) {
            accountId = sc.next();

            if (accountId.equals("0")) {
                return;
            }
            //判断一下用户输入的卡号是否存在,如果存在就将索引赋值给index
            for (int i = 0; i < accountList.size(); i++) {
                accountLock = accountList.get(i);
                if (accountLock.getAccountId().equals(accountId)) {
                    index = i;
                }
            }

            //如果index没有被重新赋值的话,就说明卡号不存在
            if (index == -1) {
                System.out.println("系统中不存在该账户卡号,请重新输入......");
            } else {
                accountLock = accountList.get(index);
                if (accountLock.isLock()) {
                    System.out.println("您的账户已锁定,请明天再试!");
                    System.exit(0);
                }
                break;
            }
        }

        System.out.println("请您输入登陆密码:");
        for (int i = 0; i < 3; i++) {

            String inputPassword = sc.next();
            if (inputPassword.equals("0")) {
                return;
            }

            //上面求出了输入卡号对应的集合下标，通过下标创建Account对象获取对象的密码属性

            if (!(inputPassword.equals(accountLock.getAccountPassword()))) {
                if (i == 2) {
                    accountLock.setLock(true);
                    System.out.println("您的账户已锁定,请明天再试!");
                    while (true) {
                        System.out.println("按 1 登陆其他账户");
                        System.out.println("按 0 退出系统");
                        System.out.println("请输入您的选择:");
                        String select = sc.next();
                        if (select.equals("0")) {
                            System.out.println("欢迎下次再来!");
                            System.exit(0);
                        } else if (select.equals("1")) {
                            userLogin();
                            return;
                        } else {
                            System.out.println("您输入的指令不正确,请重新输入......");
                        }
                    }
                } else {
                    System.out.println("您输入的密码有误,今日还有" + (2 - i) + "次机会,请重新输入......");
                }
            } else {
                break;
            }
        }

        //获得先生或者女士
        String appellation = judgmentIdentity(accountLock.getAccountSex());


        System.out.println("恭喜您," + accountLock.getAccountName() + appellation + ",您已进入系统,您的卡号是:" + accountLock.getAccountId());

        //进入用户操作界面,将根据账户id获得到的对象和对象的下标作为形参，传给用户操作的方法
        userActions(accountLock, index);
    }

    /*private void userLogin() {
        System.out.println("==系统登录操作==");
        //判断该银行中是否存在银行账户,系统中没有账户的话不允许登陆
        if (accountList.size() == 0) {
            System.out.println("该银行还未录入账户信息,请先进行用户注册操作......");
            //直接跳转到用户开户界面
            System.out.println("是否进行用户开户操作? YES/NO");
            String select = sc.next();
            if (select.equals("YES")) {
                createAccount();
                System.out.println("1 系统登陆");
                System.out.println("0 返回上一级");
                System.out.println("请选择:");
                String select1 = sc.next();
                if (select1.equals("0")) {
                    return;
                }
            } else {
                return;
            }
        }

        System.out.println("请您输入登陆卡号:");

        String accountId;

        int index = -1;

        while (true) {
            accountId = sc.next();

            if (accountId.equals("0")) {
                return;
            }
            //判断一下用户输入的卡号是否存在,如果存在就将索引赋值给index
            for (int i = 0; i < accountList.size(); i++) {
                Account account1 = accountList.get(i);
                if (account1.getAccountId().equals(accountId)) {
                    index = i;
                }
            }

            //如果index没有被重新赋值的话,就说明卡号不存在
            if (index == -1) {
                System.out.println("系统中不存在该账户卡号,请重新输入......");
            } else {
                break;
            }
        }

        System.out.println("请您输入登陆密码:");
        Account account = new Account();
        for (int i = 0; i < 3; i++) {

            String inputPassword = sc.next();
            if (inputPassword.equals("0")) {
                return;
            }

            //上面求出了输入卡号对应的集合下标，通过下标创建Account对象获取对象的密码属性
            account = accountList.get(index);

            if (!(inputPassword.equals(account.getAccountPassword()))) {
                if (i == 2) {
                    account.setLock(true);
                    System.out.println("您的账户已锁定,请明天再试!");
                    while (true) {
                        System.out.println("按 1 登陆其他账户");
                        System.out.println("按 0 退出系统");
                        System.out.println("请输入您的选择:");
                        String select = sc.next();
                        if (select.equals("0")) {
                            System.out.println("欢迎下次再来!");
                            System.exit(0);
                        } else if (select.equals("1")) {
                            userLogin();
                            break;
                        } else {
                            System.out.println("您输入的指令不正确,请重新输入......");
                        }
                    }
                } else {
                    System.out.println("您输入的密码有误,今日还有" + (2 - i) + "次机会,请重新输入......");
                }
            } else {
                break;
            }
        }
        //获得先生或者女士
        String appellation = judgmentIdentity(account.getAccountSex());


        System.out.println("恭喜您," + account.getAccountName() + appellation + ",您已进入系统,您的卡号是:" + account.getAccountId());

        //进入用户操作界面,将根据账户id获得到的对象和对象的下标作为形参，传给用户操作的方法
        userActions(account, index);
    }*/

    //用户操作界面
    //可以不传形参,直接将account的级别提到最高级
    private void userActions(Account account, int index) {
        while (true) {
            System.out.println("==" + account.getAccountName() + judgmentIdentity(account.getAccountSex()) + ",您可以办理一下业务==");
            System.out.println("1 查询账户");
            System.out.println("2 存款");
            System.out.println("3 取款");
            System.out.println("4 转账");
            System.out.println("5 修改密码");
            System.out.println("6 退出");
            System.out.println("7 注销用户");
            System.out.println("请选择:");
            String select = sc.next();

            switch (select) {
                case "1":
                    //展示相关信息
                    queryAccount(account);
                    break;
                case "2":
                    //存款
                    deposit(account);
                    break;
                case "3":
                    //取款
                    withdrawal(account);
                    break;
                case "4":
                    //转账
                    transfer(account);
                    break;
                case "5":
                    int num1 = changeThePassword(account);
                    if (num1 == 1) {
                        return;
                    }
                    break;
                case "6":
                    return;//输入6后,return,返回userActions后,下面没代码直接结束,返回第37行,方法执行结束,直接break结束switch,返回最初的界面
                case "7":
                    int num = deleteAccount(account, index);
                    //如果注销成功的话返回两级
                    if (num == 0) {
                        return;
                    }
                    break;
                default:
                    System.out.println("输入的字符不合法,请重新输入......");
                    break;
            }
        }
    }

    //销户
    private int deleteAccount(Account account, int index) {
        System.out.println("==销户界面==");
        System.out.println("您确定要销户吗? YES/NO");
        String input = sc.next();
        if (input.equals("YES")) {
            if (account.getAccountBalance() > 0) {
                System.out.println("您账户中还有钱没有取完,不允许销户......");
                return 1;
            } else {
                accountList.remove(index);
                System.out.println("删除账户成功!");
            }
        }
        return 0;
    }

    //修改密码
    private int changeThePassword(Account account) {
        if (isLock) {
            System.out.println("您账户修改密码功能已锁定,请明日再试!");
            return 0;
        }
        System.out.println("==修改密码==");
        System.out.println("请您输入当前账户的密码:");
        //三次机会
        for (int i = 0; i <= 3; i++) {
            String oldPassword = sc.next();
            if (oldPassword.equals("0")) {
                return 0;
            }
            if (oldPassword.equals(account.getAccountPassword())) {
                System.out.println("请您输入新的密码:");
                String newPassword = sc.next();
                if (newPassword.equals("0")) {
                    return 0;
                }
                System.out.println("请您确认下新密码:");
                while (true) {
                    String password = sc.next();
                    if (password.equals("0")) {
                        break;
                    }
                    if (password.equals(newPassword)) {
                        break;
                    } else {
                        System.out.println("您输入的确认密码不正确,请重新输入......");
                    }
                }
                account.setAccountPassword(newPassword);
                System.out.println("密码修改成功,请您重新登陆!");
                return 1;
            } else {
                if (3 - i == 0) {
                    System.out.println("您账户修改密码功能已锁定,请明日再试!");
                    isLock = true;
                } else {
                    System.out.println("当前账户密码不正确,您今日还剩" + (3 - i) + "次机会,请重新输入......");
                }
            }
        }
        return 0;
    }

    //转账
    private void transfer(Account account) {
        System.out.println("==转账界面==");

        if (accountList.size() < 2) {
            System.out.println("当前系统,账户不足2个,不能转账......");
        } else if (account.getAccountBalance() < 100) {
            System.out.println("当前账户余额不足,不允许转账......");
        } else {
            System.out.println("请您输入转账的账户卡号:");
            while (true) {
                String inputCardId = sc.next();
                if (inputCardId.equals("0")) {
                    return;
                }
                int index = -1;
                Account account1;
                for (int i = 0; i < accountList.size(); i++) {
                    account1 = accountList.get(i);
                    if (account1.getAccountId().equals(inputCardId)) {
                        index = i;
                    }
                }
                if (index == -1) {
                    System.out.println("不存在该账户,请重新输入......");
                } else {
                    account1 = accountList.get(index);
                    String getName = account1.getAccountName();
                    String replaceFirstChar = getName.replace(getName.charAt(0), '*');
                    System.out.println("您当前在为" + replaceFirstChar + "转账!");
                    System.out.println("请您输入姓氏确认:");
                    while (true) {
                        String firstName = sc.next() + "";
                        if (firstName.equals("0")) {
                            return;
                        }
                        if (firstName.equals(getName.charAt(0) + "")) {
                            System.out.println("请输入转账金额:");
                            double money = sc.nextDouble();
                            if (money == 0) {
                                return;
                            }
                            //当前转账的用户
                            double resultAccount = account.getAccountBalance() - money;
                            //被转账的用户
                            double resultAccount1 = account1.getAccountBalance() + money;
                            account.setAccountBalance(resultAccount);
                            account1.setAccountBalance(resultAccount1);
                            System.out.println("转账成功,当前账户余额" + account.getAccountBalance());
                            break;
                        } else {
                            System.out.println("姓氏不匹配,请重新输入......");
                        }
                    }
                    break;
                }
            }
        }
    }

    //取款
    private void withdrawal(Account account) {
        System.out.println("==取款界面==");
        while (true) {
            System.out.println("请输入取款金额:");
            while (true) {
                //要把取款金额放到循环里面,不然会发生死循环
                double money = sc.nextDouble();
                if (money == 0) {
                    break;
                }
                //判断账户是否有钱
                if (account.getAccountBalance() >= 100) {
                    //判断取款金额是不是100的倍数
                    if (money >= 100 && money % 100 == 0) {
                        if (money > account.getAccountBalance()) {
                            System.out.println("取款金额大于余额,您当前账户余额" + account.getAccountBalance() + "请重新输入取款金额......");
                        } else if (money > account.getWithdrawalAmount()) {
                            System.out.println("取款金额大于当前限额,您当前账户限额" + account.getWithdrawalAmount() + "请重新输入取款金额......");
                        } else {
                            double accountBalance = account.getAccountBalance();
                            double resultMoney = accountBalance - money;
                            account.setAccountBalance(resultMoney);
                            System.out.println("取款成功,您当前账户剩余金额" + resultMoney);
                            break;
                        }
                    } else {
                        System.out.println("取款金额必须是100的倍数,请重新输入......");
                    }
                } else {
                    System.out.println("余额不足,不支持取款......");
                    System.out.println("输入exit退出取款界面");
                    String s = sc.next();
                    if (s.equals("exit")) {
                        return;
                    }
                }
            }
            //需要放在最外层
            System.out.println("是否继续进行取款? YES/NO");
            String select = sc.next();
            if (select.equals("NO")) {
                return;
            }
        }
    }

    //存款
    private void deposit(Account account) {
        System.out.println("==存款界面==");
        //如果不把maney定义在外边,再次存钱的时候就会被覆盖掉
        /*
        ==存款界面==
        请您输入要存款的金额:
        1000
        您的当前账户总金额为1000.0
        您是否要继续存款 YES/NO
        YES
        请您输入要存款的金额:
        1000
        您的当前账户总金额为1000.0
        您是否要继续存款 YES/NO
         */
        while (true) {
            System.out.println("请您输入要存款的金额:");
            double maney = sc.nextDouble();
            if (maney == 0) {
                return;
            }
            //account.getAccountBalance() += maney;这样写是错误的,因为余额是封装在account类中的,不允许直接修改,只能使用set方法修改
            double resultManey = account.getAccountBalance() + maney;

            //出现上面情况的原因是没有将集合中的金额重新设置
            account.setAccountBalance(resultManey);

            System.out.println("存入金额" + maney + ",您的当前账户总金额为" + resultManey);
            System.out.println("您是否要继续存款 YES/NO");
            String s = sc.next();
            if (s.equals("NO")) {
                break;
            }
        }
    }

    //查询账户的相关信息
    private void queryAccount(Account account) {
        System.out.println("==查询账户信息==");
        System.out.println("------------------------------");
        System.out.println("您当前卡号为:" + account.getAccountId());
        System.out.println("------------------------------");
        System.out.println("您的姓名为:" + account.getAccountName());
        System.out.println("------------------------------");
        System.out.println("您的性别为:" + account.getAccountSex());
        System.out.println("------------------------------");
        System.out.println("您的密码为:" + account.getAccountPassword());
        System.out.println("------------------------------");
        System.out.println("您的余额为:" + account.getAccountBalance());
        System.out.println("------------------------------");
        System.out.println("您的每次取现额度为:" + account.getWithdrawalAmount());
        System.out.println("------------------------------");
    }

    //判断是先生还是女士
    public String judgmentIdentity(String accountSex) {
        return accountSex.equals("男") ? "先生" : "女士";
    }
}
