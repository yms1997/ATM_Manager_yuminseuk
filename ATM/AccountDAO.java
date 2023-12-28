package ATM;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountDAO {
  ArrayList<Account> accList;
  Util ut;

  AccountDAO(){
      accList = new ArrayList<>();
      ut = new Util();
  }

  void addAccountsFromData(String data){ // 파일 분할해서 배열에 넣기
      if(data.isEmpty()) return;
      String[] temp = data.split("\n");
      for (int i = 0; i < temp.length; i++) {
        String[] info = temp[i].split("/");
        accList.add(new Account(info[0], info[1], Integer.parseInt(info[2])));
      }
  }

  String saveAsFileData() { // 파일 저장
    if(accList.size() == 0) return "";
    String data = "";
    for(Account ac : accList) {
      data += ac.saveToData();
    }
    return data;
  }

  ArrayList<Account> getAllAccountbyId(String id){
    if(accList.size() == 0) return null;
    ArrayList<Account> list = new ArrayList<>();
    for (int i = 0; i < accList.size(); i++) {
      if(accList.get(i).clientId.equals(id)){
        list.add(accList.get(i));
      }
    }
    return list;
  }

  void deleteAllAccountsInOneClient(Client cli){ // 한 회원 모든 계좌정보 삭제
    if(accList.size() == 0) return;
    for (int i = 0; i < accList.size(); i++) {
      if(cli.id.equals(accList.get(i).clientId)){
        accList.remove(accList.get(i));
        i--;
      }
    }
  }

  boolean checkAccPattern(String AccNum){ // 계좌번호 확인
    String acc = AccNum;
    String accPattern = "^\\d{4}-\\d{4}-\\d{4}$";
    Pattern p = Pattern.compile(accPattern);
    Matcher m = p.matcher(acc);
    if(m.matches()){
      return true;
    }
    return false;
  }

  String getAccountValue() { // 계좌생성
    while (true){
      String AccNum = ut.getValue("계좌");
      if(!checkAccPattern(AccNum)){
        System.out.println("1111-1111-1111 형태로 입력해주세요");
        continue;
      }
      return AccNum;
    }
  }


  void addOneAcc(Client client){ // 계좌추가
    String AccNum = getAccountValue();
    if(getAccbyAccNum(AccNum) != null){
      System.out.println("이미 존재하는 계좌번호가 있습니다");
      return;
    }
    accList.add(new Account(client.id, AccNum, 0));
    System.out.println("[ 계좌 추가 완료 ]");
  }

  Account getAccbyAccNum(String AccNUm){ // 계좌중복검사
    for (Account acc : accList) {
      if(acc.accNumber.equals(AccNUm)){
        return acc;
      }
    }
    return null;
  }

  Account getAccbyClient(String AccNUm, String id){ // 내계좌검사
    for (Account acc : accList) {
      if(acc.accNumber.equals(AccNUm) && acc.clientId.equals(id)){
        return acc;
      }
    }
    return null;
  }

  Account getLogAccount(String id){
    while(true){
        String accNum = getAccountValue();
        Account acc = getAccbyClient(accNum, id);
        if(acc == null){
          System.out.println("해당 계좌번호가 없습니다");
          continue;
        }
        if(!isMyAccountAcc(id, accList)){
          System.out.println("본인 계좌번호만 가능합니다");
          continue;
        }
        return acc;
    }
  }

  boolean isMyAccountAcc(String id, ArrayList<Account> accList) {
    for (Account acc : accList) {
      if(acc.clientId.equals(id)){
        return true;
      }
    }
    return false;
  }

  int getAccIdx(Account acc){
    for (int i = 0; i < accList.size(); i++) {
        if(acc == accList.get(i)){
          return i;
        }
    }
    return -1;
  }

  void removeOncAcc(Client client){
    Account Acc = getLogAccount(client.id);
    accList.remove(getAccIdx(Acc));
    System.out.println("[ 계좌 삭제 완료 ]");
  }

  void depositMoney(Client client){
    Account acc = getLogAccount(client.id);
    if(acc == null) return;
    int money = ut.getValue("[ 입금 ]", 100, 1000000);
    acc.money += money;
    System.out.println("%d원 입금완료".formatted(money));
    System.out.println("현재 잔고 : %d원".formatted(acc.money));
  }

  void withdrawMoney(Client client){
    Account acc = getLogAccount(client.id);
    if(acc == null) return;
    int money = ut.getValue("[ 출금 ]", 100, acc.money);
    if(acc.money - money < 0){
      System.out.println("잔액 부족");
      return;
    }
    acc.money -= money;
    System.out.println("%d원 출금완료".formatted(money));
    System.out.println("현재 잔고 : %d원".formatted(acc.money));
  }

  void transferMoney(Client client){
    Account myAcc = getLogAccount(client.id);
    if(myAcc == null) return;
    Account yourAcc = getAccbyAccNum(getAccountValue());
    if(yourAcc == null){
      System.out.println("존재하지 않는 계좌번호");
      return;
    }
    if(myAcc == yourAcc){
      System.out.println("본인계좌 이체 불가능");
      return;
    }
    int money = ut.getValue("[ 이체 ]", 100, myAcc.money);
    if(myAcc.money - money < 0){
      System.out.println("잔액 부족");
      return;
    }
    myAcc.money -= money;
    yourAcc.money += money;
    System.out.println("%d원 이체완료".formatted(money));
    System.out.println("현재 잔고 : %d원 상대방 잔고 : %d원".formatted(myAcc.money, yourAcc.money));
  }

  void showLogAccount(Client client){
    ArrayList<Account> temp = getAllAccountbyId(client.id);
    System.out.println("================================");
    System.out.println("회원번호\t계좌번호\t잔액");
    System.out.println("--------------------------------");
    for (Account acc : temp) {
      System.out.println(acc);
    }
  }

  void printAllAccount(){
    for (Account a : accList) {
      System.out.println(a);
    }
  }

}

